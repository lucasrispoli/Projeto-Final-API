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
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

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
                                .requestMatchers(
                                        "/swagger-ui/**",
                                        "/v3/api-docs/**",
                                        "/swagger-resources/**",
                                        "/swagger-resources",
                                        "/configuration/ui",
                                        "/configuration/security",
                                        "/webjars/**"
                                ).permitAll()
                                .requestMatchers("/h2-console/**").permitAll()
//                        .requestMatchers("/carrinhos/**", "/clientes/**", "/categorias/**", "/enderecos/**", "/pedidos/**", "funcionarios/**", "/produtos/**", "/autorizar/**" , "/avaliacoes/**").permitAll()
                                .requestMatchers(HttpMethod.POST, "/clientes/inserir").permitAll()
                                .requestMatchers(HttpMethod.POST, "/autorizar/logar").permitAll()
                                .requestMatchers(HttpMethod.POST, "/login").permitAll()

                                // ROTAS DE CLIENTE
                                .requestMatchers(HttpMethod.GET, "/produtos/listar").hasRole("CLIENTE")
                                .requestMatchers("/carrinhos/**").hasRole("CLIENTE")
                                .requestMatchers("/clientes/**").hasRole("CLIENTE")
                                .requestMatchers(HttpMethod.POST, "/pedidos/**").hasRole("CLIENTE")
                                .requestMatchers(HttpMethod.GET, "/pedidos/**").hasRole("CLIENTE")
                                .requestMatchers(HttpMethod.DELETE, "/pedidos/**").hasRole("CLIENTE")

                                // ROTAS DE FUNCIONÁRIO
                                .requestMatchers("/produtos/**").hasRole("FUNCIONARIO")
                                .requestMatchers(HttpMethod.PATCH, "/pedidos").hasRole("FUNCIONARIO")
                                .requestMatchers("/clientes/**").hasRole("FUNCIONARIO")
                                .requestMatchers("/enderecos/**").hasRole("FUNCIONARIO")

                                // ROTAS DE ADMIN
                                .requestMatchers("/carrinhos/**").hasRole("ADMIN")
                                .requestMatchers("/categorias/**").hasRole("ADMIN")
                                .requestMatchers("/clientes/**").hasRole("ADMIN")
                                .requestMatchers("/enderecos/**").hasRole("ADMIN")
                                .requestMatchers("/funcionarios/**").hasRole("ADMIN")
                                .requestMatchers("/pedidos/**").hasRole("ADMIN")
                                .requestMatchers("/produtos/**").hasRole("ADMIN")
                                .requestMatchers("/avliacoes").hasRole("ADMIN")


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