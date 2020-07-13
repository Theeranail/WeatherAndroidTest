package com.example.weatherandroidtest.services.http.apiobservable

import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

open class HttpApiObservableBase {
    fun initBaseObservable(observable: Observable<*>): Observable<*> {
        return observable.observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io())
    }
}