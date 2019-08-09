package com.example.testapp.nytnesws.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class MediaMetadatum (

    @SerializedName("url")
    @Expose
    var url: String,
    @SerializedName("format")
    @Expose
    var format: String,
    @SerializedName("height")
    @Expose
    var height: Long,
    @SerializedName("width")
    @Expose
    var width: Long
)
