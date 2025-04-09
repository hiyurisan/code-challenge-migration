package com.example.dummyjson.controller;

import com.example.dummyjson.dto.DummyJsonResponse;
import com.example.dummyjson.dto.Product;
import com.example.dummyjson.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationContext;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;


@SpringBootTest
public class ProductControllerTest {

    WebTestClient webTestClient;

    @MockBean
    private ProductService productService;

    @BeforeEach
    void setUp(ApplicationContext context) {
        webTestClient = WebTestClient.bindToApplicationContext(context).build();
    }

    @Test
    public void testGetAllProducts() throws Exception {
        Product product1 = new Product();
        product1.setId(1L);
        product1.setTitle("Product 1");

        Product product2 = new Product();
        product2.setId(2L);
        product2.setTitle("Product 2");

        List<Product> products = Arrays.asList(product1, product2);
        DummyJsonResponse dummyJsonResponse = new DummyJsonResponse(products);

        when(productService.getAllProducts()).thenReturn(Mono.just(dummyJsonResponse));

        var result = this.webTestClient.get().uri("/api/products")
                .exchange()
                .expectStatus().isOk()
                .expectBody(DummyJsonResponse.class)
                .returnResult();

        DummyJsonResponse response = result.getResponseBody();

        assertThat(response).isNotNull();
        assertThat(response.getProducts()).isNotEmpty();
        assertThat(response.getProducts()).hasSize(2);
        assertThat(response.getProducts().get(0).getTitle()).isEqualTo(product1.getTitle());
    }

    @Test
    public void testGetProductById() throws Exception {
        Product product = new Product();
        product.setId(1L);
        product.setTitle("Product 1");

        when(productService.getProductById(1L)).thenReturn(Mono.just(product));


        var result = this.webTestClient.get().uri("/api/products/1")
                .exchange()
                .expectStatus().isOk()
                .returnResult(Product.class);

        var response = result.getResponseBody().blockFirst();

        assertThat(response).isNotNull();
        assertThat(response.getId()).isEqualTo(1L);
        assertThat(response.getTitle()).isEqualTo(product.getTitle());
    }
}
