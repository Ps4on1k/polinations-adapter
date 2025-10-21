package ru.realmweavers.polinationsadapter.strategy

import ru.realmweavers.polinationsadapter.models.HttpModel
import ru.realmweavers.polinationsadapter.models.StrategyModel

interface HttpRequestStrategy {
    fun createRequest(httpData: Map<String,Any>): HttpModel
    fun getConfig(): StrategyModel
}

