package com.reactive.productservice;

import com.reactive.productservice.controller.ProductController;
import com.reactive.productservice.service.ProductService;
import dto.ProductDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.util.List;

import java.util.ArrayList;

@WebFluxTest(ProductController.class)
public class ControllerGetTest {

    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    ProductService productService;

    @Test
    public void fluentAssertionTest(){

        ProductDto stubProduct = new ProductDto("someId", "someDescription", 9999);

        Mockito.when(productService.getProductById(Mockito.anyString())).thenReturn(Mono.just(stubProduct));

        webTestClient
                .get()
                .uri("/product/sdfsdfsd")
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBody(ProductDto.class)
                .value(Assertions::assertNotNull)
                .value(r-> Assertions.assertEquals(stubProduct, r));

    }

    @Test
    public void listResponse(){
        List<ProductDto> list = new ArrayList<>();
        ProductDto stubProduct1 = new ProductDto("someId", "someDescription", 9999);
        ProductDto stubProduct2 = new ProductDto("someId", "someDescription", 9999);

        list.add(stubProduct1);
        list.add(stubProduct2);

        Flux<ProductDto> flux = Flux.fromIterable(list);

        Mockito.when(productService.getAll()).thenReturn(flux);

        webTestClient
                .get()
                .uri("/product/all")
                .exchange()
                .expectBodyList(ProductDto.class)
                .hasSize(2);


    }


}
