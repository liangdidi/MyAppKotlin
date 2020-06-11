package com.ldd.mak

import android.util.Log
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity :BaseActivity() {
    override fun isWantTitleBar()=true
    override fun getLayoutId()=R.layout.activity_main
    override fun initData() {
        setTitleBarName("首页")
        tvMain.setOnClickListener {
            Log.i(TAG,"测试")
            startActivityCustom(FirstActivity::class.java)
        }
    }
}