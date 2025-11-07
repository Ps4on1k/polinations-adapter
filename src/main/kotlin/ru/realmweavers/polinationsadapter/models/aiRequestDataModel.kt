package ru.realmweavers.polinationsadapter.models

data class aiRequestDataModel(
    var model: String,
    var prompt: String,
    var systemPrompt: String?,
    var seed: Int?,
    var temperature: Double?,
    var json: Boolean?,
    var stream: Boolean?,
    var private: Boolean?,
    var referrer: String?
)
{
    class Builder() {

        private var model: String = ""
        private var prompt: String = ""
        private var systemPrompt: String? = ""
        private var seed: Int? = 0
        private var temperature: Double? = 0.0
        private var json: Boolean? = false
        private var stream: Boolean? = false
        private var private: Boolean? = true
        private var referrer: String? = ""

        fun model(aiModel: String) = apply { this.model = aiModel }
        fun prompt(prompt: String) = apply { this.prompt = prompt }
        fun systemPrompt(systemPrompt: String?) = apply { this.systemPrompt = systemPrompt }
        fun seed(seed: Int?) = apply { this.seed = seed }
        fun temperature(temperature: Double?) = apply { this.temperature = temperature }
        fun json(json: Boolean?) = apply { this.json = json }
        fun stream(stream: Boolean?) = apply { this.stream = stream }
        fun private(private: Boolean?) = apply { this.private = private }
        fun referrer(referrer: String?) = apply { this.referrer = referrer }

        fun build(): aiRequestDataModel {
            //TODO make a validations here
            return aiRequestDataModel(model,prompt,systemPrompt,seed, temperature, json, stream, private, referrer)
        }

    }
}