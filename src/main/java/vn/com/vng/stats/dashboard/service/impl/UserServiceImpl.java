package vn.com.vng.stats.dashboard.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.com.vng.stats.dashboard.dao.UserDao;
import vn.com.vng.stats.dashboard.form.GameConfigForm;
import vn.com.vng.stats.dashboard.form.UserForm;
import vn.com.vng.stats.dashboard.model.User;
import vn.com.vng.stats.dashboard.service.UserService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static vn.com.vng.stats.dashboard.constant.AppConstantValue.ADMIN_DEFAULT;
import static vn.com.vng.stats.dashboard.constant.AppConstantValue.DEFAULT_USER;

/**
 * Created by Tanaye on 6/8/17.
 */
@Service("userService")
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Override
    public List<User> getUsers() throws Exception {

        return new ArrayList<User>() {{
            ADMIN_DEFAULT.keySet().stream().forEach(k -> {
                add(ADMIN_DEFAULT.get(k));
            });
            addAll(userDao.getUsers());
        }};
    }

    @Override
    public Map<String, Object> addUser(UserForm userForm) throws Exception {

        Map<String, Object> result = new HashMap<>();
        if (!userForm.isNullValue()) {
            if (userForm.isUpdate()) {
                userDao.updateUser(userForm);
                result.put("result", true);
                result.put("message", "Success");
            } else if (ADMIN_DEFAULT.containsKey(userForm.getDomainName())) {
                result.put("result", false);
                result.put("message", "Domain Name : " + userForm.getDomainName() + " Đã tồn tại ...");
            } else if (userDao.isContains(userForm.getDomainName())) {
                result.put("result", false);
                result.put("message", "Domain Name : " + userForm.getDomainName() + " Đã tồn tại ...");
            } else {
                userDao.addNewUser(userForm);
                result.put("result", true);
                result.put("message", "Success");
            }
        }

        return result;
    }

    @Override
    public Map<String, Object> removeUser(UserForm userForm) throws Exception {

        Map<String, Object> result = new HashMap<>();
        if (ADMIN_DEFAULT.containsKey(userForm.getDomainName())) {
            result.put("result", false);
            result.put("message", "Domain Name : " + userForm.getDomainName() + " Không thể xoá ...");
        } else if (!userDao.isContains(userForm.getDomainName())) {
            result.put("result", false);
            result.put("message", "Domain Name : " + userForm.getDomainName() + " Không tồn tại ...");
        } else {
            userDao.removeUser(userForm);
            result.put("result", true);
            result.put("message", "Success");
        }

        return result;
    }
}
