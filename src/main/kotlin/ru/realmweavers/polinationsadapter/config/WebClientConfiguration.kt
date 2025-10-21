package ru.realmweavers.polinationsadapter.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.client.WebClient

@Configuration
class WebClientConfig {

    @Bean
    fun polinationsWebClient(properties: PolinationsAdapterProperties): WebClient {
        return WebClient.builder()
            .baseUrl(properties.baseUrl)
            .defaultHeader("Content-type", "application/json")
            .defaultHeader("Authorization", "Bearer ${properties.apiKey}")
            .build()
    }
}