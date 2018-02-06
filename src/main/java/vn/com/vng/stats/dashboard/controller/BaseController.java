package vn.com.vng.stats.dashboard.controller;

import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public abstract class BaseController {

    protected String writeJson(Object result) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(result);
    }


    protected void writeJsonResponse(HttpServletResponse response, Object result) throws IOException {
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write(writeJson(result));
    }

    protected String getPrincipal() {
        String userName = null;
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof UserDetails) {
            userName = ((UserDetails) principal).getUsername();
        } else {
            userName = principal.toString();
        }
        return userName;
    }

    protected void setSessionAttribute(HttpServletRequest request, String name, Object object) {
        if (request != null && !StringUtils.isEmpty(name)) {
            request.getSession().setAttribute(name, object);
        }
    }

    protected <T> T getSessionAttribute(HttpServletRequest request, String name) {
        if (request == null || StringUtils.isEmpty(name)) {
            return null;
        }

        Object object = request.getSession().getAttribute(name);
        return object == null ? null : (T) object;
    }
}
