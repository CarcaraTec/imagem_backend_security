package com.carcara.imagem_backend_security.infra.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.security.web.util.matcher.AntPathRequestMatcher.antMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfigurations {

    @Autowired
    SecurityFilter securityFilter;

    private static final String[] WHITELIST_SWAGGER = {
            "/api/v1/auth/",
            "/v3/api-docs/**",
            "/v3/api-docs.yaml",
            "/swagger-ui/**",
            "/swagger-ui/index.html",
            "/swagger-ui.html"
    };

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return  httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authorize -> {
                    for (String url : WHITELIST_SWAGGER) {
                        authorize.requestMatchers(antMatcher(url)).permitAll();
                    }
                    authorize
                            .requestMatchers(HttpMethod.POST, "/auth/login").permitAll()
                            .requestMatchers(HttpMethod.POST, "/auth/register").permitAll()
                            .requestMatchers(HttpMethod.GET, "/user/dadosPessoaisUsuarios").hasAnyRole("USER", "ADMIN")
                            .requestMatchers(HttpMethod.GET, "/user/usuariosStatusAguardando").hasRole("ADMIN")
                            .requestMatchers(HttpMethod.PUT, "/user/updateStatusAceito").hasRole("ADMIN")
                            .requestMatchers(HttpMethod.PUT, "/user/updateStatusRecusado").hasRole("ADMIN")
                            .requestMatchers(HttpMethod.PUT, "/user/updateUsuario").hasAnyRole("USER", "ADMIN")
                            .requestMatchers(HttpMethod.GET, "/user/buscar/").hasAnyRole("ADMIN", "USER")
                            .requestMatchers(HttpMethod.GET, "/user/listar/").hasAnyRole("ADMIN", "USER")
                            .anyRequest().authenticated();
                })
                .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

   

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
