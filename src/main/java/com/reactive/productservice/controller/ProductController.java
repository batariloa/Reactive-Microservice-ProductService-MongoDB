package com.reactive.productservice.controller;

import com.reactive.productservice.service.ProductService;
import dto.ProductDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.concurrent.ThreadLocalRandom;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("all")
    public Flux<ProductDto> all(){
        return productService.getAll();
    }

    @GetMapping("{id}")
    public Mono<ResponseEntity<ProductDto>> getById(@PathVariable String id){


        return productService.getProductById(id)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @GetMapping("pricerange")
    public Flux<ProductDto> findByPriceRange(@RequestParam int min, @RequestParam  int max){
        return productService.findInPriceRange(min,max);
    }

    @PostMapping
    public Mono<ProductDto> insertProduct(@RequestBody Mono<ProductDto> productDto){

        return productService.insertProduct(productDto);
    }

    @PutMapping("{id}")
    public Mono<ProductDto> updateProduct(@PathVariable String id, @RequestBody Mono<ProductDto> productDtoMono){
        return productService.updateProduct(id,productDtoMono);
    }

    @DeleteMapping("{id}")
    public Mono<Void> deleteProduct(@PathVariable String id){
       return productService.deleteProduct(id);
    }



    private void simulateRandomException(){
       int nextInt = ThreadLocalRandom.current().nextInt(1,10);
        if(nextInt>5)
            throw new RuntimeException("something is wrong");


    }

}
