package com.example.testapp.nytnesws.views

import android.os.Bundle
import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.example.testapp.nytnesws.models.Result

@StateStrategyType(value = SkipStrategy::class)
interface TidingsView : MvpView {
    fun setupNewsList(newsList: List<Result>)
    fun openNews(bundle: Bundle)
    fun startLoading()
    fun endLoading()
    fun showError(message: String)
}