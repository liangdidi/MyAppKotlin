package com.ldd.mak.mvvm.model._base

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.google.gson.JsonParseException
import com.google.gson.stream.MalformedJsonException
import com.ldd.mak.mvvm.model.network.ApiServer
import com.ldd.mak.mvvm.model.network.RetrofitUtil
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONException
import retrofit2.HttpException
import java.io.InterruptedIOException
import java.net.ConnectException
import java.net.UnknownHostException
import java.text.ParseException

abstract class BaseViewModel : ViewModel() {
    protected val apiServer: ApiServer by lazy { RetrofitUtil.getInstance().apiService }

    private val _loadingIsShow by lazy { MutableLiveData<Boolean>() }
    val loadingIsShow: LiveData<Boolean>
        get() = _loadingIsShow

    private val _errorMessage by lazy { MutableLiveData<String>() }
    val errorMessage: LiveData<String>
        get() = _errorMessage

    /**
     * 显示加载框
     */
    private fun showLoading() {
        _loadingIsShow.value = true
    }

    /**
     * 隐藏加载框
     */
    private fun hideLoading() {
        _loadingIsShow.value = false
    }

    /**
     * 设置错误信息
     */
    private fun setErrorMessage(message: String) {
        _errorMessage.value = message
    }

    /**
     * 封装公共请求方法
     * @param liveData 可观察的数据存储类
     * @param isShowLoading 是否显示加载框
     * @param block 网络请求方法
     */
    fun <T : BaseEntity> request(
        liveData: MutableLiveData<T>,
        isShowLoading: Boolean = true,
        block: suspend CoroutineScope.() -> T
    ) {
        if (isShowLoading) {
            showLoading()
        }

        viewModelScope.launch {
            try {
                //io线程网络请求
                withContext(Dispatchers.IO) {
                    val data = block()
                    //主线程更新数据
                    withContext(Dispatchers.Main) {
                        when (data.code) {
                            200 -> {
                                if (data.success) {
                                    liveData.value = data
                                } else {
                                    data.msg?.let {
                                        setErrorMessage(it)
                                    }
                                }
                            }
                            else -> {
                                data.msg?.let {
                                    setErrorMessage(it)
                                }
                            }
                        }

                    }

                }
            } catch (e: Exception) {
                onError(e)
            } finally {
                if (isShowLoading) {
                    hideLoading()
                }
            }
        }
    }


    private fun onError(e: Throwable) {
        Log.i("ldd", "====onError==="+Gson().toJson(e))
        when (e) {
            is HttpException -> {
                when (e.code()) {
                    404 -> {
                        setErrorMessage("链接找不到")
                    }
                    else -> {
                        setErrorMessage("网络问题")
                    }
                }
            }
            is ConnectException, is UnknownHostException -> {
                setErrorMessage("连接错误")
            }
            is InterruptedIOException -> {
                setErrorMessage("连接超时")
            }
            is JsonParseException, is JSONException, is MalformedJsonException, is ParseException -> {
                setErrorMessage("解析数据失败")
            }
            else -> {
                setErrorMessage(e.toString())
            }
        }
    }

}