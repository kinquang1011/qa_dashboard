package vn.com.vng.stats.dashboard.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import vn.com.vng.stats.dashboard.service.SsoService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.logging.Logger;

import static vn.com.vng.stats.dashboard.constant.AppConstantValue.SSO_URL;

@Controller
public class LoginController {

    private static final Logger logger = Logger.getLogger(LoginController.class.getName());

    @Autowired
    private SsoService ssoService;

    @Autowired
    @Value("${appId}")
    private String appId;

    @RequestMapping(value = "/authen", method = RequestMethod.GET)
    public String authenPage(@CookieValue("sid") String cookieValue,
                             @RequestParam(value = "permission", required = false) String permission) {
        logger.info(cookieValue);
        if (Boolean.parseBoolean(permission)) {
            return "redirect:" + SSO_URL + appId;
        } else  {
            return "redirect:login?sid=" + cookieValue;
        }

    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String loginPage(@RequestParam(value = "sid", required = false) String sid,
                            ModelMap map) throws Exception {
        String domain = ssoService.checklogin(sid);
        if (!StringUtils.isEmpty(domain)) {
            map.put("domainName", domain);
            return "login";
        } else {
            return "redirect:/authen?permission=false";
        }

    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logoutPage(HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return "redirect:/authen";
    }

}
