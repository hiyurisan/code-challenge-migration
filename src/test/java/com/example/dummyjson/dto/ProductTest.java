package com.example.dummyjson.dto;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class ProductTest {

    @Test
    public void testGetAndSetter(){
        Long expectId = 1L;
        String expectedTitle = "A dummy title";
        String expectedDescription = "A dummy description";
        Double expectedPrice = Double.valueOf("2.1");

        Product product1 = new Product();
        product1.setId(1L);
        product1.setTitle("A dummy title");
        product1.setDescription("A dummy description");
        product1.setPrice(Double.valueOf("2.1"));

        assertThat(product1.getId()).isEqualTo(expectId);
        assertThat(product1.getTitle()).isEqualTo(expectedTitle);
        assertThat(product1.getDescription()).isEqualTo(expectedDescription);
        assertThat(product1.getPrice()).isEqualTo(expectedPrice);
    }
}
