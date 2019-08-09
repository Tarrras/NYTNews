package com.example.testapp.nytnesws.database

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Delete
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import com.example.testapp.nytnesws.models.News
import io.reactivex.Flowable
import io.reactivex.Single

@Dao
interface NewsDAO {
    @Query(value = "SELECT * FROM news WHERE id=:newsId")
    fun getNewsById(newsId: Int): Single<News>

    @Query(value = "SELECT * FROM news")
    fun getAllNews(): Single<List<News>>

    @Insert
    fun insertNews(vararg news: News)

    @Delete
    fun deleteArticle(news: News)

    @Query(value = "DELETE FROM news WHERE id=:newsId")
    fun deleteNews(newsId: Int)

    @Query(value = "SELECT * FROM news WHERE news_title=:title")
    fun checkTable(title: String): Single<List<News>>

}