package ir.romina.hossein.core.ui.navigation

import kotlinx.serialization.json.Json
import kotlinx.serialization.KSerializer
import android.net.Uri
import androidx.core.bundle.Bundle
import androidx.navigation.NavType

class CustomNavType<T : Any>(private val serializer: KSerializer<T>) :
    NavType<T>(isNullableAllowed = false) {

    override fun get(bundle: Bundle, key: String): T? {
        val jsonString = bundle.getString(key) ?: return null
        return Json.decodeFromString(serializer, jsonString)
    }

    override fun parseValue(value: String): T {
        return Json.decodeFromString(serializer, Uri.decode(value))
    }

    override fun serializeAsValue(value: T): String {
        return Uri.encode(Json.encodeToString(serializer, value))
    }

    override fun put(bundle: Bundle, key: String, value: T) {
        bundle.putString(key, Json.encodeToString(serializer, value))
    }
}