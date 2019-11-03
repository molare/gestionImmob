package com.immo.webConfig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;



/**
 * Created by MORY on 31/03/2019.
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true,securedEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private AuthenticationManagerBuilder auth;
   /* @Autowired
    private UserService userService;
    @Autowired
    public UserDetailsService userDetailsService;*/

  @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //super.configure(auth);
        auth.inMemoryAuthentication().withUser("admin").password("{noop}1234").roles("ADMIN","USER");
        auth.inMemoryAuthentication().withUser("mory").password("{noop}12345").roles("USER");
     // auth.userDetailsService(userDetailsService()).passwordEncoder(passwordEncoder());

    /*  auth.jdbcAuthentication().dataSource(dataSource)
              .usersByUsernameQuery("SELECT username AS principal, password AS credentials,active FROM users " +
                      "WHERE username = ? AND active is true")
              .authoritiesByUsernameQuery("SELECT u.username, r.name FROM users u, role r " +
                      "WHERE u.role_id = r.id " +
                      "AND u.username=?").rolePrefix("ROLE_");*/


  }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //super.configure(http);
        http.csrf().disable()
                .authorizeRequests()//.anyRequest().hasAnyRole("ADMIN", "USER")
                .antMatchers("/resources/**","/html/**","/dist/**","/assets/**","/angular/**","/src/**","/tools.gulp/**","allRessources/**", "/documentaion/**", "/css/**", "/js/**","/jsp/**", "/font-awesome-master/**", "/plugins/**","/images/**","/scss/**","/static/**").permitAll()
                .anyRequest()
                .authenticated()
                .and().
                formLogin().loginPage("/login")
                .loginProcessingUrl("/login")
                .permitAll().defaultSuccessUrl("/home", true)
                .failureForwardUrl("/login");
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        //super.configure(web);"
        web.ignoring().antMatchers("/resources/**","/html/**","/dist/**","/assets/**","/angular/**","/src/**","/tools.gulp/**","/documentaion/**","allRessources/**","/css/**","/js/**","jsp/**","/font-awesome-master/**","/plugins/**","/images/**","/scss/**","/static/**");
    }
}
