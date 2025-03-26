package org.santiago.springcloud.msvcitems.services.implementations;

import org.santiago.springcloud.msvcitems.DTOentities.Item;
import org.santiago.springcloud.msvcitems.DTOentities.ProductDTO;
import org.santiago.springcloud.msvcitems.services.abstractions.ItemService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.*;

@Service
/*@Primary*/
@Qualifier("webClient")
public class ItemServiceWebClientImpl implements ItemService {
        //Atributos de ItemServiceWebClientImpl
    private final WebClient webClient;
        /*private final WebClient.Builder webClient;*/

        //Constructores de ItemServiceWebClientImpl
    public ItemServiceWebClientImpl(WebClient webClient) {
        this.webClient = webClient;
    }
        /*public ItemServiceWebClientImpl(WebClient.Builder webClient) {
            this.webClient = webClient;
        }*/

    //Asignadores de atributos de ItemServiceWebClientImpl (setters)
    //Lectores de atributos de ItemServiceWebClientImpl (getters)
        //Métodos de ItemServiceWebClientImpl
    @Override
    public List<Item> findAll() {
        return this.webClient/*.build()*/
                .get()  //Porque se trata de un método GET
                .uri("/api/products")
                .accept(MediaType.APPLICATION_JSON)    //Porque vamos a recibir respuestas de tipo JSON
                .retrieve()
                .bodyToFlux(ProductDTO.class)     //Recordar que WebClient es un tipo reactivo, y contiene varios elementos
                .map(p -> new Item(p, new Random().nextInt(20) + 1))
                .collectList()
                .block();
    }

    @Override
    public Optional<Item> findById(long id) {
        Map<String, Long> params = new HashMap<>();
            params.put("id", id);
        /*try {*/
            Item searchResult = this.webClient/*.build()*/
                    .get()  //Porque se trata de un método GET
                    .uri("/api/products/{id}", params)   //Así se puebla tratándose de un @PathVariable
                    .accept(MediaType.APPLICATION_JSON)    //Porque vamos a recibir respuestas de tipo JSON
                    .retrieve()
                    .bodyToMono(ProductDTO.class)     //Recordar que WebClient es un tipo reactivo, y esta vez contiene un elemento
                    .map(p -> new Item(p, new Random().nextInt(20) + 1))
                    .block();

            return Optional.ofNullable(searchResult);   //Decido dejarlo como Optional.ofNullable(), aunque no es necesario
        /*} catch (WebClientResponseException e) {
            return Optional.empty();
        }*/
    }

    @Override
    public Item save(ProductDTO product) {
        return this.webClient/*.build()*/
                .post()  //Porque se trata de un método POST
                .uri("/api/products")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(product)
                .retrieve()
                .bodyToMono(ProductDTO.class)
                    //Transformar la instancia de ProductDTO en Item
                .map(p -> new Item(p, new Random().nextInt(20) + 1))
                .block();
    }

    @Override
    public Item update(ProductDTO product, Long id) {
        Map<String, Long> params = new HashMap<>();
            params.put("id", id);
        return this.webClient/*.build()*/
                .put()  //Porque se trata de un método PUT
                .uri("/api/products/{id}", params)   //Así se puebla tratándose de un @PathVariable
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)    //Porque vamos a recibir respuestas de tipo JSON
                .bodyValue(product)
                .retrieve()
                .bodyToMono(ProductDTO.class)     //Recordar que WebClient es un tipo reactivo, y esta vez contiene un elemento
                    //Transformar la instancia de ProductDTO en Item
                .map(p -> {
                    Item item = new Item();
                        item.setProduct(p);
                        item.setQuantity(new Random().nextInt(20) + 1);
                    return item;
                })
                .block();
    }

    @Override
    public void delete(Long id) {
        Map<String, Long> params = new HashMap<>();
            params.put("id", id);
        this.webClient/*.build()*/
                .delete()   //Porque se trata de un método DELETE
                .uri("/api/products/{id}", params) //Así se puebla tratándose de un @PathVariable
                .retrieve()
                /*.toBodilessEntity()*/ //Hace lo mismo que .bodyToMono(Void.class)
                .bodyToMono(Void.class) //Porque el método es vacío
                .block();
    }
}
