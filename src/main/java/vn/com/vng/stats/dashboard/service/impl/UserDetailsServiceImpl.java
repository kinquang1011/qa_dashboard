package vn.com.vng.stats.dashboard.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import vn.com.vng.stats.dashboard.dao.UserDao;
import vn.com.vng.stats.dashboard.model.UserAccount;


import java.util.logging.Logger;


/**
 * Use for login
 */
@Service("myUserDetailsService")
public class UserDetailsServiceImpl implements UserDetailsService {
    private static final Logger logger = Logger.getLogger(UserDetailsServiceImpl.class.getName());

    @Autowired
    private UserDao userDao;

    public static String userName;

    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {
        UserAccount user = null;
        try {
            user = userDao.loadUserByUsername(username);
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.userName = username;
        return buildLightweightCredentials(user);
    }

    private UserAccount buildLightweightCredentials(UserAccount user) {
        UserAccount newUser = new UserAccount(user.getId(), user.getUsername(), user.getPassword(),
                !user.isAccountNonExpired(), !user.isAccountNonLocked(),
                !user.isCredentialsNonExpired(), user.isEnabled(), user.getRoles());
        return newUser;
    }


}
