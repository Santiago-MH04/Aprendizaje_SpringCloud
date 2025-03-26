package org.santiago.springcloud.gatewayserver.app;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.ServerRequest;
import org.springframework.web.servlet.function.ServerResponse;

import static org.springframework.cloud.gateway.server.mvc.handler.GatewayRouterFunctions.route;
import static org.springframework.cloud.gateway.server.mvc.handler.HandlerFunctions.http;
import static org.springframework.cloud.gateway.server.mvc.filter.BeforeFilterFunctions.stripPrefix;
import static org.springframework.cloud.gateway.server.mvc.filter.CircuitBreakerFilterFunctions.circuitBreaker;
import static org.springframework.cloud.gateway.server.mvc.filter.LoadBalancerFilterFunctions.lb;
import static org.springframework.cloud.gateway.server.mvc.predicate.GatewayRequestPredicates.path;

@Configuration
public class RouterConfig {
    //Atributos de RouterConfig
    //Constructores de RouterConfig
    //Asignadores de atributos de RouterConfig (setters)
    //Lectores de atributos de RouterConfig (getters)
        //Métodos de RouterConfig
    @Bean
    RouterFunction<ServerResponse> productsRouterConfig(){
        return route("msvc-products")
            .route(
                path("/api/products/**"),
                http()
            )
                //Para un filtro personalizado PRE-handle
            .filter((request, next) -> {
                ServerRequest modifiedRequest = ServerRequest.from(request)
                    .header("request-personalised-message", "Mira este mensaje personalizado en Gateway MVC")
                    .build();
                    //Devolver un filtro personalizado POST-handle
                ServerResponse response = next.handle(modifiedRequest);
                    response.headers().add("response-personalised-message", "Aquí te respondo con otro mensajito");

                return response;
            })
            .filter(lb("msvc-products"))
            .filter(circuitBreaker(config -> config.setId("products")
                .setStatusCodes(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()))
                .setFallbackPath("forward:api/items/4")
            ))
            /*.before(stripPrefix(2))*/     //Esto se deja como mero ejemplo ilustrativo
            .build();
    }
}
