package com.gabrielsmm.springjwtboilerplate.config;

import com.gabrielsmm.springjwtboilerplate.security.JWTAuthenticationFilter;
import com.gabrielsmm.springjwtboilerplate.security.JWTAuthorizationFilter;
import com.gabrielsmm.springjwtboilerplate.security.JWTUtil;
import lombok.AllArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@AllArgsConstructor
public class SecurityConfig {

    private final JWTUtil jwtUtil;

    private final UserDetailsService userDetailsService;

    private static final String[] PUBLIC_MATCHERS_GET = {

    };

    private static final String[] PUBLIC_MATCHERS_POST = {

    };

    /**
     * Configura a segurança da aplicação.
     * - Define sessões sem estado, permite requisições públicas específicas
     *   e adiciona filtros para autenticação/autorização via JWT.
     */
    @Bean
    SecurityFilterChain springWebFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(Customizer.withDefaults())
                .headers(headers -> headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin))
                .sessionManagement(c -> c.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests((req) -> req
                        .requestMatchers(PathRequest.toH2Console()).permitAll()
                        .requestMatchers(HttpMethod.GET, PUBLIC_MATCHERS_GET).permitAll()
                        .requestMatchers(HttpMethod.POST, PUBLIC_MATCHERS_POST).permitAll()
                        .anyRequest().authenticated())
                .addFilter(new JWTAuthenticationFilter(authenticationManager(http.getSharedObject(AuthenticationConfiguration.class)), jwtUtil))
                .addFilter(new JWTAuthorizationFilter(authenticationManager(http.getSharedObject(AuthenticationConfiguration.class)), jwtUtil, userDetailsService))
                .build();
    }

    /**
     * Configura as regras de CORS para a aplicação.
     * - Permite requisições de qualquer origem e métodos HTTP definidos.
     * - Garante compatibilidade com front-ends em outros domínios.
     */
    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration().applyPermitDefaultValues();
        configuration.setAllowedOrigins(Arrays.asList("*"));
        configuration.setAllowedMethods(Arrays.asList("POST", "GET", "PUT", "PATCH", "DELETE", "OPTIONS"));
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    /**
     * Registra o AuthenticationManager como um bean gerenciado pelo Spring.
     * - Utilizado para autenticar usuários validando suas credenciais.
     */
    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    /**
     * Registra o BCryptPasswordEncoder como um bean para criptografia de senhas.
     * - Garante a segurança das senhas armazenadas no banco de dados usando hash seguro.
     */
    @Bean
    BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
