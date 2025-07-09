package com.springboot.tourism_agency_backend.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import com.springboot.tourism_agency_backend.security.JwtFilter;

@Configuration
@EnableWebSecurity

public class SecurityConfig {
    @Autowired
    private UserDetailsService myUserDetailsService;

    @Autowired
    private JwtFilter jwtFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
            .csrf(csrf -> csrf.disable())
            .cors(Customizer.withDefaults())
            .authorizeHttpRequests(request -> request
                .requestMatchers("/auth/authenticate", "/auth/register", "/touristServices", "/touristServices/image/**")
                .permitAll()

                .requestMatchers("/paymentMethods", "/tourPackages").hasAnyRole("ADMIN", "EMPLOYEE", "CUSTOMER")

                .requestMatchers("/customers/findByDni/**", "/employees", "/sales/find/purchases/**", 
                    "/sales/find/sale/**").hasAnyRole("ADMIN", "CUSTOMER")

                .requestMatchers("/customers").hasAnyRole("ADMIN", "EMPLOYEE")

                .requestMatchers("/sales/buyServiceOrPackage").hasAnyRole("CUSTOMER", "EMPLOYEE")

                .requestMatchers("/customers/updateInfo", "/sales/delete/**").hasRole("CUSTOMER")

                .requestMatchers("/employees/find/byDni/**", "/employees/updateInfo", 
                    "/sales/find/sales/**").hasRole("EMPLOYEE")
                
                .requestMatchers("/admins/**", "/customers/**", "/employees/**", "/jobTitles/**", 
                    "/paymentMethods/**", "/touristServices/**", "/tourPackages/**", "/sales/**").hasRole("ADMIN")
            )
            .httpBasic(Customizer.withDefaults())
            .addFilterBefore(this.jwtFilter, UsernamePasswordAuthenticationFilter.class)
            .sessionManagement(session -> session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            )
            .build();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(12);
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();

        provider.setPasswordEncoder(passwordEncoder());
        provider.setUserDetailsService(this.myUserDetailsService);

        return provider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}
