package com.reactive.productservice.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;
import org.springframework.data.annotation.Id;

@Data
@ToString
@AllArgsConstructor
public class Product {

    @Id
    private String id;
    private String description;
    private Integer price;

}
