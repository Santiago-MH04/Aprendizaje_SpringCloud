package org.santiago.springcloud.gatewayserver.app.filters;

import jakarta.servlet.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/*import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;*/

import org.springframework.core.Ordered;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseCookie;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import java.io.IOException;
import java.util.Optional;

@Component
public class SampleGlobalFilter implements Filter, Ordered {
        //Atributos de SampleGlobalFilter
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    //Constructores de SampleGlobalFilter
    //Asignadores de atributos de SampleGlobalFilter (setters)
    //Lectores de atributos de SampleGlobalFilter (getters)
        //Métodos de SampleGlobalFilter
    @Override
    public int getOrder() {
        return 100;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

    }
}

/*@Component
public class SampleGlobalFilter implements GlobalFilter, Ordered {
        //Atributos de SampleGlobalFilter
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    //Constructores de SampleGlobalFilter
    //Asignadores de atributos de SampleGlobalFilter (setters)
    //Lectores de atributos de SampleGlobalFilter (getters)
        //Métodos de SampleGlobalFilter
    @Override
    public int getOrder() {
        return 100;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
            //El PRE maneja al request
        this.logger.info("Ejecutando el filtro antes de cualquier request (PRE-)");

            //El objeto exchange contiene al request y al response
        ServerHttpRequest request = exchange.getRequest();
        ServerHttpResponse response = exchange.getResponse();

        ServerHttpRequest mutatedRequest = request.mutate()
            .headers(h -> h.add("token", "abcdefg"))
            .build();
        ServerWebExchange mutatedExchange = exchange.mutate().request(mutatedRequest).build();


            //El POST maneja al response
        return chain.filter(exchange).then(
            Mono.fromRunnable(() -> {
                *//*this.logger.info("token: {}", request.getHeaders().getFirst("token"));*//*  //Con esta instancia inmutable del request no funciona
                String token = mutatedExchange.getRequest().getHeaders().getFirst("token");

                    //Dos formas distintas de mostrar el token
                if (token != null) {
                    this.logger.info("token: {}", token);
                    response.getHeaders().add("token", token);
                }
                this.logger.info("Ejecutando el filtro después de cualquier request (POST-)");

                Optional.ofNullable(mutatedExchange.getRequest().getHeaders().getFirst("token")).ifPresent(
                    t -> {
                        this.logger.info("token 2: {}", t);
                        response.getHeaders().add("token-2", t);
                    }
                );

                    response.getCookies()
                        .add(
                            "color",
                            ResponseCookie.from("color", "rojo").build()
                        );
                    response.getHeaders().setContentType(MediaType.APPLICATION_JSON); *//*MediaType.TEXT_PLAIN*//* //Es para un ejemplo. El estándar sigue siendo JSON
            })
        );
    }
}*/
