package com.ldd.mak.mvvm.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.ldd.mak.mvvm.model.entity.LoginEntity
import com.ldd.mak.mvvm.repository.LoginRepository

class LoginViewModel:ViewModel() {

    private val mapParam=MutableLiveData<MutableMap<String,String>>()
    var loginData: LiveData<LoginEntity> = Transformations.switchMap(mapParam) { input -> LoginRepository().login(input) }
    fun login(map:MutableMap<String,String>){
        mapParam.value=map
    }

}