package uz.nt.userservice.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.configuration.EnableGlobalAuthentication;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import shared.libs.security.MyFilterChain;

@EnableWebSecurity
@RequiredArgsConstructor
@Configuration
@EnableGlobalAuthentication
public class SecurityConfiguration {

    private final MyFilterChain myFilterChain;

    @Bean
    public SecurityFilterChain httpSecurity(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/user/login/**").permitAll()
                .antMatchers(HttpMethod.POST, "/user/**").permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .addFilterBefore(myFilterChain, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}