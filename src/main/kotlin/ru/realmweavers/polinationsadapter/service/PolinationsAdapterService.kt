package ru.realmweavers.polinationsadapter.service

import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import ru.realmweavers.polinationsadapter.config.PolinationsAdapterProperties

@Service
class PolinationsAdapterService(
    private val properties: PolinationsAdapterProperties
) {

    private val logger =  LoggerFactory.getLogger(PolinationsAdapterService::class.java)


    /**
     * Make a request to Poinations API
     * Choose AI model and params via 'request' object
     * do not to forget set up your personal API key (https://auth.pollinations.ai/)
     * @param input Object with API params and prompt for AI model
     * @return Plain text with markdown, based on AI model
     */
    fun makeRequest(request:String): String {
        logger.info("Making a request to Polinations: {}", request)

        if(!properties.enabled) {
            logger.warn("Polinations Adapter disabled. Unable to make an API request. Check your local properties")
            return "Polinations Apdapter disabled"
        }

        if(!properties.isValid()) {
            logger.warn("Polinations Adapter configuration is not valid. Unable to make an API request. Check your local properties")
            return "Polinations Apdapter configuration is invalid"
        }


        val result = "Processed: $request"
        logger.debug("Polinations Request completed: {}", result)

        return result
    }

    /**
     *
     */
}