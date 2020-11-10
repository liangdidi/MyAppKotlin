package com.ldd.mak.mvvm.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.ldd.mak.mvvm.model.entity.LoginEntity
import com.ldd.mak.mvvm.model.network.BaseObserver
import com.ldd.mak.mvvm.model.network.BaseView
import com.ldd.mak.mvvm.model.network.RetrofitUtil
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class LoginRepository {

    fun login(map: MutableMap<String, String>): LiveData<LoginEntity> {
        val mutableLiveData: MutableLiveData<LoginEntity> = MutableLiveData<LoginEntity>()
        Log.i("ldd", "=====login======="+Gson().toJson(map))
        RetrofitUtil.getInstance().getApiService().login("登录", map)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : BaseObserver<LoginEntity>() {
                override fun onSuccess(data: LoginEntity) {
                    mutableLiveData.value=data
                }

                override fun onError(msg: String) {

                }
            })
        return mutableLiveData
    }

}