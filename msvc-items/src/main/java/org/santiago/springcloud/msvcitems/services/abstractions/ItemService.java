package org.santiago.springcloud.msvcitems.services.abstractions;

import org.santiago.springcloud.msvcitems.DTOentities.Item;
import org.santiago.springcloud.msvcitems.DTOentities.ProductDTO;

import java.util.List;
import java.util.Optional;

public interface ItemService {
    //Atributos de ItemService
    //Constructores de ItemService
    //Asignadores de atributos de ItemService (setters)
    //Lectores de atributos de ItemService (getters)
        //Métodos de ItemService
    List<Item> findAll();
    Optional<Item> findById(long id);
    Item save(ProductDTO product);
    Item update(ProductDTO product, Long id);
    void delete(Long id);
}
