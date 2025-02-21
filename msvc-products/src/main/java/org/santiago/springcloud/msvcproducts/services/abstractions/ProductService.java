package org.santiago.springcloud.msvcproducts.services.abstractions;

/*import org.santiago.springcloud.msvcproducts.entities.Product;*/  //Es para usar la clase Product de la librería Commons
import org.santiago.springcloud.libs.msvccommons.entities.Product;

import java.util.List;
import java.util.Optional;

public interface ProductService {
    //Atributos de ProductService
    //Constructores de ProductService
    //Asignadores de atributos de ProductService (setters)
    //Lectores de atributos de ProductService (getters)
        //Métodos de ProductService
    List<Product> findAll();
    Optional<Product> findById(Long id);
    Product save(Product product);
    void deleteById(Long id);
}
