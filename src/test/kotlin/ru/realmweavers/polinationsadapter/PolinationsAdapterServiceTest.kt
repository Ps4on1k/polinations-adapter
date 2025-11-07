package ru.realmweavers.polinationsadapter.service

import io.mockk.*
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.bodyToMono
import reactor.core.publisher.Mono
import ru.realmweavers.polinationsadapter.config.PolinationsAdapterProperties

@OptIn(ExperimentalCoroutinesApi::class)
@ExtendWith(MockKExtension::class)
class PolinationsAdapterServiceTest {

    @MockK
    private lateinit var webClient: WebClient

    @MockK
    private lateinit var requestHeadersUriSpec: WebClient.RequestHeadersUriSpec<*>

    @MockK
    private lateinit var requestHeadersSpec: WebClient.RequestHeadersSpec<*>

    @MockK
    private lateinit var responseSpec: WebClient.ResponseSpec

    private lateinit var polinationsAdapterService: PolinationsAdapterService

    @BeforeEach
    fun setUp() {
        val properties = PolinationsAdapterProperties().apply {
            baseUrl = "https://text.pollinations.ai/"
            apiKey = "asdsadasdad"
            timeout = 5000
            enabled = true
        }

        polinationsAdapterService = PolinationsAdapterService(properties, webClient)
    }
/*
    @Test
    fun `should return success message when GET request done with no errors`() = runTest {
        // Arrange
        val expectedResponse = """{"models": ["gpt-4"]}"""

        // MockK мокирование - более читаемый синтаксис
        every { webClient.get() } returns requestHeadersUriSpec
        every { requestHeadersUriSpec.uri("/models") } returns requestHeadersSpec
        every { requestHeadersSpec.retrieve() } returns responseSpec
        every { responseSpec.bodyToMono<String>() } returns Mono.just(expectedResponse)

        // Act
        //TODO hardcode for uri - fix that shit
        val result = polinationsAdapterService.httpGetPolinationsRequest("/models")

        // Assert
        assertEquals(expectedResponse, result)

        // Проверка вызовов
        verify { webClient.get() }
        verify { requestHeadersUriSpec.uri("/models") }
        verify { requestHeadersSpec.retrieve() }
        verify { responseSpec.bodyToMono<String>() }
    }

 */
}