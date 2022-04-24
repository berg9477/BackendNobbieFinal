package nl.novi.nobbie.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import javax.sql.DataSource;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private DataSource dataSource;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .jdbcAuthentication()
                .passwordEncoder(new BCryptPasswordEncoder())
                .dataSource(dataSource)
                .usersByUsernameQuery("select username, password, enabled from users where username=?")
                .authoritiesByUsernameQuery("select username, role from users where username=?");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .httpBasic()
                .and()
                .authorizeRequests()
                .antMatchers(HttpMethod.POST,"/babyNames").hasAuthority("0")
                .antMatchers(HttpMethod.GET,"/socialMediaAccounts").hasAuthority("0")
                .antMatchers(HttpMethod.GET, "/babies").hasAuthority("0")
                .antMatchers(HttpMethod.DELETE,"/user").hasAuthority("0")
                .antMatchers("/users").hasAuthority("0")
                .antMatchers("/resetPassword").hasAuthority("0")
                .antMatchers("/socialMediaMessage").hasAuthority("1") //Only service the Admin can't use
                .antMatchers("/**").hasAnyAuthority("0", "1")
                .anyRequest()
                .authenticated()
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) ;
    }


}
