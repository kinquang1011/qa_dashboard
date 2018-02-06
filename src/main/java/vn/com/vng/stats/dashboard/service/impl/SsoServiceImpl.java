package vn.com.vng.stats.dashboard.service.impl;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import vn.com.vng.stats.dashboard.dao.UserDao;
import vn.com.vng.stats.dashboard.service.SsoService;
import vn.com.vng.stats.dashboard.utils.JsonUtils;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.logging.Logger;

import static vn.com.vng.stats.dashboard.constant.AppConstantValue.ADMIN_DEFAULT;


/**
 * Created by Tanaye on 5/25/17.
 */

@Service("ssoServiceImpl")
public class SsoServiceImpl implements SsoService {

    private static final Logger logger = Logger.getLogger(SsoServiceImpl.class.getName());

    @Autowired
    @Value("${appId}")
    private String appId;

    @Autowired
    @Value("${appKey}")
    private String appKey;

    @Autowired
    @Value("${ssoUrlCheckSession}")
    private String ssoUrlCheckSession;

    @Autowired
    private UserDao userDao;

    @Override
    public String getSession(String sid) {
        return null;
    }

    @Override
    public String checklogin(String sid) throws Exception {

        if (StringUtils.isEmpty(sid)) {
            return null;
        }

        return post(ssoUrlCheckSession, JsonUtils.Object2Json(new HashMap<String, String>() {{
            put("id", sid);
        }}));
    }

    private String post(String apiUrl, String datasJson) throws Exception {
        logger.info("API Url : " + apiUrl);

        URL url = new URL(apiUrl);

        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

        conn.setDoOutput(true);
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setRequestProperty("X-App-Id", appId);
        conn.setRequestProperty("X-Api-Key", appKey);

        OutputStream os = conn.getOutputStream();
        os.write(datasJson.getBytes());
        os.flush();

        if (conn.getResponseCode() >= HttpURLConnection.HTTP_BAD_REQUEST) {
            return null;
        }

        String respone = readInputStreamToString(conn);

        conn.disconnect();

        JSONParser parser = new JSONParser();
        Object obj = parser.parse(respone);
        JSONObject jsonObject = (JSONObject) obj;
        Long status = (Long) jsonObject.get("status");
        String domainName = (String) ((JSONObject) jsonObject.get("data")).get("uid");

        if (status == 200) {

            if (ADMIN_DEFAULT.containsKey(domainName) || userDao.isContains(domainName)) {
                return domainName;
            }

            return null;

        }

        return null;
    }

    private String readInputStreamToString(HttpURLConnection connection) {
        String result = null;
        StringBuffer sb = new StringBuffer();
        InputStream is = null;

        try {
            is = new BufferedInputStream(connection.getInputStream());
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String inputLine = "";
            while ((inputLine = br.readLine()) != null) {
                sb.append(inputLine);
            }
            result = sb.toString();
        } catch (Exception e) {
            result = null;
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                }
            }
        }

        return result;
    }
}
