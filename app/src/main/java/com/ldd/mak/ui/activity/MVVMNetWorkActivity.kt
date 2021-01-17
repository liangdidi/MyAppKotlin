package com.ldd.mak.ui.activity

import android.util.Log
import com.google.gson.Gson
import com.ldd.mak.R
import com.ldd.mak.mvvm.model.entity.LoginEntity
import com.ldd.mak.mvvm.viewmodel.LoginViewModel
import com.ldd.mak.ui._base.BaseVMActivity
import kotlinx.android.synthetic.main.ac_mvvm_network.*
import java.util.*

class MVVMNetWorkActivity: BaseVMActivity<LoginViewModel>() {
    override fun isWantTitleBar()=true
    override fun getLayoutId()= R.layout.ac_mvvm_network
    override fun getViewModelClass()=LoginViewModel::class.java

    override fun initData() {
        button.setOnClickListener {
            val map = LinkedHashMap<String,String>()
            map["userName"] = "20020"
            map["password"] = "123456"
            map["max"] = "123"
            mViewModel.login(map)
        }
        mViewModel.logData.observe(this){
            textView3.text= Gson().toJson(it)
        }
        mViewModel.viewIsEnable.observe(this){
            button.isEnabled=it
        }

        val test1=intent.getStringExtra("test1")
        val test2=intent.getStringExtra("test2")

        Log.i("ldd", "======test1=$test1")
        Log.i("ldd", "======test2=$test2")
    }

}