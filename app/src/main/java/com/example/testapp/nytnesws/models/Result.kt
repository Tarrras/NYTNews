package com.example.testapp.nytnesws.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Result (

    @SerializedName("url")
    @Expose
    var url: String,
    @SerializedName("adx_keywords")
    @Expose
    var adxKeywords: String,
    @SerializedName("subsection")
    @Expose
    var subsection: String,
    @SerializedName("email_count")
    @Expose
    var emailCount: Long,
    @SerializedName("count_type")
    @Expose
    var countType: String,
    @SerializedName("column")
    @Expose
    var column: Any,
    @SerializedName("eta_id")
    @Expose
    var etaId: Long,
    @SerializedName("section")
    @Expose
    var section: String,
    @SerializedName("id")
    @Expose
    var id: Long,
    @SerializedName("asset_id")
    @Expose
    var assetId: Long,
    @SerializedName("nytdsection")
    @Expose
    var nytdsection: String,
    @SerializedName("byline")
    @Expose
    var byline: String,
    @SerializedName("type")
    @Expose
    var type: String,
    @SerializedName("title")
    @Expose
    var title: String,
    @SerializedName("abstract")
    @Expose
    var abstract: String,
    @SerializedName("published_date")
    @Expose
    var publishedDate: String,
    @SerializedName("source")
    @Expose
    var source: String,
    @SerializedName("updated")
    @Expose
    var updated: String,
    //    @SerializedName("des_facet")
    //    @Expose
    var desFacet: List<String>,
    //    @SerializedName("org_facet")
    //    @Expose
    var orgFacet: List<String>,
    //    @SerializedName("per_facet")
    //    @Expose
    var perFacet: List<String>,
    //    @SerializedName("geo_facet")
    //    @Expose
    var geoFacet: List<String>,
    @SerializedName("media")
    @Expose
    var media: List<Medium>,
    @SerializedName("uri")
    @Expose
    var uri: String
)
