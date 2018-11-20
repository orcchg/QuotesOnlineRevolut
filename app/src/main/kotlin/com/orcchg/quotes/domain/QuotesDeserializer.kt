package com.orcchg.quotes.domain

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import java.lang.reflect.Type
import javax.inject.Singleton

@Singleton
class QuotesDeserializer : JsonDeserializer<Quotes> {

    override fun deserialize(json: JsonElement, typeOfT: Type, context: JsonDeserializationContext): Quotes {
        val jsonObject = json.asJsonObject

        val rates = mutableMapOf<String, Double>()
        val ratesObject = jsonObject.get("rates").asJsonObject
        ratesObject.keySet().forEach { rates[it] = ratesObject.get(it).asDouble }

        return Quotes(base = jsonObject.get("base").asString, date = jsonObject.get("date").asString, rates = rates)
    }
}
