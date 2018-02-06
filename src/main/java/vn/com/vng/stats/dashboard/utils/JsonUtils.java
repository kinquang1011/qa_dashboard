package vn.com.vng.stats.dashboard.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.util.StringUtils;

/**
 * Created by tanaye on 13/03/2017.
 */
public class JsonUtils {

    public static  <T> T Json2Object(String source, Class<T> clazz) throws Exception {
        if (StringUtils.isEmpty(source)){
            return null;
        } else{
            T result = clazz.newInstance();
            ObjectMapper mapper = new ObjectMapper();
            result = mapper.readValue(source, clazz);
            return  result;
        }
    }

    public static String Object2Json(Object o) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(o);
    }

}
