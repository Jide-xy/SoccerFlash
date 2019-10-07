package com.jide.soccerflash.room

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.jide.soccerflash.models.*

class ListConverter {

    @TypeConverter
    fun <T> fromGoalListObject(listObject: List<Goal>?): String? {
        if (listObject == null) {
            return null
        }
        val gson = Gson()
        val type = object : TypeToken<List<Goal>>() {}.type
        return gson.toJson(listObject, type)
    }

    @TypeConverter
    fun <T> toGoalListObject(jsonArray: String?): List<Goal>? {
        if (jsonArray == null) {
            return null
        }
        val gson = Gson()
        val type = object : TypeToken<List<Goal>>() {}.type

        return gson.fromJson(jsonArray, type)
    }

    @TypeConverter
    fun <T> fromBookingListObject(listObject: List<Booking>?): String? {
        if (listObject == null) {
            return null
        }
        val gson = Gson()
        val type = object : TypeToken<List<Booking>>() {}.type
        return gson.toJson(listObject, type)
    }

    @TypeConverter
    fun <T> toBookingListObject(jsonArray: String?): List<Booking>? {
        if (jsonArray == null) {
            return null
        }
        val gson = Gson()
        val type = object : TypeToken<List<Booking>>() {}.type

        return gson.fromJson(jsonArray, type)
    }

    @TypeConverter
    fun <T> fromSubstitutionListObject(listObject: List<Substitution>?): String? {
        if (listObject == null) {
            return null
        }
        val gson = Gson()
        val type = object : TypeToken<List<Substitution>>() {}.type
        return gson.toJson(listObject, type)
    }

    @TypeConverter
    fun <T> toSubstitutionListObject(jsonArray: String?): List<Substitution>? {
        if (jsonArray == null) {
            return null
        }
        val gson = Gson()
        val type = object : TypeToken<List<Substitution>>() {}.type

        return gson.fromJson(jsonArray, type)
    }

    @TypeConverter
    fun fromMatchPersonListObject(listObject: List<MatchPerson>?): String? {
        if (listObject == null) {
            return null
        }
        val gson = Gson()
        val type = object : TypeToken<List<MatchPerson>>() {}.type
        return gson.toJson(listObject, type)
    }

    @TypeConverter
    fun toMatchPersonListObject(jsonArray: String?): List<MatchPerson>? {
        if (jsonArray == null) {
            return null
        }
        val gson = Gson()
        val type = object : TypeToken<List<MatchPerson>>() {}.type

        return gson.fromJson(jsonArray, type)
    }

    @TypeConverter
    fun <T> fromStringListObject(listObject: List<String>?): String? {
        if (listObject == null) {
            return null
        }
        val gson = Gson()
        val type = object : TypeToken<List<String>>() {}.type
        return gson.toJson(listObject, type)
    }

    @TypeConverter
    fun <T> toStringListObject(jsonArray: String?): List<String>? {
        if (jsonArray == null) {
            return null
        }
        val gson = Gson()
        val type = object : TypeToken<List<String>>() {}.type

        return gson.fromJson(jsonArray, type)
    }

    @TypeConverter
    fun <T> fromSeasonListObject(listObject: List<Season>?): String? {
        if (listObject == null) {
            return null
        }
        val gson = Gson()
        val type = object : TypeToken<List<Season>>() {}.type
        return gson.toJson(listObject, type)
    }

    @TypeConverter
    fun <T> toSeasonListObject(jsonArray: String?): List<Season>? {
        if (jsonArray == null) {
            return null
        }
        val gson = Gson()
        val type = object : TypeToken<List<Season>>() {}.type

        return gson.fromJson(jsonArray, type)
    }

    @TypeConverter
    fun fromTeamPlayerListObject(listObject: List<TeamPlayer>?): String? {
        if (listObject == null) {
            return null
        }
        val gson = Gson()
        val type = object : TypeToken<List<TeamPlayer>>() {}.type
        return gson.toJson(listObject, type)
    }

    @TypeConverter
    fun <T> toTeamPlayerListObject(jsonArray: String?): List<TeamPlayer>? {
        if (jsonArray == null) {
            return null
        }
        val gson = Gson()
        val type = object : TypeToken<List<TeamPlayer>>() {}.type

        return gson.fromJson(jsonArray, type)
    }
}