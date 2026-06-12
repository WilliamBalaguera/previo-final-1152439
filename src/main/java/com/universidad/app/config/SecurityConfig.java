package com.universidad.app.config;

import com.universidad.app.security.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final CustomUserDetailsService userDetailsService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf
                .ignoringRequestMatchers("/api/**", "/h2-console/**")
            )
            // Permite que la consola H2 use iframes
            .headers(headers -> headers.frameOptions(frame -> frame.sameOrigin()))
            .authorizeHttpRequests(auth -> auth
                // Consola H2 libre (solo desarrollo)
                .requestMatchers("/h2-console/**").permitAll()

                // ── Módulo 1: Mensajes ──────────────────────────────────────
                .requestMatchers("/api/mensajes/**").authenticated()

                // ── Módulo 2: Solicitudes ───────────────────────────────────
                .requestMatchers(HttpMethod.POST, "/api/solicitudes").authenticated()
                .requestMatchers(HttpMethod.GET,  "/api/solicitudes/mis-solicitudes").authenticated()
                .requestMatchers(HttpMethod.GET,  "/api/solicitudes").hasRole("ADMIN")
                .requestMatchers(HttpMethod.PUT,  "/api/solicitudes/*/aprobar").hasRole("ADMIN")
                .requestMatchers(HttpMethod.PUT,  "/api/solicitudes/*/rechazar").hasRole("ADMIN")

                // ── Módulo 3: Panel visual ──────────────────────────────────
                .requestMatchers("/admin/solicitudes/panel").hasRole("ADMIN")

                .requestMatchers("/login", "/css/**", "/js/**").permitAll()
                .anyRequest().authenticated()
            )
            .formLogin(form -> form
                .loginPage("/login")
                .defaultSuccessUrl("/", true)
                .permitAll()
            )
            .logout(logout -> logout
                .logoutSuccessUrl("/login?logout")
                .permitAll()
            );

        return http.build();
    }
}
