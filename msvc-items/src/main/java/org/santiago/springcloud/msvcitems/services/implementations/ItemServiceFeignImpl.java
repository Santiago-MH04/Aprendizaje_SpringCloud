package org.santiago.springcloud.msvcitems.services.implementations;

import feign.FeignException;
import org.santiago.springcloud.msvcitems.DTOentities.Item;
import org.santiago.springcloud.msvcitems.DTOentities.ProductDTO;
import org.santiago.springcloud.msvcitems.httpClients.ProductFeignClient;
import org.santiago.springcloud.msvcitems.services.abstractions.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

@Service
@Qualifier("feignClient")
public class ItemServiceFeignImpl implements ItemService {
        //Atributos de ItemServiceFeignImpl
    @Autowired
    private ProductFeignClient httpClient;

    //Constructores de ItemServiceFeignImpl
    //Asignadores de atributos de ItemServiceFeignImpl (setters)
    //Lectores de atributos de ItemServiceFeignImpl (getters)
        //Métodos de ItemServiceFeignImpl
    @Override
    public List<Item> findAll() {
        return this.httpClient.findAll()
                .stream()
                .map(p -> new Item(p, new Random().nextInt(20) + 1))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Item> findById(long id) {
        try {
            ProductDTO product = this.httpClient.findProductDetailsById(id);
            return Optional.of(
                new Item(product, new Random().nextInt(20) + 1)
            );
        } catch (FeignException e) {
            return Optional.empty();
        }
    }
}
