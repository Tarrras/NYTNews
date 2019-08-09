package com.example.testapp.nytnesws.models

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import org.jetbrains.annotations.NotNull
import java.lang.StringBuilder

@Entity(tableName = "news")
data class News(
    @NotNull @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id") var id: Int = 0,
    @ColumnInfo(name = "news_title") var title: String = "",
    @ColumnInfo(name = "news_category") var category: String = "",
    @ColumnInfo(name = "news_url") var newsurl: String = "",
    @ColumnInfo(name = "image_url") var imageurl: String = "",
    @ColumnInfo(name = "abstracts") var abstracts: String = "",
    @ColumnInfo(name = "author") var author: String = ""
) {
    constructor(title: String,category: String,newsurl: String,imageurl: String,abstracts: String,author: String) : this() {
        this.title = title
        this.category = category
        this.newsurl = newsurl
        this.imageurl = imageurl
        this.abstracts = abstracts
        this.author = author
    }
}