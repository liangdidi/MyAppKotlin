package com.ldd.mak.ui.activity

import android.util.Log
import androidx.lifecycle.ViewModelProvider
import com.google.gson.Gson
import com.ldd.mak.R
import com.ldd.mak.mvvm.viewmodel.LoginViewModel
import com.ldd.mak.ui._base.BaseActivity
import kotlinx.android.synthetic.main.ac_mvvm_network.*
import java.util.*

class MVVMNetWorkActivity:BaseActivity() {
    override fun isWantTitleBar()=true
    override fun getLayoutId()= R.layout.ac_mvvm_network

    private val myViewModel: LoginViewModel by lazy {
        ViewModelProvider(this).get(LoginViewModel::class.java)
    }

    override fun initData() {
        button.setOnClickListener {
            val map = LinkedHashMap<String, String>()
            map["userName"] = "20020"
            map["password"] = "123456"
            myViewModel.login(map)
        }

        myViewModel.loginData.observe(this){
            textView3.text= Gson().toJson(it)
        }

        val test1=intent.getStringExtra("test1")
        val test2=intent.getStringExtra("test2")

        Log.i("ldd", "======test1=$test1")
        Log.i("ldd", "======test2=$test2")
    }
}