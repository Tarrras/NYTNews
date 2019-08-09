package com.example.testapp.nytnesws.presenters

import android.os.Bundle
import android.os.Handler
import android.util.Log
import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.example.testapp.nytnesws.builders.NetworkService
import com.example.testapp.nytnesws.fragments.ItemDetailFragment
import com.example.testapp.nytnesws.models.Example
import com.example.testapp.nytnesws.models.Result
import com.example.testapp.nytnesws.views.TidingsView
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import retrofit2.HttpException

@InjectViewState
class TidingsPresenter : MvpPresenter<TidingsView>() {
    fun loadTidings(id: Int) {
        viewState.startLoading()
        var observable: Single<Example>? = null
        when(id){
            0 -> observable = NetworkService.instance.jsonApi.mostEmailed
            1 -> observable = NetworkService.instance.jsonApi.mostShared
            2 -> observable = NetworkService.instance.jsonApi.mostViewed
        }
        observable?.
            subscribeOn(Schedulers.io())?.
            observeOn(AndroidSchedulers.mainThread())?.
            subscribe({
                viewState.endLoading()
                viewState.setupNewsList(it.results)
        }, {
            viewState.endLoading()
            if (it is HttpException) {
                viewState.showError(message = it.response()?.errorBody()?.string().orEmpty())
            } else Log.d("Bolno", it.localizedMessage)
        })

    }

    fun openNews(result: Result){
        val bundle = Bundle()
        bundle.putString(
            ItemDetailFragment.EXTRA_NEWS_CATEGORY,
            result.section
        )
        bundle.putString(
            ItemDetailFragment.EXTRA_NEWS_ABSTRACT,
            result.abstract
        )
        bundle.putString(
            ItemDetailFragment.EXTRA_NEWS_AUTHOR,
            result.byline
        )
        bundle.putString(
            ItemDetailFragment.EXTRA_NEWS_TITLE,
            result.title
        )
        bundle.putString(
            ItemDetailFragment.EXTRA_NEWS_URL,
            result.url
        )
        bundle.putString(
            ItemDetailFragment.EXTRA_NEWS_IMAGE,
            result.media[0].mediaMetadata[2].url
        )
        viewState.openNews(bundle = bundle)
    }

}