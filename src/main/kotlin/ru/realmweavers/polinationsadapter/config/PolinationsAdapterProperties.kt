package ru.realmweavers.polinationsadapter.config

import jakarta.validation.constraints.Min
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.bind.DefaultValue

/**
 * This class enables configuration via application.yml
 * @Example for application.yml
 * ```yaml
 * polinations:
 *  adapter:
 *      enabled: true
 *      api-key: "your-API-Key"
 *      timeout: 5000
 *      base-url: "https://text.pollinations.ai/"
 *
 *```
 */

@ConfigurationProperties(prefix = "polinations.adapter")
data class PolinationsAdapterProperties(
    /**
     * Check for library ON
     */
    var enabled: Boolean = true,

    /**
     * Polinations API key for HTTP requests
     */
    var apiKey: String? = null,

    /**
     *  HTTP timeout, ms
     */
    @field:Min(15, message = "HTTP timeout must be at leas 15ms")
    var  timeout: Long = 35000,

    /**
     *  HTTP retry, count
     */
    @field:Min(0, message = "Retries cannot be negative")
    var  maxRetries: Int = 3,

    /**
     *  HTTP retry, delay ms
     */
    @field:Min(0, message = "Retry delay cannot be negative")
    var  retryDelay: Int = 3000,



    /**
     * Base Polinations API URL
     */
    var baseUrl: String = "https://text.pollinations.ai/",
)
{
    /**
     * Validate configuration
     */
    fun isValid(): Boolean {
        return enabled && !apiKey.isNullOrBlank() && !baseUrl.isNullOrBlank()
    }

}

