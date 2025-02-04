package org.santiago.springcloud.msvcproducts.controllers;

import org.santiago.springcloud.msvcproducts.entities.Product;
import org.santiago.springcloud.msvcproducts.services.abstractions.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("api/products")
public class ProductController {
        //Atributos de ProductController
    private final ProductService productService;

        //Constructores de ProductController
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    //Asignadores de atributos de ProductController (setters)
    //Lectores de atributos de ProductController (getters)
        //Métodos de ProductController
    @GetMapping
    public List<Product> listAllProducts() {
        return this.productService.findAll();
    }
    @GetMapping("/{id}")
    public ResponseEntity<Product> findProductById(@PathVariable Long id) throws InterruptedException {
            //Simulemos un error ahí bien tirado
        if(id.equals(10L)) {
            throw new IllegalStateException("Buscar este producto, arroja un error");
        }
            //Simular una demora ahí bien tirada
        if(id.equals(7L)) {
            TimeUnit.SECONDS.sleep(5L);
        }
        return this.productService.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

}
