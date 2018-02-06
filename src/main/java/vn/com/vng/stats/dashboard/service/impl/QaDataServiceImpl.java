package vn.com.vng.stats.dashboard.service.impl;

import org.hibernate.mapping.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import vn.com.vng.stats.dashboard.dao.GameConfigDao;
import vn.com.vng.stats.dashboard.dao.QaDataDao;
import vn.com.vng.stats.dashboard.model.GameInfo;
import vn.com.vng.stats.dashboard.model.QaData;
import vn.com.vng.stats.dashboard.service.QaDataService;
import vn.com.vng.stats.dashboard.utils.OtherUtils;
import vn.com.vng.stats.dashboard.utils.PropertyUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import static vn.com.vng.stats.dashboard.constant.AppConstantValue.*;
import static vn.com.vng.stats.dashboard.utils.PropertyUtils.readProperties;

/**
 * Created by Tanaye on 5/26/17.
 */
@Service("qaDataServiceImpl")
public class QaDataServiceImpl implements QaDataService {
    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    private static final Logger logger = Logger.getLogger(QaDataServiceImpl.class.getName());

    @Autowired
    private QaDataDao qaDataDao;

    @Autowired
    private GameConfigDao gameConfigDao;

    @Override
    @Scheduled(cron = "0 0 * * * * ")
    public void compareData() throws Exception {

        logger.info("----- QA DATA START -----");

        String lastDate = readProperties("lastDate");
        List<QaData> qaDataList;

        if (StringUtils.isEmpty(lastDate)) {
            qaDataList = qaDataDao.getData4Qa();
        } else {
            qaDataList = qaDataDao.getPieData4Qa();
        }

        String maxDate = qaDataDao.getMaxDate();
        PropertyUtils.updateProperties("lastDate", maxDate.trim());

        compareData(qaDataList);

        qaDataDao.writeData(qaDataList);
        logger.info("-------- DONE ---------");
    }

    @Override
    public void refreshData(String rangeDate) throws Exception {

        logger.info("----- QA DATA REFRESH -----");

        Map<String, String> dateRange = OtherUtils.splitDate(rangeDate);
        String fromDate = dateRange.get(FROM_DATE);
        String toDate = dateRange.get(TO_DATE);

        List<QaData> qaDataList = qaDataDao.refreshData(fromDate, toDate);

        compareData(qaDataList);

        qaDataDao.writeData(qaDataList);

        logger.info("---------- DONE ----------");

    }


    @Override
    public List<List<Object>> getIssuesOfGame(String rangeDate, boolean isNoIssues) throws Exception {

        Map<String, String> dateRange = OtherUtils.splitDate(rangeDate);
        List<QaData> data;

        boolean isGreaterThanToday = OtherUtils.isGreaterThanToday(dateRange.get(TO_DATE));

        if (isGreaterThanToday) {
            Calendar c = Calendar.getInstance();
            c.add(Calendar.DATE, -1);
            String toDAte = sdf.format(c.getTime());
            data = qaDataDao.getData4FooTable(dateRange.get(FROM_DATE), toDAte);
            data.addAll(compareData(qaDataDao.getDataToday()));
            dateRange.put(TO_DATE, toDAte);
        } else {
            data = qaDataDao.getData4FooTable(dateRange.get(FROM_DATE), dateRange.get(TO_DATE));
        }

        Map<String, List<QaData>> mapData = new HashMap<>();

        data.stream().forEach(d -> {
            String key = d.getGameCode() + "-" + d.getKpiId();
            if (mapData.containsKey(key)) {
                mapData.get(key).add(d);
            } else {
                List<QaData> qaDataList = new ArrayList<>();
                qaDataList.add(d);
                mapData.put(key, qaDataList);
            }
        });

        if (isGreaterThanToday) {
            List<GameInfo> gameConfigMap = gameConfigDao.getGameConfig();
            gameConfigMap.stream().forEach(gameInfo -> {
                String key = gameInfo.getGameCode() + "-" + gameInfo.getKpiId();
                String gameName = gameInfo.getGameName();
                if (!mapData.containsKey(key)) {
                    mapData.put(key, new ArrayList<QaData>() {{
                        QaData qaData = new QaData();
                        qaData.setGameName(gameName);
                        add(qaData);
                    }});
                }
            });
        }

        return transformerData(isGreaterThanToday, mapData, isNoIssues, dateRange);
    }

    private List<List<Object>> transformerData(boolean isGreaterThanToday, Map<String, List<QaData>> mapData, boolean isNoIssues, Map<String, String> dateRange) throws Exception {
        filterData(isGreaterThanToday, mapData, isNoIssues, dateRange);

        List<List<Object>> results = new ArrayList<>();

        if (mapData == null || mapData.size() == 0) {
            return results;
        } else {
            mapData.keySet().stream().forEach(k -> {
                List<QaData> ds = mapData.get(k);
                if (ds.size() > 0) {
                    List<Object> value = new ArrayList<>();
                    QaData qaData = ds.get(0);
                    value.add(qaData.getDate());
                    value.add(qaData.getGameCode().toUpperCase());
                    value.add(qaData.getGameName());
                    value.add(qaData.getKpiId() + "");
                    value.add(getRangeValue(ds));
                    value.add(ds.get(ds.size() - 1).getDiff() + "%");
                    value.add(ds.get(ds.size() - 1).getToolSource());
                    value.add(ds.get(ds.size() - 1).getOtherSource());
                    results.add(value);
                }
            });

            Collections.sort(results, (p1, p2) -> {
                Float diff1 = Float.parseFloat(((String) p1.get(5)).replace("%", ""));
                Float diff2 = Float.parseFloat(((String) p2.get(5)).replace("%", ""));
                return new Float(Math.abs(diff2)).compareTo(new Float(Math.abs(diff1)));
            });
        }


        return results;
    }


    private void filterData(boolean isGreaterThanToday, Map<String, List<QaData>> mapData, boolean isNoIssues, Map<String, String> dateRange) throws Exception {
        List<String> keys = new ArrayList<>();

        for (String key : mapData.keySet()) {
            List<QaData> qaDataList = mapData.get(key);
            if (isTrueValue(isGreaterThanToday, key, qaDataList, isNoIssues, dateRange)) {
                keys.add(key);
            }
        }

        for (String key : keys) {
            mapData.remove(key);
        }
    }

    private boolean isTrueValue(boolean isGreaterThanToday, String key, List<QaData> qaDataList, boolean isNoIssues, Map<String, String> dateRange) throws Exception {
        createFakeDate(isGreaterThanToday, qaDataList, key, dateRange);

        Collections.sort(qaDataList, (p1, p2) -> {
            try {
                Date d1 = sdf.parse(p1.getDate());
                Date d2 = sdf.parse(p2.getDate());

                return d1.compareTo(d2);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return 0;
        });

        if (isGreaterThanToday) {
            QaData qaData = qaDataList.get(qaDataList.size() - 1);
            if (qaData.getOtherSource().contains("nodata")) return isNoIssues ? true : false;
            if (qaData.getToolSource().contains("nodata")) return isNoIssues ? true : false;
        }

        for (QaData qaData : qaDataList) {
            if (Math.abs(qaData.getDiff()) >= Float.parseFloat(readProperties(DEFAULT_ALERT_VALUE_PROPERTIES))) {
                return isNoIssues ? true : false;
            }
        }

        return isNoIssues ? false : true;
    }

    private List<String> getRangeValue(List<QaData> qaDataList) {
        List<String> result = new ArrayList<>();

        qaDataList.stream().forEach(qaData -> result.add(qaData.getDiff() + " "));
        return result;
    }

    private List<QaData> compareData(List<QaData> qaDataList) {
        qaDataList.stream().forEach(qaData -> {
            if (qaData.getKpiValueOther() == 0) {
                qaData.setDiff(100f);
            } else {
                float diff = (qaData.getKpiValueSource() - qaData.getKpiValueOther()) * 100 / qaData.getKpiValueOther();
                qaData.setDiff(diff);
            }
        });
        return qaDataList;
    }

    private List<QaData> createFakeDate(boolean isGreaterThanToday, List<QaData> qaDataList, String key, Map<String, String> dateRange) throws Exception {

        String formDate = dateRange.get(FROM_DATE);
        String toDate = dateRange.get(TO_DATE);
        QaData qd = qaDataList.get(0);
        String gameName = qd.getGameName();
        if (qd.isNullValue()) {
            qaDataList.remove(0);
        }

        Map<String, Float> mapDate;

        if (qaDataList.size() == 0) {
            mapDate = new HashMap<>();
        } else {
            mapDate  = qaDataList.stream().collect(
                    Collectors.toMap(x -> x.getDate(), x -> x.getDiff()));
        }


        Date startDate = sdf.parse(formDate), endDate = sdf.parse(toDate);

        Calendar start = Calendar.getInstance();
        start.setTime(startDate);
        Calendar end = Calendar.getInstance();
        end.setTime(endDate);
        if (!isGreaterThanToday) {
            end.add(end.DATE, -1);
        }

        while (!start.after(end)) {
            int year = start.get(Calendar.YEAR);
            int month = start.get(Calendar.MONTH) + 1;
            int day = start.get(Calendar.DAY_OF_MONTH);

            String date = year + "-" + (month < 10 ? "0" + month : month) + "-" + (day < 10 ? "0" + day : day);

            if (!mapDate.containsKey(date)) {
                String gameCode = key.split("-")[0];
                String kpiId = key.split("-")[1];
                QaData qaData = new QaData();
                qaData.setDate(date);
                qaData.setToolSource("nodata(0)");
                qaData.setOtherSource("nodata(0)");
                qaData.setGameName(gameName);
                qaData.setGameCode(gameCode);
                qaData.setKpiId(OtherUtils.toInt(kpiId, 0));
                qaData.setDiff(0f);
                qaDataList.add(qaData);
            }

            start.add(Calendar.DATE, 1);
        }


        return qaDataList;
    }

}
