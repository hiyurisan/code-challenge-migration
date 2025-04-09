package com.example.dummyjson.dto;

import java.util.List;

public class DummyJsonResponse {

    public DummyJsonResponse() {
    }

    public DummyJsonResponse(List<Product> products) {
        this.products = products;
    }

    private List<Product> products;

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }
}
