package org.santiago.springcloud.msvcitems;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
@EnableAutoConfiguration(exclude = {DataSourceAutoConfiguration.class}) //Se excluye porque únicamente es una librería. Esta aplicación no tiene persistencia directa en este proyecto, no está conectada a base de datos
public class MsvcItemsApplication {
    public static void main(String[] args) {
        SpringApplication.run(MsvcItemsApplication.class, args);
    }
}
