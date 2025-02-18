package com.example.labs.Config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Value("${security.logins}")
    private List<String> login;
    @Value("${security.passwords}")
    private List<String> password;
    @Value("${security.roles}")
    private List<String> role;
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http
//                .cors(cors->cors.disable())
//                .authorizeHttpRequests((requests) -> requests
//                        .requestMatchers("/", "/home", "/login").permitAll()
//                        .requestMatchers(HttpMethod.POST, "/api/project/**").hasRole(role.get(1))
//                        .requestMatchers(HttpMethod.PUT, "/api/project/**").hasRole(role.get(1))
//                        .requestMatchers(HttpMethod.DELETE, "/api/project/**").hasRole(role.get(1))
//                        .requestMatchers("/swagger-ui.html").hasRole(role.get(1))
//                        .anyRequest().authenticated()
//                ) .formLogin((form) -> form
//                        .loginPage("/login.html")
//                        .loginProcessingUrl("/login")
//                        .defaultSuccessUrl("/api/project?search")
//                        .failureUrl("/login.html?error=true")
//                        .permitAll()
//                )
//                .logout(logout -> logout.permitAll());
//        return http.build();
        http
                .csrf((csrf) -> {csrf.disable();})
                .authorizeHttpRequests(
                        (requests) -> requests
                                .requestMatchers(HttpMethod.POST, "/projects").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.PUT, "/projects/{id}").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.DELETE, "/projects/{id}").hasRole("ADMIN")
                                .anyRequest().authenticated()
                ).httpBasic(Customizer.withDefaults());
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails user =
                User.withUsername(login.get(0))
                        .password(passwordEncoder().encode(password.get(0)))
                        .roles("USER")
                        .build();
        UserDetails admin =
                User.withUsername(login.get(1))
                        .password(passwordEncoder().encode(password.get(1)))
                        .roles("ADMIN")
                        .build();

        return new InMemoryUserDetailsManager(user, admin);
    }
}

