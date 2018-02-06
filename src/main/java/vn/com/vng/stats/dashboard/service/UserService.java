package vn.com.vng.stats.dashboard.service;

import vn.com.vng.stats.dashboard.form.GameConfigForm;
import vn.com.vng.stats.dashboard.form.UserForm;
import vn.com.vng.stats.dashboard.model.User;

import java.util.List;
import java.util.Map;

/**
 * Created by Tanaye on 6/7/17.
 */
public interface UserService {

    List<User> getUsers() throws Exception;

    Map<String, Object> addUser(UserForm userForm) throws Exception;

    Map<String, Object> removeUser(UserForm userForm) throws Exception;
}
