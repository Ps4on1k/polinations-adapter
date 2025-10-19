package ru.realmweavers.polinationsadapter

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import ru.realmweavers.polinationsadapter.config.PolinationsAdapterProperties
import ru.realmweavers.polinationsadapter.service.PolinationsAdapterService
import kotlin.test.assertEquals
import kotlin.test.assertTrue

/**
 * Unit tests for PolinationsAdapterService
 *
 */

class PolinationsAdapterServiceTest {

    private lateinit var service: PolinationsAdapterService
    private lateinit var properties: PolinationsAdapterProperties

    @BeforeEach
    fun setUp() {
        properties = PolinationsAdapterProperties(
            enabled = true,
            apiKey = "asdKey176ASd",
            baseUrl = "http://test.some.shit.com",
            timeout = 5000,
            maxRetries = 3,
            retryDelay = 1000
        )
        service = PolinationsAdapterService(properties)
    }

    /**
     *
     */
    @Test
    fun `should process requests when Polinations Adapter is enabled`() {
        //Arrange
        val request = "test request"

        //Act
        val result = service.makeRequest(request)

        //Assert
        assertTrue(result.contains(request))
    }

    @Test
    fun `should return disabled message when disabled`() {
        //Arrange
        properties = PolinationsAdapterProperties(enabled = false)
        service = PolinationsAdapterService(properties)

        //Act
        val result = service.makeRequest("test request")

        //Assert
        assertEquals("Polinations Apdapter disabled", result)
    }

}