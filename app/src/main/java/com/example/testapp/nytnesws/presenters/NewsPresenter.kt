package com.example.testapp.nytnesws.presenters

import android.content.Context
import android.os.Handler
import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.example.testapp.nytnesws.database.NewsDatabase
import com.example.testapp.nytnesws.models.News
import com.example.testapp.nytnesws.views.NewsView
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

@InjectViewState
class NewsPresenter: MvpPresenter<NewsView>() {
    fun showNews(){
//        viewState.startLoading()
        Handler().postDelayed({
            viewState.endLoading()
            viewState.showNews()
        },500)
    }

    fun saveNewsToDatabase(news: News, context: Context){
        var isSaved = false
        val database = NewsDatabase.instance(context)
        val disposable = CompositeDisposable()
        val single: Single<List<News>> = database.newsDAO.checkTable(news.title)
        single
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object: SingleObserver<List<News>> {
                override fun onSuccess(t: List<News>) {
                    if(t.isNotEmpty()){
                        viewState.showError("Эта статья уже сохранена!")
                        isSaved = true
                    } else
                        Completable.fromRunnable {
                            database.newsDAO.insertNews(news)
                        }.subscribeOn(Schedulers.io())
                            .subscribe()
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