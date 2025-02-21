package org.santiago.springcloud.libs.msvccommons;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication
@EnableAutoConfiguration(exclude = {DataSourceAutoConfiguration.class}) //Se excluye porque únicamente es una librería. Esta aplicación no tiene persistencia en este proyecto
public class LibsMsvcCommonsApplication {
		//Esto no es una aplicación ejecutable de Spring Boot
    /*public static void main(String[] args) {
        SpringApplication.run(LibsMsvcCommonsApplication.class, args);
    }*/
}
