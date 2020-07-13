package com.example.weatherandroidtest.helpers

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import java.lang.reflect.Type
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class DateDeserializer: JsonDeserializer<Date> {
    private var format:String = "yyyy-MM-dd HH:mm:ss"
    constructor(format_:String){
        this.format = format_
    }
    constructor(){

    }
    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): Date? {
        val date = json!!.getAsString()
        val format = SimpleDateFormat(this.format)
        format.setTimeZone(TimeZone.getTimeZone("Asia/Bangkok"));
        try {
            return format.parse(date)
        } catch (exp: ParseException) {
            return null
        }

    }
}