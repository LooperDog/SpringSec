package ru.kata.spring.boot_security.demo.configs;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.filter.HiddenHttpMethodFilter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    private final SuccessUserHandler successUserHandler;
    private final UserDetailsService userDetailsService;
    private final AppConfig appConfig;

    public WebSecurityConfig(SuccessUserHandler successUserHandler,
                             @Qualifier("userServiceImpl") UserDetailsService userDetailsService, AppConfig appConfig) {
        this.successUserHandler = successUserHandler;
        this.userDetailsService = userDetailsService;
        this.appConfig = appConfig;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception { // конфиги в которых указывается доступы пользователей
        http
                .cors().disable()
                .csrf().disable() //  защита от CSRF-атак( типо подставного сайта где злоумышленник его использует и заставляет
                // от имени пользователя отправлять пароли, деньги со счёта на счёт и т.п
                .authorizeRequests() //авторизацуем запрос
                .antMatchers("/login", "/").permitAll()
                .antMatchers("/users/**").hasAnyRole("USER", "ADMIN") //прописываем доступ для юрл /user/**
                .antMatchers("/admin/**").hasRole("ADMIN") //прописываем доступ для юрл /admin/**
                .anyRequest().authenticated() // все запросы должны быть авторизованы и аутентифицированы
                .and()
                .formLogin() // задаю форму для ввода логина-пароля, по дефолту это "/login"
                .successHandler(successUserHandler)
                .permitAll() // доступно всем
                .and()
                .logout().permitAll(); // настройка логаута
    }
    @Bean
    protected DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(userDetailsService);
        daoAuthenticationProvider.setPasswordEncoder(appConfig.passwordEncoder());
        return daoAuthenticationProvider;
    }
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(daoAuthenticationProvider());
    }

    @Bean
    public HiddenHttpMethodFilter hiddenHttpMethodFilter() {
        return new HiddenHttpMethodFilter();
    }

}