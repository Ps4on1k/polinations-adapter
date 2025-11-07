package ru.realmweavers.polinationsadapter.service
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpHeaders
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Mono
import ru.realmweavers.polinationsadapter.config.PolinationsAdapterProperties
import ru.realmweavers.polinationsadapter.models.aiRequestDataModel
import java.net.URLEncoder
import java.nio.charset.StandardCharsets
import java.time.Duration

@Service
class PolinationsAdapterService(
    private val properties: PolinationsAdapterProperties,
    private val webClient: WebClient
) {


    private val logger =  LoggerFactory.getLogger(PolinationsAdapterService::class.java)


    /**
     * Make a request to Poinations API
     * Choose AI model and params via 'request' object
     * do not to forget set up your personal API key (https://auth.pollinations.ai/)
     * @param input Object with API params and prompt for AI model
     * @return Plain text with markdown, based on AI model
     */
    fun makeRequest(request:aiRequestDataModel): Mono<String> {
        logger.info("Making a request to Polinations: {}", request)

        if(!properties.enabled) {
            logger.warn("Polinations Adapter disabled. Unable to make an API request. Check your local properties")
            return Mono.just("Polinations Adapter disabled")
        }

        if(!properties.isValid()) {
            logger.warn("Polinations Adapter configuration is not valid. Unable to make an API request. Check your local properties")
            return Mono.just("Polinations Adapter configuration is invalid")
        }

        //TODO add parameters
        val result = httpGetPolinationsRequest(request)
        logger.debug("Polinations Request completed: {}", result)
        return result
    }

    fun httpGetPolinationsRequest(request: aiRequestDataModel): Mono<String> {
        logger.info("Get list of AI models from {}", properties.baseUrl)

        val httpHeaders = HttpHeaders().apply {
            set("Authorization", "Bearer ${properties.apiKey}")
        }

        return webClient.get()
            .uri(
                properties.baseUrl +
                        request.prompt +
                        "?model=" + request.model +
                        "&seed=" + request.seed +
                        "&system=" + request.systemPrompt +
                        "&private=" + request.private +
                        "&stream=" + request.stream +
                        "&referrer=" + request.referrer +
                        "&json=" + request.json +
                        "&temperature=" + request.temperature
            )
            .headers { it.addAll(httpHeaders) }
            .retrieve()
            .bodyToMono(String::class.java)
            .doOnSuccess { data ->
                logger.info("Successfully received data: ${data.take(100)}")
            }
            .doOnError { error ->
                logger.warn("Request failed: ${error.message}")
            }
            .timeout(Duration.ofSeconds(properties.timeout))
            .onErrorResume { error ->
                logger.error("Exception occurred: ${error.message}", error)
                Mono.just("Error: ${error.message}")
            }
    }
}