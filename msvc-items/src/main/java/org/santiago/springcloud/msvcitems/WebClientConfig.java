package org.santiago.springcloud.msvcitems;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {
        //Atributos de WebClientConfig
    @Value("${config.baseurl.endpoint.msvc-products}")
    private String contextPath;

    //Constructores de WebClientConfig
    //Asignadores de atributos de WebClientConfig (setters)
    //Lectores de atributos de WebClientConfig (getters)
        //Métodos de WebClientConfig
    @Bean
    @LoadBalanced
    WebClient.Builder webClient(){
        return WebClient.builder().baseUrl(this.contextPath);
    }
}
