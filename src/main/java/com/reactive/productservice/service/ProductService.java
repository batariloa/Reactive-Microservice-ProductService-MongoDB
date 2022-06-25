package com.reactive.productservice.service;

import com.reactive.productservice.dto.ProductDto;
import com.reactive.productservice.repository.ProductRepository;
import com.reactive.productservice.util.EntityDtoUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Sinks;

@Service
public class ProductService {

    @Autowired
    private Sinks.Many<ProductDto> sink;

    @Autowired
    private ProductRepository productRepository;

    public Flux<ProductDto> getAll(){
        return productRepository.findAll()
                .map(EntityDtoUtil::toDto);
    }

    public Mono<ProductDto> getProductById(String id){

        return productRepository.findById(id)
                .map(EntityDtoUtil::toDto);

    }

    public Mono<ProductDto> insertProduct(Mono<ProductDto> dto){

        return dto.map(EntityDtoUtil::toEntity)
                .flatMap(productRepository::insert)
                .map(EntityDtoUtil::toDto)
                .doOnNext(sink::tryEmitNext);

    }


    public Mono<ProductDto> updateProduct(String id,Mono<ProductDto> dto){

       return productRepository.findById(id)
               .flatMap(p-> dto
                       .map(EntityDtoUtil::toEntity)
                       .doOnNext(e-> e.setId(id)))
               .flatMap(productRepository::save)
               .map(EntityDtoUtil::toDto);
    }

    public Mono<Void> deleteProduct(String id){
         return productRepository.deleteById(id);
    }

    public Flux<ProductDto> findInPriceRange(int min, int max){
       return productRepository.findAllByPriceBetween(min, max)
               .map(EntityDtoUtil::toDto);

    }


}
