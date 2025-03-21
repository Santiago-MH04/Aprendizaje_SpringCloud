package org.santiago.springcloud.gatewayserver.app.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.security.web.SecurityFilterChain;

/*import org.springframework.security.web.server.SecurityWebFilterChain;*/
import org.springframework.security.web.server.context.NoOpServerSecurityContextRepository;

/*import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;*/

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
public class SecurityConfig {
    //Atributos de SecurityConfig
    //Constructores de SecurityConfig
    //Asignadores de atributos de SecurityConfig (setters)
    //Lectores de atributos de SecurityConfig (getters)
        //Métodos de SecurityConfig
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http.authorizeHttpRequests(auth -> {
            auth.requestMatchers("/authorized", "/logout").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/products", "/api/items", "/api/users").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/products/{id}", "/api/items/{id}", "/api/users/{id}").hasAnyRole("ADMIN", "USER")
                .requestMatchers("/api/products/**", "/api/items/**", "/api/users/**").hasRole("ADMIN")
                .anyRequest().authenticated();
        })
            //Para el manejo de sesiones
        .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .csrf(csrf -> csrf.disable())
        .oauth2Login(login -> login.loginPage("/oauth2/authorization/client-app"))
        .oauth2Client(withDefaults())
        .oauth2ResourceServer(oauth2 -> oauth2.jwt(
            jwt -> jwt.jwtAuthenticationConverter(source -> {
                Collection<String> roles = source.getClaimAsStringList("roles"); //Debe ser el mismo claim que registramos en la configuración de OAuth2
                    //Transformar los roles en una lista de SimpleGrantedAuthority
                List<SimpleGrantedAuthority> authorities = roles.stream()
                    .map(SimpleGrantedAuthority::new)
                    .toList();
                return new JwtAuthenticationToken(source, authorities);
            })
        ))
        .build();
    }
}
