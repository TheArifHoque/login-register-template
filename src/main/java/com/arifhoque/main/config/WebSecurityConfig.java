package com.arifhoque.main.config;

import com.arifhoque.main.service.CustomUserDetails;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.web.util.matcher.AntPathRequestMatcher.antMatcher;

@Configuration
public class WebSecurityConfig {

    private final CustomUserDetails customUserDetails;

    public WebSecurityConfig(CustomUserDetails customUserDetails) {
        this.customUserDetails = customUserDetails;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .headers(headerConfig -> headerConfig
                        .frameOptions(HeadersConfigurer.FrameOptionsConfig::disable)
                )
                .authorizeHttpRequests(requestConfig -> requestConfig
                        .requestMatchers(antMatcher("/h2-console/**")).permitAll()
                        .requestMatchers(antMatcher("/css/**")).permitAll()
                        .requestMatchers(antMatcher("/login/**")).permitAll()
                        .requestMatchers(antMatcher("/user/register/**")).permitAll()
                        .requestMatchers(antMatcher("/user/**")).hasAnyAuthority("ROLE_USER","ROLE_ADMIN")
                        .requestMatchers(antMatcher("/admin/**")).hasAuthority("ROLE_ADMIN")
                        .anyRequest().fullyAuthenticated()
                )
                .formLogin(loginConfig -> loginConfig
                        .loginPage("/login")
                        .usernameParameter("username")
                        .passwordParameter("password")
                        .loginProcessingUrl("/login")
                        .successForwardUrl("/dashboard")
                        .failureUrl("/login?error=true")
                )
                .logout(logoutConfig -> logoutConfig
                        .logoutUrl("/logout")
                )
                .userDetailsService(customUserDetails);
        return httpSecurity.build();
    }
}
