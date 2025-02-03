package org.santiago.springcloud.gatewayserver.app.filters.factory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.OrderedGatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Component
public class SampleCookieGatewayFilterFactory extends AbstractGatewayFilterFactory<SampleCookieGatewayFilterFactory.ConfigurationCookie> {
        //Atributos de SampleCookieGatewayFilterFactory
    private final Logger logger = LoggerFactory.getLogger(SampleCookieGatewayFilterFactory.class);

        //Constructores de SampleCookieGatewayFilterFactory
            //Son dos maneras de invocar el constructor de la clase padre, con o sin parámetros
    public SampleCookieGatewayFilterFactory(Class<ConfigurationCookie> configClass) {
        super(configClass);
    }
        public SampleCookieGatewayFilterFactory() {
            super(ConfigurationCookie.class);
        }

    //Asignadores de atributos de SampleCookieGatewayFilterFactory (setters)
    //Lectores de atributos de SampleCookieGatewayFilterFactory (getters)
        //Métodos de SampleCookieGatewayFilterFactory
    /*@Override
    public List<String> shortcutFieldOrder() {  //Para definir el orden de los atributos con que se configuró el application.yml
        return Arrays.asList("name", "value", "message");
    }*/

    /*@Override
    public String name() {
        *//*return "ExempleCookie";*//*
        *//*return "SampleCookie";*//*
    }*/

    @Override
    public GatewayFilter apply(ConfigurationCookie config) { //Se hace así porque es una interfaz funcional. Recuerda que las interfaces con un método, son funcionales por defecto
            //Todo esto es básicamente un método filter() que llama a otro método filter()
        return new OrderedGatewayFilter((exchange, chain) -> {
            this.logger.info(String.format("Ejecutando PRE gateway filter factory «%s» %s", config.message, "antes de"));
            return chain.filter(exchange)
                        .then(Mono.fromRunnable(() -> {
                            Optional.ofNullable(config.getValue()).ifPresent(cookie -> {
                                exchange.getResponse().addCookie(
                                    ResponseCookie.from(config.getName(), cookie).build()
                                );
                            });
                            this.logger.info(String.format("Ejecutando POST gateway filter factory «%s» %s", config.getMessage(), "después de"));
                        }));
        }, 100);
    }

        //Preparaos para lo más loco del mundo LAS CLASES ANIDADAS
    public static class ConfigurationCookie{
            //Atributos de ConfigurationCookie
        private String name;
        private String value;
        private String message;

        //Constructores de ConfigurationCookie
            //Asignadores de atributos de ConfigurationCookie (setters
        public void setName(String name) {
            this.name = name;
        }
            public void setValue(String value) {
                this.value = value;
            }
                public void setMessage(String message) {
                    this.message = message;
                }

            //Lectores de atributos de ConfigurationCookie (getters)
        public String getName() {
            return this.name;
        }
            public String getValue() {
                return this.value;
            }
                public String getMessage() {
                    return this.message;
                }

        //Métodos de ConfigurationCookie
    } 
}
