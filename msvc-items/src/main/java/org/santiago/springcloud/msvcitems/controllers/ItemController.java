package org.santiago.springcloud.msvcitems.controllers;

import org.santiago.springcloud.msvcitems.DTOentities.Item;
import org.santiago.springcloud.msvcitems.DTOentities.ProductDTO;
import org.santiago.springcloud.msvcitems.services.abstractions.ItemService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/items")
public class ItemController {
        //Atributos de ItemController
    private final ItemService itemService;  //Cuando se inyecta sin @Autowired hay que inyectar por constructor
    @Value("${config.provider-name}")
    private String providerName;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final CircuitBreakerFactory circuitBreakerFactory;

        //Constructores de ItemController
    public ItemController(@Qualifier("webClient") ItemService itemService, CircuitBreakerFactory circuitBreakerFactory) {
        this.itemService = itemService;
        this.circuitBreakerFactory = circuitBreakerFactory;
    }

    //Asignadores de atributos de ItemController (setters)
    //Lectores de atributos de ItemController (getters)
        //Métodos de ItemController
    @GetMapping
    public List<Item> listAllItems(@RequestParam(name = "name", required = false) String name,
                                   @RequestHeader(name = "token-request", required = false) String token) {
        this.logger.info(String.format("%s: %s", "parameter", name));
        this.logger.info(String.format("%s: %s", "request-token", token));
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
            /*Optional<Item> itemOptional = this.itemService.findById(id);*/
            Optional<Item> itemOptional = this.circuitBreakerFactory.create("items")
                                              .run(() -> this.itemService.findById(id),
                                          e ->  {
                                                        this.logger.error(e.getMessage());
                                                        ProductDTO product = new ProductDTO(1L, "Aguacate maduro", 3500D, "Qué belleza de aguacate");
                                                        return Optional.of(new Item(product, 4));
                                                   }
                                              );
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
