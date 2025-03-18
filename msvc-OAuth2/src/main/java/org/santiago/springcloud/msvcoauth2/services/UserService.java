package org.santiago.springcloud.msvcoauth2.services;

import org.santiago.springcloud.libs.msvccommons.entities.Usager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class UserService implements UserDetailsService {
        //Atributos de UserService
    private final WebClient.Builder webClient;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

        //Constructores de UserService
    public UserService(WebClient.Builder webClient) {
        this.webClient = webClient;
    }

    //Asignadores de atributos de UserService (setters)
    //Lectores de atributos de UserService (getters)
        //Métodos de UserService
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Map<String, String> params = new HashMap<>();
            params.put("username", username);
        try {
                //Obtener el usuario de base de datos con WebClient
            this.logger.info("Ingresando al método UserService::loadUserByUsername");
            Usager usager = this.webClient.build()
                .get()
                .uri("/api/usagers/username/{username}", params)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(Usager.class)
                .block();

                //Transformar los roles en una colección de SimpleGrantedAuthority
            /*assert usager != null;*/
            this.logger.info("En el método UserService::loadUserByUsername, cargando los roles del usuario autenticado");
            List<SimpleGrantedAuthority> authorities = usager.getRoles()
                .stream()
                .map(r -> new SimpleGrantedAuthority(r.getName().toString()))
                .toList();
                    /*.collect(Collectors.toList());*/

            this.logger.info("En el método UserService::loadUserByUsername, vamos a comparar contraseñas");
                //Devolver un User de Spring Security
            return new User(
                usager.getUsername(),
                usager.getPassword(),
                usager.isEnabled(),
                true,
                true,
                true,
                authorities
            );
        } catch (WebClientResponseException /*| AssertionError*/ e) {
            String errorMessage = String.format("Error de inicio de sesión: el usuario '%s' no existe en nuestro sistema", username);
            this.logger.error(errorMessage);
            throw new UsernameNotFoundException(errorMessage);
        }
    }
}
