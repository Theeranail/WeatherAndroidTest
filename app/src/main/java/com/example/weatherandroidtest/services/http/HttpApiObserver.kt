package com.example.weatherandroidtest.services.http

import android.util.Log
import com.google.gson.Gson
import io.reactivex.Observer
import io.reactivex.disposables.Disposable

class HttpApiObserver<T> : Observer<T> {

    private var apiListener: HttpApiListener<T>;

    constructor(apiListener: HttpApiListener<T>) {
        this.apiListener = apiListener
    }

    override fun onComplete() {
        Log.e("RESPONSE", "onComplete : ")
    }

    override fun onSubscribe(d: Disposable?) {
        Log.e("RESPONSE", "onSubscribe : ")
    }

    override fun onNext(value: T) {
        Log.e("RESPONSE", "onNext : ")
        Log.e("RESPONSE", Gson().toJson(value))
        this.apiListener.let {
            it.onRetrieveDone(value);
        }
    }

    override fun onError(e: Throwable?) {
        this.apiListener.let {
            it.onRetrieveError(e?.message.toString());
        }
    }

}