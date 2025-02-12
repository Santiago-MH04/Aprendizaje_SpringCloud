package org.santiago.springcloud.msvcitems;

import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.timelimiter.TimeLimiterConfig;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JCircuitBreakerFactory;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JConfigBuilder;
import org.springframework.cloud.client.circuitbreaker.Customizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@Configuration
public class AppConfig {
    //Atributos de AppConfig
    //Constructores de AppConfig
    //Asignadores de atributos de AppConfig (setters)
    //Lectores de atributos de AppConfig (getters)
        //Métodos de AppConfig
    @Bean
    Customizer<Resilience4JCircuitBreakerFactory> customiserCircuitBreaker(){
            //Este "id" es del ItemController.findItemDetails(Long id) donde se le asigna al circuit breaker un identificador. Así, podemos filtrar las diferentes llamadas
        return factory -> factory.configureDefault(id -> new Resilience4JConfigBuilder(id).
                circuitBreakerConfig(
                    CircuitBreakerConfig.custom()
                    .slidingWindowSize(10)  //Para que la ventana de solicitudes que arrojan error cambie a 10
                    .failureRateThreshold(30)   //Para que el umbral de solicitudes en el estado semiabierto cambie a 30 %
                    .waitDurationInOpenState(Duration.ofSeconds(10L))    //Para que el estado abierto dure 10 s nada más
                    .permittedNumberOfCallsInHalfOpenState(5)   //Para cambiar el número permitido de llamadas en estado semiabierto
                    .slowCallDurationThreshold(Duration.ofSeconds(1L))  //Usualmente, o es llamada lenta, o es timeout
                    .slowCallRateThreshold(50)    //Para que se puedan invocar máximo el 50 % de llamadas lentas antes de abrir el circuito
                    .build()
                )
        .timeLimiterConfig(TimeLimiterConfig.custom()   //Para lanzar excepciones de tipo TimeLimiter si una petición excede 3s de tiempo de carga
                .timeoutDuration(Duration.ofSeconds(5L))    //El timeout debe durar más que la llamada lenta
                .build())
        .build());
    }
}
