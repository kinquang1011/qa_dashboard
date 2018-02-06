package vn.com.vng.stats.dashboard.authentication;

import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import vn.com.vng.stats.dashboard.service.impl.UserDetailsServiceImpl;

import java.util.logging.Logger;

import static vn.com.vng.stats.dashboard.constant.AppConstantValue.DEFAULT_USER;

public class Md5PasswordEncoderModify extends Md5PasswordEncoder {
    private static final Logger logger = Logger.getLogger(Md5PasswordEncoderModify.class.getName());

    public Md5PasswordEncoderModify() {
        super();
    }

    @Override
    public boolean isPasswordValid(String encPass, String rawPass, Object salt) {
        return true;
    }


}
