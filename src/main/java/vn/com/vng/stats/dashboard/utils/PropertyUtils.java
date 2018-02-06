package vn.com.vng.stats.dashboard.utils;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Logger;

import static vn.com.vng.stats.dashboard.constant.AppConstantValue.PROPERTIES_FILE_NAME;

/**
 * Created by tanaye on 13/03/2017.
 */
public class PropertyUtils {

    private static final Logger logger = Logger.getLogger(PropertyUtils.class.getName());

    public static String readProperties(String propertyName) {
        Properties prop = new Properties();
        InputStream input = null;
        String result = null;
        try {
            input = new FileInputStream(PROPERTIES_FILE_NAME);
            if (input == null) {
                logger.warning("Sorry, unable to find : " + PROPERTIES_FILE_NAME);
                return result;
            }
            prop.load(input);
            result = prop.getProperty(propertyName);
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }

    public static void updateProperties(String propertyName, String value) throws Exception {
        PropertiesConfiguration config = new PropertiesConfiguration(PROPERTIES_FILE_NAME);
        config.setProperty(propertyName, value);
        config.save();
    }


}
