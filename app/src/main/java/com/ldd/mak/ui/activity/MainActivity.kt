package com.ldd.mak.ui.activity

import android.util.Log
import com.ldd.mak.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity :BaseActivity() {
    override fun isWantTitleBar()=true
    override fun getLayoutId()= R.layout.activity_main
    override fun initData() {
        setTitleBarName("首页")
        tvMain.setOnClickListener {
            Log.i(TAG,"测试")
        }
    }
}