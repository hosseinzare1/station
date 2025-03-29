package ir.romina.hossein.features.map.data.local.entities

import androidx.room.TypeConverter
import ir.romina.hossein.features.map.enums.RentalMethod
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.json.Json

class RentalMethodConverter {

    private val json = Json { isLenient = true; ignoreUnknownKeys = true }

    @TypeConverter
    fun fromRentalMethodList(value: List<RentalMethod>?): String? {
        return value?.let { json.encodeToString(ListSerializer(RentalMethod.serializer()), it) }
    }

    @TypeConverter
    fun toRentalMethodList(value: String?): List<RentalMethod>? {
        return value?.let { json.decodeFromString(ListSerializer(RentalMethod.serializer()), it) }
    }
}