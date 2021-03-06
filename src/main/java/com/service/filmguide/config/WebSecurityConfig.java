package com.service.filmguide.config;

import com.service.filmguide.controller.authentication.security.JwtTokenAuthenticationFilter;
import com.service.filmguide.controller.authentication.security.RestAuthenticationEntryPoint;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.http.Cookie;

@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableWebSecurity
@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter{
    
    @Autowired
    private UserDetailsService userService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtTokenAuthenticationFilter jwtTokenAuthenticationFilter;
    @Autowired
    private RestAuthenticationEntryPoint restAuthenticationEntryPoint;

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception{
        http//.exceptionHandling().authenticationEntryPoint(restAuthenticationEntryPoint).and()
            .cors()
            .and()
            .httpBasic().disable()
            .csrf().disable()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
                .authorizeRequests()
                .antMatchers(HttpMethod.POST, "/auth/login").permitAll()
                .antMatchers(HttpMethod.POST, "/auth/register").permitAll()
                .antMatchers(HttpMethod.POST, "/auth/refresh/token").permitAll()
                .antMatchers(HttpMethod.GET, "/movie/trending").permitAll()
                .antMatchers(HttpMethod.GET, "/movie/top_rated").permitAll()
                .antMatchers(HttpMethod.GET, "/movie/upcoming").permitAll()
                .antMatchers(HttpMethod.GET, "/movie/now_playing").permitAll()
                .and()
                .authorizeRequests()
                .antMatchers(HttpMethod.GET, "/user").hasAuthority("admin")
                .antMatchers(HttpMethod.GET, "/user/{username}").hasAuthority("user")
                .anyRequest().authenticated();
                
            
        http.addFilterBefore(jwtTokenAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
    }

    @Override
    public void configure(WebSecurity web) {
        web
            .ignoring()
            .antMatchers(HttpMethod.POST, "/auth/login") 
            .antMatchers(HttpMethod.POST, "/auth/register")
            .antMatchers(HttpMethod.POST, "/auth/refresh/token");
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService).passwordEncoder(passwordEncoder);
    }
}