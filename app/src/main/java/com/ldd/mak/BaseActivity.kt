package com.ldd.mak

import com.ldd.base.ui.activity.BaseLddActivity

abstract class BaseActivity : BaseLddActivity() {
    override fun getTitleBarId()=0

    override fun initSetting() {
    }

}