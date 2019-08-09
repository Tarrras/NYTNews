package com.example.testapp.nytnesws.views

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.example.testapp.nytnesws.models.News
import com.example.testapp.nytnesws.models.Result

@StateStrategyType(value = AddToEndSingleStrategy::class)
interface OpenSavedArticleView: MvpView {
    fun showSavedArticle(news: News)
    fun showError(text: String)
}