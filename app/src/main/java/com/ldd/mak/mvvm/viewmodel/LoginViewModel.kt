package com.ldd.mak.mvvm.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ldd.mak.mvvm.model.entity.LoginEntity
import com.ldd.mak.mvvm.model.network.RetrofitUtil
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoginViewModel:ViewModel() {
    private val _loginData=MutableLiveData<LoginEntity>()
    val loginData: LiveData<LoginEntity>
        get()=_loginData

    fun login(map:MutableMap<String,String>){
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                val loginEntity=RetrofitUtil.getInstance().apiService.login("登录", map)
                withContext(Dispatchers.Main){
                    _loginData.value=loginEntity
                }
            }
        }
    }

}