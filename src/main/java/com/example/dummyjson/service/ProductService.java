package com.example.dummyjson.service;

import com.example.dummyjson.dto.DummyJsonResponse;
import com.example.dummyjson.dto.Product;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public class ProductService {

    private static final Logger log = LoggerFactory.getLogger(ProductService.class);
    private final String BASE_URL;
    private final WebClient webClient;

    @Autowired
    public ProductService(WebClient webClient) {
        this.webClient = webClient;
        this.BASE_URL = "https://dummyjson.com/products";
    }

    public ProductService(WebClient webClient, String baseUrl) {
        this.webClient = webClient;
        this.BASE_URL = baseUrl;
    }

    public Mono<DummyJsonResponse> getAllProducts() {
        return webClient.get()
                .uri(BASE_URL)
                .retrieve()
                .bodyToMono(DummyJsonResponse.class);
    }

    public Mono<Product> getProductById(Long id) {
        String url = BASE_URL + "/" + id;

        return webClient.get()
                .uri(url)
                .retrieve()
                .bodyToMono(Product.class);
    }
}
