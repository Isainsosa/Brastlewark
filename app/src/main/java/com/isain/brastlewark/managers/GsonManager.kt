package com.isain.brastlewark.managers

import com.google.gson.*
import com.isain.network.Environment
import java.lang.reflect.Type
import kotlin.reflect.KClass

object GsonManager {

    val typeAdapters = listOf(
        Pair(Environment::class.java, EnvironmentAdapter())
    )

    fun defaultGson(): Gson = GsonBuilder().apply {
        typeAdapters.forEach {
            registerTypeAdapter(it.first, it.second)
        }
    }.create()
}

class EnvironmentAdapter : JsonSerializer<Environment>, JsonDeserializer<Environment> {
    @Throws(JsonParseException::class)
    override fun deserialize(json: JsonElement, typeOfT: Type, context: JsonDeserializationContext): Environment? {
        try {
            val element = json.asJsonObject
            if (element.has("url")) {
                val url = element.getAsJsonPrimitive("url").asString
                return when (url) {
                    Environment.Production().mainUrl -> Environment.Production()
                    Environment.Staging().mainUrl -> Environment.Staging()
                    Environment.Debug().mainUrl -> Environment.Debug()
                    else -> Environment.Custom(url)
                }
            } else if (element.has("mainUrl")) {
                val mainUrl = element.getAsJsonPrimitive("mainUrl").asString
                when (mainUrl) {
                    Environment.Production().mainUrl -> return Environment.Production()
                    Environment.Staging().mainUrl -> return Environment.Staging()
                    Environment.Debug().mainUrl -> return Environment.Debug()
                    else -> return Environment.Custom(mainUrl)
                }
            } else {
                return Environment.Production()
            }
        } catch (e: Exception) {
            e.printStackTrace()
            return null
        }
    }

    @Throws(JsonParseException::class)
    override fun serialize(src: Environment, typeOfSrc: Type, context: JsonSerializationContext): JsonElement {
        val element = JsonObject()
        try {
            element.add("mainUrl", JsonPrimitive(src.mainUrl))
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return element
    }
}

internal inline fun <reified T : Any> String.fromJson() = fromJson(T::class)

internal fun <T : Any> String.fromJson(clazz: KClass<T>) = try {
    GsonManager.defaultGson().fromJson(this, clazz.java)
} catch (e: Throwable) {
    throw Error("Error in parsing to ${clazz.java}. Possibly lack of gson serializers. The string to parse: \"$this\"", e)
}