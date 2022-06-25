package com.reactive.productservice.service;

import dto.ProductDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class DataSetupService implements CommandLineRunner {

    @Autowired
    private ProductService productService;

    @Override
    public void run(String... args) throws Exception {
        ProductDto p1 = new ProductDto(null, "Crni ormar", 25000);
        ProductDto p2 = new ProductDto(null, "Beli jastuk", 2000);

        Flux.just(p1,p2)
                .flatMap(p-> productService.insertProduct(Mono.just(p)))
                .subscribe(System.out::println);
    }
}
