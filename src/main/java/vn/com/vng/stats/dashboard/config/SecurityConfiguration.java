package vn.com.vng.stats.dashboard.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.web.filter.CharacterEncodingFilter;
import vn.com.vng.stats.dashboard.authentication.Md5PasswordEncoderModify;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {


    @Autowired
    @Qualifier("myUserDetailsService")
    private UserDetailsService userService;

    @Bean(name = "md5PasswordEncoder")
    public Md5PasswordEncoder passwordEncoder() {
        return new Md5PasswordEncoderModify();
    }

    @Autowired
    private Md5PasswordEncoder passwordEncoder;

    @Autowired
    public void configureGlobalSecurity(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService).passwordEncoder(passwordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter();
        characterEncodingFilter.setEncoding("UTF-8");
        characterEncodingFilter.setForceEncoding(true);
        http.addFilterBefore(characterEncodingFilter, CsrfFilter.class);

        http.authorizeRequests()
                .antMatchers(  "/authen", "/login", "/resources/**").permitAll()
                .antMatchers("/**").access("hasRole('ROLE_ADMIN') or hasRole('ROLE_INTERN')")
                .and().formLogin().loginPage("/login")
                .and().exceptionHandling().accessDeniedPage("/authen");

    }
}
