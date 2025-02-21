package org.santiago.springcloud.msvcproducts.repositories;

/*import org.santiago.springcloud.msvcproducts.entities.Product;*/  //Es para usar la clase Product de la librería Commons
import org.santiago.springcloud.libs.msvccommons.entities.Product;

import org.springframework.data.jpa.repository.JpaRepository;
/*import org.springframework.data.repository.CrudRepository;*/
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    //Atributos de ProductRepository
    //Constructores de ProductRepository
    //Asignadores de atributos de ProductRepository (setters)
    //Lectores de atributos de ProductRepository (getters)
    //Métodos de ProductRepository
}
