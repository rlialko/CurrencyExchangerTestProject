package com.ruslanlialko.currencyexchanger.data.local.converter


import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class MapTypeConverter {
    private val gson = Gson()

    @TypeConverter
    fun fromMap(map: Map<String, Double>?): String? {
        return gson.toJson(map)
    }

    @TypeConverter
    fun toMap(mapString: String?): Map<String, Double>? {
        return mapString?.let {
            val type = object : TypeToken<Map<String, Double>>() {}.type
            gson.fromJson(it, type)
        }
    }
}