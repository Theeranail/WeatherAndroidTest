package com.example.weatherandroidtest.services.http

import retrofit2.HttpException

interface HttpApiListener<T> {
    fun onRetrieveDone(apiResponse: T)
    fun onRetrieveError(errorMsg: String)
    fun onRetrieveErrorHttpException(httpException: HttpException? = null) {

    }
}