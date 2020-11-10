package com.ldd.mak.mvvm.model.network

import android.util.Log
import android.view.View
import com.google.gson.Gson
import com.google.gson.JsonParseException
import com.google.gson.stream.MalformedJsonException
import io.reactivex.observers.DisposableObserver
import org.json.JSONException
import retrofit2.HttpException
import java.io.InterruptedIOException
import java.net.ConnectException
import java.net.UnknownHostException
import java.text.ParseException

/**
 * 公共观察者
 * 处理响应的数据
 */
abstract class BaseObserver<T> : DisposableObserver<T> {
    private val TAG = BaseObserver::class.java.simpleName

    /**公共view */
    private var baseView: BaseView?=null

    /**是否显示加载动画 */
    private var isShowDialog = true

    /**触发请求的view，防止重复提交 */
    private var requestView: View? = null

    constructor(view: BaseView?) {
        baseView = view
    }

    constructor(view: BaseView?, isShowDialog: Boolean) {
        baseView = view
        this.isShowDialog = isShowDialog
    }

    constructor(view: BaseView?, requestView: View?) {
        baseView = view
        this.requestView = requestView
    }

    constructor(baseView: BaseView?,isShowDialog: Boolean,requestView: View?) {
        this.baseView = baseView
        this.isShowDialog = isShowDialog
        this.requestView = requestView
    }

    constructor()

    override fun onStart() {
        if (isShowDialog) {
//            baseView?.showLoading()
        }
        requestView?.isEnabled = false
    }

    override fun onNext(o: T) {
    }

    override fun onError(e: Throwable) {
        Log.i(TAG, "====onError==="+Gson().toJson(e))
        requestView?.isEnabled = true
        when (e) {
            is HttpException -> {
                val code = e.code()
                when (code) {
                    404 -> {
                        onError("链接找不到")
                    }
                    else -> {
                        onError("网络问题")
                    }
                }
            }
            is ConnectException, is UnknownHostException -> {
                onError("连接错误")
            }
            is InterruptedIOException -> {
                onError("连接超时")
            }
            is JsonParseException, is JSONException, is MalformedJsonException, is ParseException -> {
                onError("解析数据失败")
            }
            else -> {
                onError(e.toString())
            }
        }
    }

    override fun onComplete() {
        requestView?.isEnabled = true
    }

    abstract fun onSuccess(data: T)
    abstract fun onError(msg: String)
}