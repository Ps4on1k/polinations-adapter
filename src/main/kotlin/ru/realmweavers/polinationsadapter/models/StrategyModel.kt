package ru.realmweavers.polinationsadapter.models

data class StrategyModel(
    val baseUrl: String,
    val apiKey: String? = null,
    val version: String = "v1",
    val retryCount: Int = 3
) {
    class Builder {
        private var baseUrl: String = ""
        private var apiKey: String? = null
        private var version: String = "v1"
        private var retryCount: Int = 3

        fun baseUrl(baseUrl: String) = apply { this.baseUrl = baseUrl }
        fun apiKey(apiKey: String?) = apply { this.apiKey = apiKey }
        fun version(version: String) = apply { this.version = version }
        fun retryCount(retryCount: Int) = apply { this.retryCount = retryCount }

        fun build(): StrategyModel {
            require(baseUrl.isNotBlank()) { "Base URL must be specified" }
            return StrategyModel(baseUrl, apiKey, version, retryCount)
        }
    }
}
