package com.example.testapp.nytnesws.database

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import com.example.testapp.nytnesws.models.News

@Database(entities = [News::class], version = 1)
public abstract class NewsDatabase: RoomDatabase() {
    public abstract val newsDAO: NewsDAO
    companion object{
        public const val DATABASE_NAME = "QWE-Database"
        private var mInstance: NewsDatabase? = null
        fun instance(context: Context):NewsDatabase{
            if(mInstance==null){
                mInstance = Room.databaseBuilder(context, NewsDatabase::class.java, DATABASE_NAME)
                    .fallbackToDestructiveMigration()
                    .build()
            }
            return mInstance as NewsDatabase
        }

    }
}