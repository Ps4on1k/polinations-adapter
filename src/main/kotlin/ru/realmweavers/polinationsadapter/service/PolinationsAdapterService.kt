package ru.realmweavers.polinationsadapter.service

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.reactor.awaitSingle
import kotlinx.coroutines.withContext
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.bodyToMono
import ru.realmweavers.polinationsadapter.config.PolinationsAdapterProperties
import ru.realmweavers.polinationsadapter.models.aiRequestDataModel
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

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
    suspend fun makeRequest(request:aiRequestDataModel): String {
        logger.info("Making a request to Polinations: {}", request)

        if(!properties.enabled) {
            logger.warn("Polinations Adapter disabled. Unable to make an API request. Check your local properties")
            return "Polinations Adapter disabled"
        }

        if(!properties.isValid()) {
            logger.warn("Polinations Adapter configuration is not valid. Unable to make an API request. Check your local properties")
            return "Polinations Adapter configuration is invalid"
        }

        //TODO add parameters
        val uriPrepared =
            "" + withContext(Dispatchers.IO) {
                URLEncoder.encode(request.prompt, StandardCharsets.UTF_8.toString())
            }
        val result = httpGetPolinationsRequest(uriPrepared)
        logger.debug("Polinations Request completed: {}", result)
        return result
    }

    suspend fun httpGetPolinationsRequest(uri:String): String {
        logger.info("Get list of AI models from {}",properties.baseUrl)
        return try {
                val response = webClient.get()
                    .uri(uri)
                    .retrieve()
                    .bodyToMono<String>()
                    .doOnSuccess { data ->
                        logger.info("Successfully received data: ${data.take(100)}")
                    }
                    .doOnError {
                        error ->  logger.warn("Request failed: ${error.message}")
                    }
                    .timeout(java.time.Duration.ofSeconds(properties.timeout))
                    .awaitSingle()
                response
            } catch (e: Exception) {
                logger.error("Exception occurred: ${e.message}",e)
                "Error: ${e.message}"
            }


    }
}