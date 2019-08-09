package com.example.testapp.nytnesws.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Medium (

    @SerializedName("type")
    @Expose
    var type: String,
    @SerializedName("subtype")
    @Expose
    var subtype: String,
    @SerializedName("caption")
    @Expose
    var caption: String,
    @SerializedName("copyright")
    @Expose
    var copyright: String,
    @SerializedName("approved_for_syndication")
    @Expose
    var approvedForSyndication: Long,
    @SerializedName("media-metadata")
    @Expose
    var mediaMetadata: List<MediaMetadatum>
)
