package com.ldd.mak.mvvm.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ldd.mak.mvvm.model._base.BaseViewModel
import com.ldd.mak.mvvm.model.entity.LoginEntity
import java.util.*

class LoginViewModel: BaseViewModel() {
    private val _loginData by lazy { MutableLiveData<LoginEntity>() }
    val logData: LiveData<LoginEntity>
        get() = _loginData

    fun login(map:Map<String,String>) {
        request(_loginData) {
            apiServer.login("登录", map)
        }
    }

}