package org.serratec.backend.config;

import org.serratec.backend.security.JwtAuthenticationFilter;
import org.serratec.backend.security.JwtAuthorizationFilter;
import org.serratec.backend.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Arrays;

@EnableWebSecurity
@Configuration
public class SecurityConfig {
    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserDetailsService userDetailsService;

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .authorizeHttpRequests(requests -> requests

                        .requestMatchers("/public/**").permitAll()
                        .requestMatchers("/h2-console/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/login").permitAll()

                        // Regras para ADMIN
                        .requestMatchers("/carrinhos/**").hasRole("ADMIN")
                        .requestMatchers("/categorias/**").hasRole("ADMIN")
                        .requestMatchers("/clientes/**").hasRole("ADMIN")
                        .requestMatchers("/enderecos/**").hasRole("ADMIN")
                        .requestMatchers("/funcionarios/**").hasRole("ADMIN")
                        .requestMatchers("/pedidos/**").hasRole("ADMIN")
                        .requestMatchers("/produtos/**").hasRole("ADMIN")

                        // Regras para CLIENTE
                        .requestMatchers("/carrinhos/**").hasRole("CLIENTE") // CLIENTE pode "tudo de /carrinhos"
                        .requestMatchers(HttpMethod.POST, "/clientes").hasRole("CLIENTE") // Inserir cliente
                        .requestMatchers(HttpMethod.PUT, "/clientes").hasRole("CLIENTE") // Atualizar cliente
                        .requestMatchers(HttpMethod.GET, "/pedidos").hasRole("CLIENTE") // Abrir pedido (GET para visualizar, assumindo "abrir" como visualizar)
                        .requestMatchers(HttpMethod.DELETE, "/pedidos").hasRole("CLIENTE") // Cancelar pedido

                        // Regras para FUNCIONARIO
                        .requestMatchers("/produtos/**").hasRole("FUNCIONARIO") // Tudo de produtos
                        .requestMatchers(HttpMethod.PATCH, "/pedidos").hasRole("FUNCIONARIO") // Atualizar pedidos (PATCH)
                        .requestMatchers("/clientes/**").hasRole("FUNCIONARIO") // Tudo de clientes
                        .requestMatchers("/enderecos/**").hasRole("FUNCIONARIO") // Tudo de enderecos

                        .anyRequest().authenticated()
                )
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .headers(headers -> headers.frameOptions().disable());

        http.addFilterBefore(new JwtAuthenticationFilter(
                        authenticationManager(http.getSharedObject(AuthenticationConfiguration.class)), jwtUtil),
                UsernamePasswordAuthenticationFilter.class);

        http.addFilterBefore(new JwtAuthorizationFilter(
                        authenticationManager(http.getSharedObject(AuthenticationConfiguration.class)), jwtUtil, userDetailsService),
                UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
            throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.setAllowedOrigins(Arrays.asList("http://localhost:5173", "http://localhost:2000"));
        corsConfiguration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS", "HEAD"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfiguration.applyPermitDefaultValues());
        return source;
    }

}