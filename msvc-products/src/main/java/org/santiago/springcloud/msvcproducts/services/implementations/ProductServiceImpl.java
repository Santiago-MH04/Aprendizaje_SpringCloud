package org.santiago.springcloud.msvcproducts.services.implementations;

import org.santiago.springcloud.msvcproducts.entities.Product;
import org.santiago.springcloud.msvcproducts.repositories.ProductRepository;
import org.santiago.springcloud.msvcproducts.services.abstractions.ProductService;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {
        //Atributos de ProductServiceImpl
    final private ProductRepository repoProduct;    //La buena práctica recomienda hacerlo final. Pero esto obliga a descartar el constructor vacío
    final private Environment environment;    //Como la interfaz Environment hace parte del contexto de Spring, al inyectar una instancia de ésta, la referencia no es nula

        //Constructores de ProductServiceImpl
    public ProductServiceImpl(ProductRepository productRepository, Environment environment) {
        this.repoProduct = productRepository;
        this.environment = environment;
    }

    //Asignadores de atributos de ProductServiceImpl (setters)
    //Lectores de atributos de ProductServiceImpl (getters)
        //Métodos de ProductServiceImpl
    @Override
    @Transactional(readOnly = true)
    public List<Product> findAll() {
        return this.repoProduct.findAll().stream()
                .map(p -> {
                    p.setPort(Integer.parseInt(this.environment.getProperty("local.server.port")));
                    return p;
                })
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Product> findById(Long id) {
        return this.repoProduct.findById(id)
                .map(p -> {
                    p.setPort(Integer.parseInt(this.environment.getProperty("local.server.port")));
                    return p;
                });
    }

    @Override
    @Transactional
    public Product save(Product product) {
        return null;
    }

    @Override
    @Transactional
    public void deleteById(Long id) {

    }
}
