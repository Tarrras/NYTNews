package com.example.testapp.nytnesws.views

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.example.testapp.nytnesws.models.Result

@StateStrategyType(value = AddToEndSingleStrategy::class)
interface NewsView: MvpView {
    fun showNews()
    fun startLoading()
    fun endLoading()
    fun showError(text: String)
}