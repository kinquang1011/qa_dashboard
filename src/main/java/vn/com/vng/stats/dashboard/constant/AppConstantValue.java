package vn.com.vng.stats.dashboard.constant;

import vn.com.vng.stats.dashboard.model.RolePermission;
import vn.com.vng.stats.dashboard.model.User;

import java.util.*;

/**
 * Created by Tanaye on 5/23/17.
 */
public class AppConstantValue {

    public static final String PROPERTIES_FILE_NAME = "application.properties";

    public static final String SSO_URL = "https://sso.stats.vng.com.vn/ssoLogin?appId=";

    public static final String DEFAULT_PASS = "admin";

    public static final String DEFAULT_USER = "admin";

    public static final String FROM_DATE = "fromDate";

    public static final String TO_DATE = "toDate";

    public final static String DEFAULT_ALERT_VALUE_PROPERTIES = "alertValue";

    public static final Map<String, User> ADMIN_DEFAULT = new HashMap<String, User>() {{
        put("yentn2", new User(-1, "yentn2", "", "ADMIN"));
        put("canhtq", new User(-1, "canhtq", "", "ADMIN"));
    }};

    public static final Set<RolePermission> ROLES = new HashSet<RolePermission>() {{
        add(new RolePermission(1l, "ROLE_ADMIN"));
    }};

}
