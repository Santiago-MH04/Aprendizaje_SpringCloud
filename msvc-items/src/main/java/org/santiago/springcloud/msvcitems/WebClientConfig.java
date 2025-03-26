package org.santiago.springcloud.msvcitems;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.client.loadbalancer.reactive.ReactorLoadBalancerExchangeFilterFunction;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {
        //Atributos de WebClientConfig
    /*@Value("${config.baseurl.endpoint.msvc-products}")
    private String contextPath;*/

    //Constructores de WebClientConfig
    //Asignadores de atributos de WebClientConfig (setters)
    //Lectores de atributos de WebClientConfig (getters)
        //Métodos de WebClientConfig
    @Bean
    @LoadBalanced
    WebClient webClient(
        WebClient.Builder webClientBuilder,
        ReactorLoadBalancerExchangeFilterFunction lbFunction,
        @Value("${config.baseurl.endpoint.msvc-products}") String contextPath
    ){  //Este webClientBuilder es generado automáticamente por Spring Boot
        return WebClient.builder()
            .baseUrl(contextPath)
            .filter(lbFunction)
            .build();
    }
        /*@Bean
        @LoadBalanced
        WebClient.Builder webClient(){
            return WebClient.builder().baseUrl(this.contextPath);
        }*/
}
