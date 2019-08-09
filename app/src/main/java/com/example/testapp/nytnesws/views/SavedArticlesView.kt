package com.example.testapp.nytnesws.views

import android.os.Bundle
import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.example.testapp.nytnesws.models.News
import com.example.testapp.nytnesws.models.Result

@StateStrategyType(value = SkipStrategy::class)
interface SavedArticlesView : MvpView {
    fun showSavedArtciles(newsList: List<News>)
    fun showEmptyList()
    fun openItem(id: Int)
    fun showError(message: String)
}