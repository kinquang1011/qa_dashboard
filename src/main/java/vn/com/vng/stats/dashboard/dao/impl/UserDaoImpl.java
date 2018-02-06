package vn.com.vng.stats.dashboard.dao.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;

import vn.com.vng.stats.dashboard.dao.UserDao;
import vn.com.vng.stats.dashboard.form.UserForm;
import vn.com.vng.stats.dashboard.model.QaData;
import vn.com.vng.stats.dashboard.model.RolePermission;
import vn.com.vng.stats.dashboard.model.User;
import vn.com.vng.stats.dashboard.model.UserAccount;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

import static vn.com.vng.stats.dashboard.constant.AppConstantValue.*;

/**
 * Created by tringuyen on 10/20/15.
 */
@Repository("securityDao")
public class UserDaoImpl extends BaseDaoImpl implements UserDao {

    private static final Logger logger = Logger.getLogger(UserDaoImpl.class.getName());

    @Autowired
    @Qualifier("jdbcWrite")
    private JdbcTemplate jdbcWrite;

    @Override
    public UserAccount loadUserByUsername(String username) throws Exception {
        if (ADMIN_DEFAULT.containsKey(username) || isContains(username)) {
            UserAccount securityUser = new UserAccount(null, username, DEFAULT_PASS,
                    false, false, false, true,
                    getRoleByUserName(username));
            return securityUser;
        } else {
            throw new UsernameNotFoundException("Username: " + username + " not found. Please try again.");
        }

    }

    @Override
    public List<User> getUsers() throws Exception {
        String sql = "select id, domain_name as domainName, create_date as createDate, permission from user";
        return queryForList(getConnection(jdbcWrite), sql, User.class);
    }

    @Override
    public boolean isContains(String userForm) throws Exception {
        String sql = "select count(*) as 'count' from user WHERE `domain_name` = '" + userForm + "'";

        return (getConnection(jdbcWrite)
                .query(sql, (rs, rownum) -> rs.getLong("count"))
                .stream().findFirst().orElse(null)) != 0;
    }

    @Override
    public void addNewUser(UserForm userForm) throws Exception {
        List<UserForm> userForms = new ArrayList<UserForm>() {{
            add(userForm);
        }};

        String insertTable = "INSERT INTO user(domain_name, create_date, permission ) VALUES (?, now() ,?)";

        final int batchSize = 500;

        for (int j = 0; j < userForms.size(); j += batchSize) {
            final List<UserForm> batchList = userForms.subList(j, j + batchSize > userForms.size() ? userForms.size() : j + batchSize);
            getConnection(jdbcWrite).batchUpdate(insertTable,
                    new BatchPreparedStatementSetter() {
                        @Override
                        public void setValues(PreparedStatement ps, int i)
                                throws SQLException {
                            UserForm userForm = batchList.get(i);
                            ps.setString(1, userForm.getDomainName());
                            ps.setString(2, userForm.getPermission());
                        }

                        @Override
                        public int getBatchSize() {
                            return batchList.size();
                        }
                    });
        }

    }

    @Override
    public boolean updateUser(UserForm userForm) {
        String updateTable = "UPDATE user  SET desc = ?, tool_source = ?, diff = ? WHERE report_date = ? and  game_code = ? and kpi_id = ? ";
        return false;
    }

    @Override
    public boolean removeUser(UserForm userForm) throws Exception {
        List<UserForm> userForms = new ArrayList<UserForm>() {{
            add(userForm);
        }};
        String insertTable = "DELETE FROM user  WHERE `domain_name` = ?";

        final int batchSize = 500;

        for (int j = 0; j < userForms.size(); j += batchSize) {
            final List<UserForm> batchList = userForms.subList(j, j + batchSize > userForms.size() ? userForms.size() : j + batchSize);
            getConnection(jdbcWrite).batchUpdate(insertTable,
                    new BatchPreparedStatementSetter() {
                        @Override
                        public void setValues(PreparedStatement ps, int i)
                                throws SQLException {
                            UserForm userForm = batchList.get(i);
                            ps.setString(1, userForm.getDomainName());
                        }

                        @Override
                        public int getBatchSize() {
                            return batchList.size();
                        }
                    });

        }

        return true;

    }

    private Set<RolePermission> getRoleByUserName(String userName) throws Exception {
        Set<RolePermission> roles = new HashSet<>();

        if (ADMIN_DEFAULT.containsKey(userName)) {
            RolePermission rolePermission = new RolePermission(1l, "ROLE_" + ADMIN_DEFAULT.get(userName).getPermission());
            roles.add(rolePermission);
        } else {
            String sql = "select permission as name from user WHERE `domain_name` = '" + userName + "'";
            List<RolePermission> rolePermissions = queryForList(getConnection(jdbcWrite), sql, RolePermission.class);
            for (int i = 0; i < rolePermissions.size(); i++) {
                rolePermissions.get(i).setId((new Long(i) + 1));
                rolePermissions.get(i).setName("ROLE_" + rolePermissions.get(i));
                roles.add(rolePermissions.get(i));
            }
        }

        return roles;

    }

}
