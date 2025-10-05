package is.hi.hbv501g.verkefni.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {



    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                // Disable CSRF for JSON APIs
                .csrf(csrf -> csrf.disable())

                // Make the app stateless (for JWT)
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )

                // Define which endpoints are public and which need auth
                .authorizeHttpRequests(auth -> auth
                        // ✅ Open endpoints
                        .requestMatchers("/", "/auth/**", "/movies/**", "/h2-console/**").permitAll()

                        // Everything else requires authentication
                        .anyRequest().authenticated()
                );

        // For H2 console
        http.headers(headers -> headers.frameOptions(frame -> frame.disable()));

        return http.build();
    }
}