package vn.com.vng.stats.dashboard.dao;


import vn.com.vng.stats.dashboard.form.UserForm;
import vn.com.vng.stats.dashboard.model.UserAccount;

import java.util.List;

public interface UserDao {

    UserAccount loadUserByUsername(String username) throws Exception;

    List getUsers() throws Exception;

    boolean isContains(String userForm) throws Exception;

    void addNewUser(UserForm userForm) throws Exception;

    boolean updateUser(UserForm userForm);

    boolean removeUser(UserForm userForm) throws Exception;
}
