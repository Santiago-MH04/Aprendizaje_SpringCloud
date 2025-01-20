package org.santiago.springcloud.msvcitems.services.implementations;

import org.santiago.springcloud.msvcitems.DTOentities.Item;
import org.santiago.springcloud.msvcitems.DTOentities.ProductDTO;
import org.santiago.springcloud.msvcitems.services.abstractions.ItemService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClient.Builder;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.util.*;

@Service
/*@Primary*/
@Qualifier("webClient")
public class ItemServiceWebClientImpl implements ItemService {
        //Atributos de ItemServiceWebClientImpl
    private final WebClient.Builder webClient;

        //Constructores de ItemServiceWebClientImpl
    public ItemServiceWebClientImpl(WebClient.Builder webClient) {
        this.webClient = webClient;
    }

    //Asignadores de atributos de ItemServiceWebClientImpl (setters)
    //Lectores de atributos de ItemServiceWebClientImpl (getters)
        //Métodos de ItemServiceWebClientImpl
    @Override
    public List<Item> findAll() {
        return this.webClient.build()
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
        try {
            Item searchResult = this.webClient.build()
                    .get()  //Porque se trata de un método GET
                    .uri("/api/products/{id}", params)   //Así se puebla tratándose de un @PathVariable
                    .accept(MediaType.APPLICATION_JSON)    //Porque vamos a recibir respuestas de tipo JSON
                    .retrieve()
                    .bodyToMono(ProductDTO.class)     //Recordar que WebClient es un tipo reactivo, y esta vez contiene un elemento
                    .map(p -> new Item(p, new Random().nextInt(20) + 1))
                    .block();

            return Optional.of(searchResult);   //Si a estas alturas no ha captado la excepción, no es necesario que sea ofNullable
        } catch (WebClientResponseException e) {
            return Optional.empty();
        }
    }
}
