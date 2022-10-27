package com.nantesmatthew.serinotechexam.core.db

import androidx.room.TypeConverter
import com.google.gson.Gson


class Converters {

    @TypeConverter
    fun stringListToJson(value:List<String>?) = Gson().toJson(value ?: emptyList<String>())

    @TypeConverter
    fun stringListJsonToList(value: String) =
        Gson().fromJson(value, Array<String>::class.java).toList()

}