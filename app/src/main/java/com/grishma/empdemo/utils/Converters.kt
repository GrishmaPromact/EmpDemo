package com.grishma.empdemo.utils

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.grishma.empdemo.model.Employee

/**
 * Converter class
 */
class Converters {
    @TypeConverter
    fun listToJson(value: List<Employee.Data>?) = Gson().toJson(value)

    @TypeConverter
    fun jsonToList(value: String) = Gson().fromJson(value, Array<Employee.Data>::class.java).toList()
}