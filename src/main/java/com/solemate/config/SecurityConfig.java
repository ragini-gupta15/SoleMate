package com.solemate.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http) throws Exception {

        http
            .authorizeHttpRequests(auth -> auth

                // ==============================
                // PUBLIC FRONTEND PAGES
                // ==============================
                .requestMatchers(
                    "/",
                    "/index.html",
                    "/shop.html",
                    "/product.html",
                    "/cart.html",
                    "/checkout.html",
                    "/order-success.html",
                    "/my-orders.html",
                    "/track-order.html",
                    "/about.html",
                    "/admin-login.html"
                ).permitAll()

                // ==============================
                // PUBLIC STATIC RESOURCES
                // ==============================
                .requestMatchers(
                    "/css/**",
                    "/js/**",
                    "/images/**",
                    "/favicon.ico"
                ).permitAll()

                // ==============================
                // PUBLIC PRODUCT APIs
                // ==============================
                .requestMatchers(
                    HttpMethod.GET,
                    "/api/products/**"
                ).permitAll()

                // ==============================
                // ADMIN PRODUCT APIs
                // ==============================
                .requestMatchers(
                    HttpMethod.POST,
                    "/api/products"
                ).hasRole("ADMIN")

                .requestMatchers(
                    HttpMethod.PUT,
                    "/api/products/**"
                ).hasRole("ADMIN")

                .requestMatchers(
                    HttpMethod.DELETE,
                    "/api/products/**"
                ).hasRole("ADMIN")

                // ==============================
                // CUSTOMER ORDER APIs
                // ==============================
                .requestMatchers(
                    HttpMethod.POST,
                    "/api/orders"
                ).permitAll()

                .requestMatchers(
                    HttpMethod.GET,
                    "/api/orders/customer"
                ).permitAll()

                .requestMatchers(
                    HttpMethod.GET,
                    "/api/orders/*"
                ).permitAll()

                // ==============================
                // ADMIN ORDER APIs
                // ==============================
                .requestMatchers(
                    HttpMethod.GET,
                    "/api/orders"
                ).hasRole("ADMIN")

                .requestMatchers(
                    HttpMethod.PUT,
                    "/api/orders/*/status"
                ).hasRole("ADMIN")

                // ==============================
                // PUBLIC REVIEW APIs
                // ==============================
                .requestMatchers(
                    "/api/reviews/**"
                ).permitAll()

                // ==============================
                // ADMIN APIs
                // ==============================
                .requestMatchers(
                    "/admin.html",
                    "/api/admin/**"
                ).hasRole("ADMIN")

                // ==============================
                // EVERYTHING ELSE
                // ==============================
                .anyRequest().permitAll()
            )

            // ==============================
            // CUSTOM LOGIN
            // ==============================
            .formLogin(form -> form
                .loginPage("/admin-login.html")
                .loginProcessingUrl("/login")
                .defaultSuccessUrl("/admin.html", true)
                .failureUrl("/admin-login.html?error=true")
                .permitAll()
            )

            // ==============================
            // LOGOUT
            // ==============================
            .logout(logout -> logout
                .logoutSuccessUrl("/")
                .permitAll()
            )

            // ==============================
            // API-FRIENDLY CSRF CONFIG
            // ==============================
            .csrf(csrf -> csrf.disable());

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {

        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService(
            PasswordEncoder passwordEncoder,
            @Value("${SOLEMATE_ADMIN_USERNAME}")
            String username,
            @Value("${SOLEMATE_ADMIN_PASSWORD}")
            String password) {

        UserDetails admin = User
                .withUsername(username)
                .password(
                    passwordEncoder.encode(password)
                )
                .roles("ADMIN")
                .build();

        return new InMemoryUserDetailsManager(admin);
    }
}