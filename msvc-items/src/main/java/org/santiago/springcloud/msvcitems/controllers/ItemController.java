package org.santiago.springcloud.msvcitems.controllers;

import org.santiago.springcloud.msvcitems.DTOentities.Item;
import org.santiago.springcloud.msvcitems.services.abstractions.ItemService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
public class ItemController {
        //Atributos de ItemController
    private final ItemService itemService;  //Cuando se inyecta sin @Autowired hay que inyectar por constructor
    @Value("${config.provider-name}")
    private String providerName;

        //Constructores de ItemController
    public ItemController(@Qualifier("webClient") ItemService itemService) {
        this.itemService = itemService;
    }

    //Asignadores de atributos de ItemController (setters)
    //Lectores de atributos de ItemController (getters)
        //Métodos de ItemController
    @GetMapping
    public List<Item> listAllItems() {
        return this.itemService.findAll();
    }
    /*@GetMapping("/{id}")  //Preguntar a Bryan por una posible razón
    public ResponseEntity<?> findItemDetails(@PathVariable Long id) {
        return this.itemService.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }*/
        @GetMapping("/{id}")
        public ResponseEntity<?> findItemDetails(@PathVariable Long id) {
            Optional<Item> itemOptional = this.itemService.findById(id);
            if(itemOptional.isPresent()) {
                return ResponseEntity.ok(itemOptional.get());
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(
                            Collections.singletonMap(
                                "message",
                                String.format("El producto con id %d no existe en el servicio de referencia (%s)", id, this.providerName)
                            )
                        );
            }
        }
}

/*() -> ResponseEntity.status(HttpStatus.NOT_FOUND)
                                 .body(Collections.singletonMap(
                                        "Message",
                                        String.format("El producto con id %d no existe en el servicio de referencia", id)
                                 )*/
