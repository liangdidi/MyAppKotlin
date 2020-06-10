package com.ldd.mak

import android.util.Log

class MainActivity :BaseActivity() {
    override fun isWantTitleBar()=true
    override fun getLayoutId()=R.layout.activity_main
    override fun initData() {
        Log.i(TAG,"====initData====")
    }
}