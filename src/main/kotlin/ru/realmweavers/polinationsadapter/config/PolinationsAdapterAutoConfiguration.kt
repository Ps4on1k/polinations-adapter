package ru.realmweavers.polinationsadapter.config

import org.slf4j.LoggerFactory
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.client.WebClient
import ru.realmweavers.polinationsadapter.service.PolinationsAdapterService

/**
 * SpringBoot auto configurations
 */
@Configuration
@ConditionalOnClass(PolinationsAdapterService::class)
@EnableConfigurationProperties(PolinationsAdapterProperties::class)
class PolinationsAdapterAutoConfiguration {
    private val logger = LoggerFactory.getLogger(PolinationsAdapterAutoConfiguration::class.java)

    /**
     * Make a bean if:
     * - User didn't create a bean (ConditionalOnMissingBean)
     * - Library enabled in properties
     */

    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnProperty(
        prefix = "polinations.adapter",
        name = ["enabled"],
        havingValue = "true",
        matchIfMissing = true
    )
    fun polinationsAdapterService(properties: PolinationsAdapterProperties, webClient: WebClient): PolinationsAdapterService {
        logger.info("Auto-configuring PolinationsAdapter with properties: {}", properties)
        if (!properties.isValid()) {
            logger.error("PolinationsAdapter is enabled but not  properly configured. Please set up polinations.adapter in application.yml")
        }
        return PolinationsAdapterService(properties, webClient)
    }

    /**
     * WEB client bean
     */

    @Bean
    @ConditionalOnMissingBean
    fun webClient(): WebClient {
        return WebClient.builder().build()
    }

    /**
     * Utility Bean
     */

    fun polinationsAdapterHealthIndicator(properties: PolinationsAdapterProperties): PolinationsAdapterHealthIndicator {
        return PolinationsAdapterHealthIndicator(properties)
    }

    /**
     * Health indicator class for SpringBoot Actuator
     */
    class PolinationsAdapterHealthIndicator(private val properties: PolinationsAdapterProperties) {
        fun checkHealth(): HealthStatus {
            return if (properties.isValid()) {
                HealthStatus.UP
            } else {
                HealthStatus.DOWN
            }
        }
        enum class HealthStatus {
            UP,DOWN
        }
    }
}