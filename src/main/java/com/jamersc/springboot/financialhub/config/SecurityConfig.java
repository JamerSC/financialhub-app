package com.jamersc.springboot.financialhub.config;

import com.jamersc.springboot.financialhub.service.LoginUserDetailsService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class SecurityConfig {

    @Autowired
    private LoginUserDetailsService userDetailsService;

    @Autowired
    private PasswordEncoderConfig passwordEncoder;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.authorizeHttpRequests(authorizeRequests ->
                        authorizeRequests
                                .requestMatchers("/css/**", "/js/**").permitAll()
                                .requestMatchers("/financial-hub/dashboard").hasRole("EMPLOYEE")
                                .anyRequest().authenticated()
                )
                .formLogin(formLogin ->
                        formLogin
                                .loginPage("/financial-hub/login") // Custom login page
                                .loginProcessingUrl("/authenticate") // URL to process login form submission
                                .defaultSuccessUrl("/financial-hub/dashboard", true) // Redirect to dashboard on successful login
                                .permitAll()
                )
                .logout(logout -> logout
                        .permitAll()
                        .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                        .logoutSuccessUrl("/financial-hub/login")
                )
                .exceptionHandling(configurer ->
                        configurer.accessDeniedPage("/financial-hub/access-denied")
                );
        return http.build();
    }

    /*Recommendation - Default Behavior: If you're primarily using UserDetailsService for authentication, it's usually best
    to rely on Spring Security's default behavior without defining an AuthenticationProvider bean explicitly.*/

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder.passwordEncoder());
        return authProvider;
    }

}
