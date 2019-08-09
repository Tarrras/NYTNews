package com.example.testapp.nytnesws.presenters

import android.content.Context
import android.os.Handler
import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.example.testapp.nytnesws.database.NewsDatabase
import com.example.testapp.nytnesws.models.News
import com.example.testapp.nytnesws.views.NewsView
import com.example.testapp.nytnesws.views.OpenSavedArticleView
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

@InjectViewState
class OpenSavedArticlePresenter: MvpPresenter<OpenSavedArticleView>() {
    fun showArticle(context: Context, id: Int){
        val disposable = CompositeDisposable()
        val database = NewsDatabase.instance(context)
        val single: Single<News> = database.newsDAO.getNewsById(id)
        single
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object: SingleObserver<News> {
                override fun onSuccess(t: News) {
                    viewState.showSavedArticle(t)
                }

                override fun onSubscribe(d: Disposable) {
                    disposable.add(d)
                }

                override fun onError(e: Throwable) {
                    viewState.showError(e.localizedMessage)
                }

            })
    }
}