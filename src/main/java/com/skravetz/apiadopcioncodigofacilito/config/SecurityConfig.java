package com.skravetz.apiadopcioncodigofacilito.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public UserDetailsManager userDetailsManager(DataSource dataSource,
                                                 PasswordEncoder encoder) {
        JdbcUserDetailsManager jdbcUserDetailsManager =
            new JdbcUserDetailsManager(dataSource);

        if (!jdbcUserDetailsManager.userExists("api-admin")) {
            UserDetails adminUser = User.builder()
                                        .username("api-admin")
                                        .password(encoder.encode(
                                            "!!4dm1n@api-adopcion"))
                                        .roles("ADMIN")
                                        .build();
            jdbcUserDetailsManager.createUser(adminUser);
        }

        return jdbcUserDetailsManager;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
            .csrf(AbstractHttpConfigurer::disable)
            .authorizeHttpRequests(auth ->
                                       auth
                                           .requestMatchers("/public/**")
                                           .permitAll()
                                           .requestMatchers("/private/**")
                                           .authenticated()
                                           .anyRequest().authenticated())
            .httpBasic(Customizer.withDefaults());

        return httpSecurity.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}

