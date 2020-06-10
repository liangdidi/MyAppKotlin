package com.ldd.mak

import kotlinx.android.synthetic.main.activity_main.*

class MainActivity :BaseActivity() {
    override fun isWantTitleBar()=true
    override fun getLayoutId()=R.layout.activity_main
    override fun initData() {
        setTitleBarName("首页")
        tvMain.setOnClickListener {
            startActivityCustom(FirstActivity::class.java)
        }
    }
}