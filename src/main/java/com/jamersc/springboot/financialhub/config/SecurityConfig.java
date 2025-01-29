package com.jamersc.springboot.financialhub.config;

import com.jamersc.springboot.financialhub.service.user.CustomUserDetailsService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class SecurityConfig {

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Autowired
    private PasswordEncoder passwordEncoder;

<<<<<<< HEAD
=======
    // Spring Security Configuration
    // Class Method Configuration for Authentication of User

>>>>>>> 8723a7db6bbf6f331c4b2199efc44cd7c212925d
    @Bean
    public AuthenticationManager authenticationManager() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService);
        authenticationProvider.setPasswordEncoder(passwordEncoder);

        ProviderManager providerManager = new ProviderManager(authenticationProvider);
        providerManager.setEraseCredentialsAfterAuthentication(false);

        return providerManager;
    }

<<<<<<< HEAD
=======
    // After the User Authentication/Logout/Error/Access Denied the User will be redirect Security Filter Chain
>>>>>>> 8723a7db6bbf6f331c4b2199efc44cd7c212925d
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.
                authorizeHttpRequests(authorizeRequests ->
                        authorizeRequests
                                .requestMatchers("/css/**", "/js/**", "/financial-hub/login").permitAll()
                                .requestMatchers("/financial-hub/dashboard").hasAnyRole("EMPLOYEE", "MANAGER", "ADMIN", "SUPER")
                                .anyRequest().authenticated()
                )
                .formLogin(formLogin ->
                        formLogin
                                .loginPage("/financial-hub/login") // Custom login page
                                .loginProcessingUrl("/authenticate") // URL to process login form submission
                                .defaultSuccessUrl("/financial-hub/dashboard", true) // Redirect to dashboard on successful login
                                .permitAll()
                )
                .logout(LogoutConfigurer::permitAll
                )
                .exceptionHandling(configurer ->
                        configurer.accessDeniedPage("/financial-hub/access-denied")
                );
        return http.build();
    }
}
