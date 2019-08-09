package com.example.testapp.nytnesws.presenters

import android.content.Context
import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.example.testapp.nytnesws.database.NewsDatabase
import com.example.testapp.nytnesws.models.News
import com.example.testapp.nytnesws.views.SavedArticlesView
import io.reactivex.*
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.util.*

@InjectViewState
class SavedArticlesPresenter: MvpPresenter<SavedArticlesView>() {
    fun showSavedArticles(context: Context){
        val disposable = CompositeDisposable()
        val database = NewsDatabase.instance(context)
        val single: Single<List<News>> = database.newsDAO.getAllNews()
        single
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object: SingleObserver<List<News>>{
                override fun onSuccess(t: List<News>) {
                    if(t.isEmpty()){
                        viewState.showEmptyList()
                    }
                    else viewState.showSavedArtciles(t)
                }

                override fun onSubscribe(d: Disposable) {
                    disposable.add(d)
                }

                override fun onError(e: Throwable) {
                    viewState.showError(e.localizedMessage)
                }

            })

    }

    fun deleteArticle(context: Context,news: News){
        val database = NewsDatabase.instance(context)
        val disposable = Observable.create(
            ObservableOnSubscribe<Any> {
                database.newsDAO.deleteArticle(news)
            }
        )
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe()
    }

    fun openSavedArticles(id: Int){
        viewState.openItem(id)
    }
}