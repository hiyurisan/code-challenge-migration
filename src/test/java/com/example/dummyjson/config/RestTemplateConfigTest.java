package com.example.dummyjson.config;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

@SpringBootTest
public class RestTemplateConfigTest {

    @Autowired
    WebClientConfig webClientConfig;

    @Test
    public void testRestTemplateConfig() {
        Assert.notNull(this.webClientConfig.webClient(), "webClientConfig.webClient");
    }
}
