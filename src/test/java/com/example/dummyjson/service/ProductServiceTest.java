package com.example.dummyjson.service;

import com.example.dummyjson.dto.DummyJsonResponse;
import com.example.dummyjson.dto.Product;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class ProductServiceTest {

    private final ProductService productService = new ProductService(webClient, server.url("/").toString());

    @Autowired
    private ObjectMapper objectMapper;

    private static WebClient webClient;

    static MockWebServer server;

    @BeforeAll
    static void setUp() throws IOException {
        server = new MockWebServer();
        server.start();
        webClient = WebClient
                .builder()
                .baseUrl(server.url("/").toString())
                .build();
    }

    @AfterAll
    static void shutdown() throws IOException {
        if (server != null) {
            server.shutdown();
        }
    }

    @Test
    public void testGetAllProducts() {
        Product product1 = new Product();
        product1.setId(1L);
        product1.setTitle("Product 1");

        Product product2 = new Product();
        product2.setId(2L);
        product2.setTitle("Product 2");

        var products = List.of(product1, product2);
        var response = new DummyJsonResponse(products);

        prepareResponse(mockResponse -> {
            try {
                mockResponse.setBody(objectMapper.writeValueAsString(response))
                        .addHeader("Content-Type", "application/json");
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        });

        var result = productService.getAllProducts();
        DummyJsonResponse dummyJsonResponse = result.log().block();
        assertEquals(2, Objects.requireNonNull(dummyJsonResponse).getProducts().size());
        assertEquals("Product 1", Objects.requireNonNull(dummyJsonResponse).getProducts().get(0).getTitle());
    }

    @Test
    public void testGetProductById() {
        Product product = new Product();
        product.setId(1L);
        product.setTitle("Product 1");

        prepareResponse(mockResponse -> {
            try {
                mockResponse.setBody(objectMapper.writeValueAsString(product))
                        .addHeader("Content-Type", "application/json");
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        });

        var result = productService.getProductById(1L);
        assertEquals("Product 1", Objects.requireNonNull(result.log().block()).getTitle());
    }

    private void prepareResponse(Consumer<MockResponse> consumer) {
        MockResponse response = new MockResponse();
        consumer.accept(response);
        server.enqueue(response);
    }
}
