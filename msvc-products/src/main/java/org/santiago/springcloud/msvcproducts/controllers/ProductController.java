package org.santiago.springcloud.msvcproducts.controllers;

import org.santiago.springcloud.msvcproducts.entities.Product;

import org.santiago.springcloud.msvcproducts.services.abstractions.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("api/products")
public class ProductController {
        //Atributos de ProductController
    private final ProductService productService;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

        //Constructores de ProductController
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    //Asignadores de atributos de ProductController (setters)
    //Lectores de atributos de ProductController (getters)
        //Métodos de ProductController
    @GetMapping
    public List<Product> listAllProducts() {
        this.logger.info("Ingresando al método ProductController::listAllProducts");
        return this.productService.findAll();
    }
    @GetMapping("/{id}")
    public ResponseEntity<Product> findProductById(@PathVariable Long id) throws InterruptedException {
        this.logger.info("Ingresando al método ProductController::findProductById");
            //Simulemos pues unos errores bien tirados
        if(id.equals(10L)) {
            throw new IllegalStateException(String.format("Buscar el producto con id %d, arroja un error simulado", id));
        } else if(id.equals(7L)) {
            TimeUnit.SECONDS.sleep(3L); //En Spring Cloud, el tiempo de espera por defecto es de 1 s
        }
        return this.productService.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody Product product){
        this.logger.info("Ingresando al método ProductController::create, creando {}", product.getName());
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(this.productService.save(product));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody Product product){
        this.logger.info("Ingresando al método ProductController::update, actualizando {}", product.getName());
        return this.productService.findById(id)
                    //Esto así tan bonito, porque hay cosas que, obvio bobis, no le podemos modificar a product
                .map(p -> {
                    if (product.getName() != null || !product.getName().isBlank()) {
                        p.setName(product.getName());
                    }
                    if (product.getDescription() != null || !product.getDescription().isBlank()) {
                        p.setDescription(product.getDescription());
                    }
                    if (product.getPrice() != null) {
                        p.setPrice(product.getPrice());
                    }

                    return ResponseEntity.ok(this.productService.save(p));
                })
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        return this.productService.findById(id)
                .map(p -> {
                    this.logger.info("Ingresando al método ProductController::delete, eliminando producto con id {}", p.getId());
                    this.productService.deleteById(id);
                    return ResponseEntity.noContent().build();
                })
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }
}
