package com.seven.myapplication.utils

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.seven.myapplication.model.Posts

class MyConverters  {
    @TypeConverter
    fun fromOptionValuesList(optionValues: List<Posts?>?): String? {
        if (optionValues == null) {
            return null
        }
        val gson = Gson()
        val type =
            object : TypeToken<List<Posts?>?>() {}.type
        return gson.toJson(optionValues, type)
    }

    @TypeConverter
    fun toOptionValuesList(optionValuesString: String?): List<Posts>? {
        if (optionValuesString == null) {
            return null
        }
        val gson = Gson()
        val type =
            object : TypeToken<List<Posts?>?>() {}.type
        return gson.fromJson<List<Posts>>(optionValuesString, type)
    }
}