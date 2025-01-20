package org.santiago.springcloud.msvcitems.httpClients;

import org.santiago.springcloud.msvcitems.DTOentities.ProductDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(/*url = "http://127.0.0.1:8001/api/products",*/ name = "msvc-products", path = "/api/products")    //Ya la url está configurada en el application.properties
public interface ProductFeignClient {
    //Atributos de ProductFeignClient
    //Constructores de ProductFeignClient
    //Asignadores de atributos de ProductFeignClient (setters)
    //Lectores de atributos de ProductFeignClient (getters)
        //Métodos de ProductFeignClient
    @GetMapping(/*"/api/products"*/)
    public List<ProductDTO> findAll();

    @GetMapping("/{id}")
    public ProductDTO findProductDetailsById(@PathVariable Long id);
}
