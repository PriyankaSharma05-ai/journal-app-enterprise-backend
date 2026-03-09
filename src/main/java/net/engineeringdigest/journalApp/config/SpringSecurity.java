package net.engineeringdigest.journalApp.config;

import net.engineeringdigest.journalApp.service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SpringSecurity {

    @Autowired
    private final UserDetailsServiceImpl userDetailsService;
    public SpringSecurity(UserDetailsServiceImpl userDetailsService) {

        this.userDetailsService = userDetailsService;
    }

    // 🔐 Security filter chain
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/journal/**").authenticated() // login required
                        .anyRequest().permitAll() // allow others
                )
                .httpBasic(Customizer.withDefaults()); // basic auth
        http
                .sessionManagement(session -> session    //STATELESS session → “Every request is independent; server doesn’t remember users.
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)  // set stateless
                )
                .csrf(csrf -> csrf.disable()); //csrf().disable() → “No need for CSRF protection in stateless APIs.”
                                                                        //http.csrf().disable(); turns off CSRF protection in your Spring app.
                                                                        // CSRF (Cross-Site Request Forgery) is a security feature that prevents other sites from tricking a logged-in user into doing unwanted actions.
        return http.build();
    }

    // 🔑 Password Encoder
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();   //BCryptPasswordEncoder is used to securely hash passwords before storing them so they can’t be read in plain text.
    }

    // 🔓 Authentication Provider
    //DaoAuthenticationProvider is a Spring Security class that checks user login by comparing submitted credentials with user data from a database or custom UserDetailsService.
    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    // 🔓 Authentication Manager
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

}

