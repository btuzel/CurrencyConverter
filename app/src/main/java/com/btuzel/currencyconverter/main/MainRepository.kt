package com.btuzel.currencyconverter.main

import com.btuzel.currencyconverter.data.models.CurrencyResponse
import com.btuzel.currencyconverter.util.Resource

interface MainRepository {

    suspend fun getRates(base: String): Resource<CurrencyResponse>
}