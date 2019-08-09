package com.example.testapp.nytnesws.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Example (
    @SerializedName("status")
    @Expose
    var status: String,
    @SerializedName("copyright")
    @Expose
    var copyright: String,
    @SerializedName("num_results")
    @Expose
    var numResults: Long,
    @SerializedName("results")
    @Expose
    var results: List<Result>
){
    val size: Int get() = results.size
    operator fun get(position: Int):Result = results[position]
}
