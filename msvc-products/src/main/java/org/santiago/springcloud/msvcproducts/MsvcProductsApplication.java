package org.santiago.springcloud.msvcproducts;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan({"org.santiago.springcloud.libs.msvccommons.entities"}) //Hay que configurar la clase Product de la librería Commons como parte del conexto JPA de este proyecto
public class MsvcProductsApplication {
    public static void main(String[] args) {
        SpringApplication.run(MsvcProductsApplication.class, args);
    }
}
