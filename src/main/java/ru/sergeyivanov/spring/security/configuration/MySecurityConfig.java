package ru.sergeyivanov.spring.security.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.User.UserBuilder;

import javax.sql.DataSource;

@EnableWebSecurity // is configuration
public class MySecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    DataSource dataSource;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
// in the DB all fields and all columns have assign names. So Spring works with DB independently
        auth.jdbcAuthentication().dataSource(dataSource);


        //this is configuration for spring security for saving data to memory of class

//        UserBuilder userBuilder = User.withDefaultPasswordEncoder();

//        auth.inMemoryAuthentication()
//                .withUser(userBuilder
//                        .username("Sergey")
//                        .password("jhbjhj")
//                        .roles("EMPLOYEE"))
//                .withUser(userBuilder
//                        .username("Elena")
//                        .password("jhbjhj")
//                        .roles("HR"))
//                .withUser(userBuilder
//                        .username("Ivan")
//                        .password("jhbjhj")
//                        .roles("MANAGER", "HR"));
    }

    @Override
    //this is configuration for allocate roles for access by button
    protected void configure(HttpSecurity http) throws Exception {
            // here we can give permission to roles
        http.authorizeRequests()
                .antMatchers("/").hasAnyRole("EMPLOYEE", "MANAGER", "HR")
                .antMatchers("/hr_info").hasRole("HR")
                .antMatchers("/manager_info/**").hasRole("MANAGER") // /** - any URL which begins with /manager_info
                .and().formLogin().permitAll(); // form request login and password will be requesting from all
    }
}
