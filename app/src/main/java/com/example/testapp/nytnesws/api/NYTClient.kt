package com.example.testapp.nytnesws.api

import com.example.testapp.nytnesws.models.Example
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.Single
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers

interface NYTClient {
    //@Headers("Content-Type: text/json")
    @get:Headers("Content-Type: application/json")
    @get:GET("/svc/mostpopular/v2/emailed/30.json?api-key=dQExmyLAmYYqgwYDpBP9mvNRCCQrx2AP")
    val mostEmailed: Single<Example>

    //@Headers("Content-Type: text/json")
    @get:Headers("Content-Type: application/json")
    @get:GET("/svc/mostpopular/v2/shared/30.json?api-key=dQExmyLAmYYqgwYDpBP9mvNRCCQrx2AP")
    val mostShared: Single<Example>

    //@Headers("Content-Type: text/json")
    @get:Headers("Content-Type: application/json")
    @get:GET("/svc/mostpopular/v2/viewed/30.json?api-key=dQExmyLAmYYqgwYDpBP9mvNRCCQrx2AP")
    val mostViewed: Single<Example>
}
