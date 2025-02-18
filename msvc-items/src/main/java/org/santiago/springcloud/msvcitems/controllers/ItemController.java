package org.santiago.springcloud.msvcitems.controllers;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import org.santiago.springcloud.msvcitems.DTOentities.Item;
import org.santiago.springcloud.msvcitems.DTOentities.ProductDTO;
import org.santiago.springcloud.msvcitems.services.abstractions.ItemService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.CompletableFuture;

@RefreshScope   //Para refrescar cualquier cambio en la inyección de dependencias en tiempo real
@RestController
@RequestMapping("/api/items")
public class ItemController {
        //Atributos de ItemController
    private final ItemService itemService;  //Cuando se inyecta sin @Autowired hay que inyectar por constructor
    @Value("${config.provider-name}")
    private String providerName;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final CircuitBreakerFactory circuitBreakerFactory;
            //Inyectemos un atributo desde la configuración externa
    @Value("${configuration.text}")
    private String text;
            //Representación del perfil de configuraciones que se desea manejar
    @Autowired
    private Environment env;


        //Constructores de ItemController
    public ItemController(@Qualifier("webClient") ItemService itemService, CircuitBreakerFactory circuitBreakerFactory) {
        this.itemService = itemService;
        this.circuitBreakerFactory = circuitBreakerFactory;
    }

    //Asignadores de atributos de ItemController (setters)
    //Lectores de atributos de ItemController (getters)
        //Métodos de ItemController
    @GetMapping("/fetch-configs")
    public ResponseEntity<?> fetchConfigs(@Value("${server.port}") String port){
        Map<String, String> json = new HashMap<>();
            json.put("text", this.text);    //Debe imprimir «setting development environment»
            json.put("port", port); //Debe imprimir «8005»
        this.logger.info(String.format("Imprimiendo un texto de configuración externo: '%s'", this.text));
        this.logger.info(String.format("Puerto configuración externo: '%s'", port));

        if(this.env.getActiveProfiles().length > 0 && this.env.getActiveProfiles()[0].equals("dev")){
             json.put("author.name", env.getProperty("configuration.author.name"));
             json.put("author.email", env.getProperty("configuration.author.email"));
        }

        return ResponseEntity.ok(json);
    }

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
                                                            product.setCreatedAt(LocalDate.now());
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
                //Para usar el cortocircito por anotaciones, debe existir sí o sí un application.yml o configurarse en el application.properties
            @CircuitBreaker(name = "items", fallbackMethod = "getFallbackMethodProduct") //En el otro método se le asignó el nombre items al cortocircuito
            @GetMapping("/details/{id}")
            public ResponseEntity<?> findItemDetailsAnnotation(@PathVariable Long id) {
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
                //Recordar que, para que esto juegue por anotaciones, debe haber un archivo de configuraciones que lo ajuste
            @CircuitBreaker(name = "items", fallbackMethod = "getFallbackMethodProduct2")   //Especificando el camino alternativo aquí funciona también para el @TimeLimiter
            @TimeLimiter(name = "items") //En el otro método se le asignó el nombre items al cortocircuito
            @GetMapping("/details/boosted/{id}")
            public CompletableFuture<?> findItemDetailsAnnotationsBoosted(@PathVariable Long id) {
                    //Envolver la sentencia SQL para calcular el tiempo de la llamada
                return CompletableFuture.supplyAsync(() -> {
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
                });
            }

            //Los métodos de camino alternativo deben retornar el mismo tipo de elemento que los endpoints asociados
        //Camino alternativo mediante anotaciones (debe ser del mismo tipo que el método que lo invoca)
    public ResponseEntity<?> getFallbackMethodProduct(Throwable e){
        this.logger.error(e.getMessage());
        ProductDTO product = new ProductDTO(1L, "Alfajor Cachafaz chocolate", 5500D, "Tremendo alfajor de camino alternativo");
        product.setCreatedAt(LocalDate.now());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                             .body(new Item(product, new Random().nextInt(20) + 1));
    }
        //Camino alternativo mediante anotaciones para un timeout
    public CompletableFuture<?> getFallbackMethodProduct2(Throwable e){
        return CompletableFuture.supplyAsync(() -> {
            this.logger.error(e.getMessage());
            ProductDTO product = new ProductDTO(1L, "Chocorramo", 2500D, "Un chocorramo chocorramoso");
            product.setCreatedAt(LocalDate.now());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                                 .body(new Item(product, new Random().nextInt(20) + 1));
        });
    }
}
