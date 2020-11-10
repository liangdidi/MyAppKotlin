package com.ldd.mak.ui.activity

import androidx.lifecycle.ViewModelProvider
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
            textView3.text="成功了"
        }
    }
}