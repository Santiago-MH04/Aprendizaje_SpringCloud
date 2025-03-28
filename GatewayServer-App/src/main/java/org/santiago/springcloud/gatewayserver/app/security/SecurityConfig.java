package org.santiago.springcloud.gatewayserver.app.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.context.NoOpServerSecurityContextRepository;
import reactor.core.publisher.Mono;

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
    SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) throws Exception {
        return http.authorizeExchange(auth -> {
            auth.pathMatchers("/authorized", "/logout").permitAll()
                .pathMatchers(HttpMethod.GET, "/api/products", "/api/items", "/api/usagers").permitAll()
                .pathMatchers(HttpMethod.GET, "/api/products/{id}", "/api/items/{id}", "/api/usagers/{id}").hasAnyRole("ADMIN", "USER")/*.hasAnyAuthority("SCOPE_read", "SCOPE_write")*/
                .pathMatchers("/api/products/**", "/api/items/**", "/api/usagers/**").hasRole("ADMIN")/*.hasAuthority("SCOPE_write")*/
                .anyExchange().authenticated();
        })
        .cors(ServerHttpSecurity.CorsSpec::disable) //También se puede con la expresión lambda
        .securityContextRepository(NoOpServerSecurityContextRepository.getInstance())   //Para ahorrarnos el manejo de sesiones
        .oauth2Login(withDefaults())
        .oauth2Client(withDefaults())
        .oauth2ResourceServer(oauth2 -> oauth2.jwt(
            /*withDefaults()*/  //Para agregar los roles del usuario autenticado
            jwt -> jwt.jwtAuthenticationConverter(new Converter<Jwt, Mono<? extends AbstractAuthenticationToken>>() {
                @Override
                public Mono<? extends AbstractAuthenticationToken> convert(Jwt source) {
                    Collection<String> roles = source.getClaimAsStringList("roles"); //Debe ser el mismo claim que registramos en la configuración de OAuth2
                        //Transformar los roles en una lista de SimpleGrantedAuthority
                    List<SimpleGrantedAuthority> authorities = roles.stream()
                            .map(SimpleGrantedAuthority::new)
                            .collect(Collectors.toList());
                    return Mono.just(new JwtAuthenticationToken(source, authorities));
                }
            })
        ))
        .build();
    }
}
