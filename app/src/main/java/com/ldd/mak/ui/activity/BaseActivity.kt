package com.ldd.mak.ui.activity

import android.view.View
import com.ldd.base.ui.activity.BaseLddActivity
import com.ldd.mak.R
import kotlinx.android.synthetic.main.l_coustom_title_bar.*

abstract class BaseActivity : BaseLddActivity() {
    override fun getTitleBarId()= R.layout.l_coustom_title_bar

    override fun initSetting() {}

    override fun setTitleBarName(name: String) {
        tvCommonTitleBarTitle.text=name
    }

    private var firstTime: Long = 0
    fun onCloseActivity(view: View){
        //=====================防止快速点击左上角返回，关闭上个页面=======================
        val secondTime = System.currentTimeMillis()
        if (secondTime - firstTime < 800) {
            return
        }
        firstTime = secondTime
        //=====================防止快速点击左上角返回，关闭上个页面=======================
        finishActivityCustom()
    }

}