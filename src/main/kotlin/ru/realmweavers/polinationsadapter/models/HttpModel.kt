package ru.realmweavers.polinationsadapter.models

/**
 * HTTP request data model
 */

data class HttpModel(
    val url: String,
    val method: String,
    val headers: Map<String,String>,
    val payload: Any?,
    val timeout: Int
)
{
    class Builder {
        private var url: String = ""
        private var method: String = "GET"
        private var headers: MutableMap<String,String> = mutableMapOf()
        private var payload: Any? = null
        private var timeout: Int = 35000

        fun url(url:String) = apply { this.url = url }
        fun method(method: String) = apply { this.method = method }
        fun header(headers: MutableMap<String, String>) = apply { this.headers = headers }
        fun payload(payload: Any?) = apply { this.payload = payload }
        fun timeout(timeout: Int) = apply { this.timeout = timeout }

        fun build(): HttpModel {
            require(url.isNotBlank()) { "URL must be specified" }
            return HttpModel(url, method, headers, payload, timeout)
        }
    }
}
