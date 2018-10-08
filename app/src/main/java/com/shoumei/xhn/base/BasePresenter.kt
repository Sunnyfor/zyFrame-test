package com.shoumei.xhn.base

import io.reactivex.disposables.CompositeDisposable

/**
 * Presenter父类
 * Created by zhangye on 2018/8/2.
 */
abstract class BasePresenter<T:IBaseView>(var view: T?) {

    val composites = CompositeDisposable()

    abstract fun onCreate()

    abstract fun onClose()

    fun onDestroy(){
        composites.dispose()
        onClose()
    }

}