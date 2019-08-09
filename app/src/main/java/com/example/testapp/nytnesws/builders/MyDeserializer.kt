package com.example.testapp.nytnesws.builders


import com.example.testapp.nytnesws.models.Result
import com.google.gson.*
import com.google.gson.reflect.TypeToken

import java.lang.reflect.Type
import java.util.ArrayList

class MyDeserializer : JsonDeserializer<Result> {

    @Throws(JsonParseException::class)
    override fun deserialize(json: JsonElement, typeOfT: Type, context: JsonDeserializationContext): Result {
        val decodeObj = json.asJsonObject
        val gson = Gson()
        val decode = gson.fromJson(json, Result::class.java)
        var values: List<String>? = null
        var values1: List<String>? = null
        var values2: List<String>? = null
        var values3: List<String>? = null
        if (decodeObj.get("des_facet").isJsonArray) {
            values = gson.fromJson<List<String>>(decodeObj.get("des_facet"), object : TypeToken<List<String>>() {
            }.type)
        } else {
            val single = gson.fromJson(decodeObj.get("des_facet"), String::class.java)
            values = ArrayList()
            values.add(single)
        }
        if (decodeObj.get("des_facet").isJsonArray) {
            values1 = gson.fromJson<List<String>>(decodeObj.get("des_facet"), object : TypeToken<List<String>>() {
            }.type)
        } else {
            val single = gson.fromJson(decodeObj.get("des_facet"), String::class.java)
            values1 = ArrayList()
            values1.add(single)
        }
        if (decodeObj.get("per_facet").isJsonArray) {
            values2 = gson.fromJson<List<String>>(decodeObj.get("per_facet"), object : TypeToken<List<String>>() {
            }.type)
        } else {
            val single = gson.fromJson(decodeObj.get("per_facet"), String::class.java)
            values2 = ArrayList()
            values2.add(single)
        }
        if (decodeObj.get("geo_facet").isJsonArray) {
            values3 = gson.fromJson<List<String>>(decodeObj.get("geo_facet"), object : TypeToken<List<String>>() {
            }.type)
        } else {
            val single = gson.fromJson(decodeObj.get("geo_facet"), String::class.java)
            values3 = ArrayList()
            values3.add(single)
        }
        decode.desFacet = values!!
        decode.orgFacet = values1!!
        decode.perFacet = values2!!
        decode.geoFacet = values3!!
        return decode
    }
}
