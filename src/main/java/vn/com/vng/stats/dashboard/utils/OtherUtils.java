package vn.com.vng.stats.dashboard.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import static vn.com.vng.stats.dashboard.constant.AppConstantValue.FROM_DATE;
import static vn.com.vng.stats.dashboard.constant.AppConstantValue.TO_DATE;

/**
 * Created by Tanaye on 5/28/17.
 */

public class OtherUtils {

    public static Map splitDate(String rangeDate) throws ParseException {
        return new HashMap<String, String>() {{
            String fromDate = "";
            String toDate = "";
            if (rangeDate != null && !"".equals(rangeDate)) {
                fromDate = rangeDate.split(" - ")[0].trim().replaceAll("/", "-");
                toDate = rangeDate.split(" - ")[1].trim().replaceAll("/", "-");
            }
            put(FROM_DATE, fromDate);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Calendar c = Calendar.getInstance();
            c.setTime(sdf.parse(toDate));
            c.add(Calendar.DATE, 1);
            toDate = sdf.format(c.getTime());
            put(TO_DATE, toDate);
        }};
    }

    public static int toInt(String value, int ifnull) {
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            return ifnull;
        }
    }

    public static boolean isGreaterThanToday(String date) throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        c.setTime(sdf.parse(date));
        Calendar c1 = Calendar.getInstance();
        return (c.getTime().compareTo(c1.getTime())) == 1;
    }

}
