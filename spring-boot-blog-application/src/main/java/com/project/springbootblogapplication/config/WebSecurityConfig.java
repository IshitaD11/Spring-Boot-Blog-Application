package com.project.springbootblogapplication.config;

import com.project.springbootblogapplication.models.AuthorityType;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

import static org.springframework.security.config.Customizer.withDefaults;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;


@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true)
public class WebSecurityConfig {

    private static final String[] WHITELIST = {
            "/",
            "/register",
            "/css/**",
            "/images/**",
            "/js/**",
            "/search"
    };


    @Bean
    public static PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        //.requestMatchers(HttpMethod.GET,"/posts/*").permitAll() only allows get method in posts
        http.authorizeHttpRequests(authz -> authz
                .requestMatchers(WHITELIST).permitAll()
                .requestMatchers(HttpMethod.GET,"/posts/*").permitAll()
//                .requestMatchers(HttpMethod.GET,"/").permitAll()
                .requestMatchers(HttpMethod.POST,"/upload-image/*").permitAll()
                .requestMatchers("/posts/new").hasRole("ADMIN")
                .requestMatchers(HttpMethod.GET,"/search/*").permitAll()
                .anyRequest().authenticated());

        // for login handling: authentication and authorization. create default interfaces
        http.formLogin(formLogin ->
                formLogin
                        .loginPage("/login")
                        .loginProcessingUrl("/login")
                        .usernameParameter("email")
                        .passwordParameter("password")
                        .defaultSuccessUrl("/",true)
                        .failureUrl("/login?error")
                        .permitAll())
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/logout-success") // modified from /login?logout
                        .permitAll())
                .httpBasic(withDefaults())
                .csrf(withDefaults());;


        return http.build();
    }
}
