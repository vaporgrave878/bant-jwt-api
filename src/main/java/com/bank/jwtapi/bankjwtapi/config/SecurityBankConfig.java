package com.bank.jwtapi.bankjwtapi.config;

import com.bank.jwtapi.bankjwtapi.models.Role;
import com.bank.jwtapi.bankjwtapi.security.JwtUserDetailsService;
import com.bank.jwtapi.bankjwtapi.security.jwt.JwtConfigurer;
import com.bank.jwtapi.bankjwtapi.security.jwt.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;

import static com.bank.jwtapi.bankjwtapi.models.Role.*;

@Configuration
public class SecurityBankConfig extends WebSecurityConfigurerAdapter {

    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;
    private final JwtUserDetailsService userDetailsService;

    @Autowired
    public SecurityBankConfig(JwtTokenProvider jwtTokenProvider, PasswordEncoder passwordEncoder, JwtUserDetailsService userDetailsService) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.passwordEncoder = passwordEncoder;
        this.userDetailsService = userDetailsService;
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http
                .httpBasic().disable()
                .csrf().disable()
                .sessionManagement()
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers("/register", "/login", "/pay-item/**")
                    .permitAll()
                .antMatchers("/admin/*")
                    .hasRole(String.valueOf(ADMIN))
                .antMatchers("/super_admin/*")
                    .hasRole(String.valueOf(SUPERADMIN))
                .anyRequest().authenticated()
                .and()
                .apply(new JwtConfigurer(jwtTokenProvider));

    }

    //    @Override
    //    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    //        auth.authenticationProvider(daoAuthenticationProvider());
    //    }
    //
    //    @Bean
    //    public DaoAuthenticationProvider daoAuthenticationProvider() {
    //        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
    //        provider.setPasswordEncoder(passwordEncoder);
    //        provider.setUserDetailsService(userDetailsService);
    //        return provider;
    //    }
    //
    //    @Autowired
    //    public void setApplicationContext(ApplicationContext context) {
    //        super.setApplicationContext(context);
    //        AuthenticationManagerBuilder globalAuthBuilder = context
    //                .getBean(AuthenticationManagerBuilder.class);
    //        try {
    //            globalAuthBuilder.userDetailsService(userDetailsService);
    //        } catch (Exception e) {
    //            e.printStackTrace();
    //        }
    //    }
}
