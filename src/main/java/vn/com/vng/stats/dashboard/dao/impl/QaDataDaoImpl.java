package vn.com.vng.stats.dashboard.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;
import vn.com.vng.stats.dashboard.dao.QaDataDao;
import vn.com.vng.stats.dashboard.model.QaData;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * Created by Tanaye on 5/28/17.
 */
@Repository
public class QaDataDaoImpl extends BaseDaoImpl implements QaDataDao {

    private static final Logger logger = Logger.getLogger(QaDataDaoImpl.class.getName());

    @Autowired
    @Qualifier("jdbcRead")
    private JdbcTemplate jdbcRead;

    @Autowired
    @Qualifier("jdbcWrite")
    private JdbcTemplate jdbcWrite;

    @Autowired
    @Value("${alertValue}")
    private String alertValue;


    @Override
    public String getMaxDate() throws Exception {
        String sql = "select max(report_date) as date from qc_game_kpi";

        List<QaData> qaDataList = queryForList(jdbcRead, sql, QaData.class);

        return qaDataList.get(0).getDate();
    }

    @Override
    public String getMinDate() throws Exception {
        String sql = "select min(report_date) as date from qc_game_kpi";
        List<QaData> qaDataList = queryForList(getConnection(jdbcRead), sql, QaData.class);
        return qaDataList.get(0).getDate();
    }

    @Override
    public List<QaData> getData4Qa() throws Exception {
        String sql = "select tb2.report_date as date, " +
                "tb2.kpi_id as kpiId, " +
                "tb2.game_code as gameCode, " +
                "tb4.GameName as gameName,  " +
                "IFNULL(tb1.kpi_value , 0) as kpiValueOther , " +
                "IFNULL(tb1.source, 'nodata' ) as otherSource," +
                "IFNULL(tb2.kpi_value, 0 ) as kpiValueSource," +
                "IFNULL(tb2.source, 'nodata' ) as toolSource " +
                "from qc_game_kpi tb1 " +
                "inner join " +
                "(select * from game_kpi where report_date > '%s') tb2 " +
                "on tb2.game_code = tb1.game_code and " +
                "tb1.report_date = tb2.report_date and " +
                "tb1.kpi_id = tb2.kpi_id " +
                "inner join kpi_desc tb3 " +
                "on tb2.kpi_id = tb3.kpi_id  " +
                "inner join games tb4 " +
                "on tb4.GameCode = tb2.game_code  " +
                "inner join mt_report_source tb5 " +
                "on tb5.game_code = tb2.game_code and " +
                "tb5.group_id = tb3.group_id and " +
                "tb5.data_source = tb2.source  " +
                "where tb5.kpi_type = \"game_kpi\" " +
                "group by tb2.id";

        String sqlQuery = String.format(sql, getMinDate());

        List<QaData> qaDataList = queryForList(getConnection(jdbcRead), sqlQuery, QaData.class);
        return qaDataList;
    }

    @Override
    public void writeData(List<QaData> qaDataList) throws Exception {
        String insertTable = "INSERT INTO qa_data_result(`key` , report_date, game_code, game_name, kpi_id, other_source, tool_source, diff) VALUES (?,?,?,?,?,?,?,?)"
                + "ON DUPLICATE KEY UPDATE other_source = ? , tool_source = ?, diff = ?";

        final int batchSize = 500;

        for (int j = 0; j < qaDataList.size(); j += batchSize) {
            final List<QaData> batchList = qaDataList.subList(j, j + batchSize > qaDataList.size() ? qaDataList.size() : j + batchSize);
            getConnection(jdbcWrite).batchUpdate(insertTable,
                    new BatchPreparedStatementSetter() {
                        @Override
                        public void setValues(PreparedStatement ps, int i)
                                throws SQLException {
                            QaData qaData = batchList.get(i);
                            ps.setString(1, createPriKey(qaData));
                            ps.setString(2, qaData.getDate());
                            ps.setString(3, qaData.getGameCode());
                            ps.setString(4, qaData.getGameName());
                            ps.setInt(5, qaData.getKpiId());
                            ps.setString(6, qaData.getOtherSource() + "(" + qaData.getKpiValueOther() + ")");
                            ps.setString(7, qaData.getToolSource() + "(" + qaData.getKpiValueSource() + ")");
                            ps.setFloat(8, qaData.getDiff());

                            ps.setString(9, qaData.getOtherSource() + "(" + qaData.getKpiValueOther() + ")");
                            ps.setString(10, qaData.getToolSource() + "(" + qaData.getKpiValueSource() + ")");
                            ps.setFloat(11, qaData.getDiff());
                        }

                        @Override
                        public int getBatchSize() {
                            return batchList.size();
                        }
                    });
        }
    }


    @Override
    public List<QaData> getPieData4Qa() throws Exception {
        String sql = "select tb2.report_date as date, " +
                "tb2.kpi_id as kpiId, " +
                "tb2.game_code as gameCode, " +
                "tb4.GameName as gameName,  " +
                "IFNULL(tb1.kpi_value , 0) as kpiValueOther , " +
                "IFNULL(tb1.source, 'nodata' ) as otherSource," +
                "IFNULL(tb2.kpi_value, 0 ) as kpiValueSource," +
                "IFNULL(tb2.source, 'nodata' ) as toolSource " +
                "from (select * from qc_game_kpi where report_date >= '%s') tb1 " +
                "inner join " +
                "(select * from game_kpi where report_date >= '%s') tb2 " +
                "on tb2.game_code = tb1.game_code and " +
                "tb1.report_date = tb2.report_date and " +
                "tb1.kpi_id = tb2.kpi_id " +
                "inner join kpi_desc tb3 " +
                "on tb2.kpi_id = tb3.kpi_id  " +
                "inner join games tb4 " +
                "on tb4.GameCode = tb2.game_code  " +
                "inner join mt_report_source tb5 " +
                "on tb5.game_code = tb2.game_code and " +
                "tb5.group_id = tb3.group_id and " +
                "tb5.data_source = tb2.source  " +
                "where tb5.kpi_type = \"game_kpi\" " +
                "group by tb2.id";

        String sqlQuery = String.format(sql, getMaxDate(), getMaxDate());

        List<QaData> qaDataList = queryForList(getConnection(jdbcRead), sqlQuery, QaData.class);
        return qaDataList;
    }

    @Override
    public List<QaData> refreshData(String fromDate, String toDate) throws Exception {
        String sql = "select tb1.report_date as date, " +
                "tb1.kpi_id as kpiId, " +
                "tb2.game_code as gameCode, " +
                "tb4.GameName as gameName,  " +
                "tb1.kpi_value as kpiValueOther, " +
                "tb1.source as otherSource, " +
                "tb2.kpi_value as kpiValueSource,  " +
                "tb2.source as toolSource " +
                "from (select * from qc_game_kpi where report_date >= '%s' and report_date < '%s') tb1 " +
                "inner join " +
                "(select * from game_kpi where report_date > '%s' and report_date < '%s') tb2 " +
                "on tb2.game_code = tb1.game_code and " +
                "tb1.report_date = tb2.report_date and " +
                "tb1.kpi_id = tb2.kpi_id " +
                "inner join kpi_desc tb3 " +
                "on tb2.kpi_id = tb3.kpi_id  " +
                "inner join games tb4 " +
                "on tb4.GameCode = tb2.game_code  " +
                "inner join mt_report_source tb5 " +
                "on tb5.game_code = tb2.game_code and " +
                "tb5.group_id = tb3.group_id and " +
                "tb5.data_source = tb2.source  " +
                "where tb5.kpi_type = \"game_kpi\" " +
                "group by tb2.id";

        String sqlQuery = String.format(sql, fromDate, toDate, fromDate, toDate);
        List<QaData> qaDataList = queryForList(getConnection(jdbcRead), sqlQuery, QaData.class);
        return qaDataList;
    }

    @Override
    public void updateValue(List<QaData> qaDataList) throws Exception {
        String updateTable = "UPDATE qa_data_result SET other_source = ?, tool_source = ?, diff = ? WHERE report_date = ? and  game_code = ? and kpi_id = ? ";

        final int batchSize = 500;

        for (int j = 0; j < qaDataList.size(); j += batchSize) {
            final List<QaData> batchList = qaDataList.subList(j, j + batchSize > qaDataList.size() ? qaDataList.size() : j + batchSize);
            getConnection(jdbcWrite).batchUpdate(updateTable,
                    new BatchPreparedStatementSetter() {
                        @Override
                        public void setValues(PreparedStatement ps, int i)
                                throws SQLException {

                            QaData qaData = batchList.get(i);
                            ps.setString(1, qaData.getOtherSource() + "(" + qaData.getKpiValueOther() + ")");
                            ps.setString(2, qaData.getToolSource() + "(" + qaData.getKpiValueSource() + ")");
                            ps.setFloat(3, qaData.getDiff());
                            ps.setString(4, qaData.getDate());
                            ps.setString(5, qaData.getGameCode());
                            ps.setInt(6, qaData.getKpiId());

                        }

                        @Override
                        public int getBatchSize() {
                            return batchList.size();
                        }
                    });

        }
    }

    @Override
    public List<QaData> getData4FooTable(String fromDate, String toDate) throws Exception {
        String sql = "select report_date as date, qa_data_result.* from qa_data_result where report_date >= '%s' and report_date < '%s' order by game_code desc, report_date asc";
        List<QaData> qaDataList = queryForList(getConnection(jdbcWrite), String.format(sql, fromDate, toDate), QaData.class);
        return qaDataList;
    }

    @Override
    public List<QaData> getDataToday() throws Exception {
        String sql = "select tb2.report_date as date, " +
                "tb2.kpi_id as kpiId, " +
                "tb2.game_code as gameCode, " +
                "tb4.GameName as gameName,  " +
                "IFNULL(tb1.kpi_value , 0) as kpiValueOther , " +
                "IFNULL(tb1.source, 'nodata' ) as otherSource," +
                "IFNULL(tb2.kpi_value, 0 ) as kpiValueSource," +
                "IFNULL(tb2.source, 'nodata' ) as toolSource " +
                "from (select * from qc_game_kpi where report_date >= DATE(NOW() - INTERVAL 1 DAY)) tb1 " +
                "right join " +
                "(select * from game_kpi where report_date >= DATE(NOW() - INTERVAL 1 DAY) and kpi_id in (%s)) tb2 " +
                "on tb2.game_code = tb1.game_code and " +
                "tb1.report_date = tb2.report_date and " +
                "tb1.kpi_id = tb2.kpi_id " +
                "inner join kpi_desc tb3 " +
                "on tb2.kpi_id = tb3.kpi_id  " +
                "inner join games tb4 " +
                "on tb4.GameCode = tb2.game_code  " +
                "inner join mt_report_source tb5 " +
                "on tb5.game_code = tb2.game_code and " +
                "tb5.group_id = tb3.group_id and " +
                "tb5.data_source = tb2.source  " +
                "where tb5.kpi_type = \"game_kpi\" " +
                "group by tb2.id";

        List<QaData> qaDataList = queryForList(getConnection(jdbcRead), String.format(sql, getKpiId()), QaData.class);
        qaDataList.stream().forEach(qaData -> {
            qaData.setOtherSource(qaData.getOtherSource() + "(" + qaData.getKpiValueOther() + ")");
            qaData.setToolSource(qaData.getToolSource() + "(" + qaData.getKpiValueSource() + ")");
        });
        return qaDataList;

    }

    private String createPriKey(QaData qaData) {
        StringBuilder priKey = new StringBuilder("");
        priKey.append(qaData.getDate().replaceAll("-", ""))
                .append(qaData.getGameCode())
                .append(qaData.getKpiId())
                .append(qaData.getOtherSource());

        return priKey.toString();

    }

    private String getKpiId() throws Exception {
        String sql = "select DISTINCT(kpi_id) as kpiId from game_config";
        List<Integer> kpiIdList = new ArrayList<>();

        List<QaData> qaDataList = queryForList(jdbcWrite, sql, QaData.class);
        qaDataList.stream().forEach(qaData -> kpiIdList.add(qaData.getKpiId()));

        StringBuilder kpiIds = new StringBuilder();
        for (int i = 0; i < kpiIdList.size(); i++) {
            if (i == kpiIdList.size() - 1) {
                kpiIds.append(kpiIdList.get(i));
            } else {
                kpiIds.append(kpiIdList.get(i) + ", ");
            }

        }

        return kpiIds.toString();

    }


}
