package com.ldd.mak.ui.activity

import com.ldd.mak.R
import kotlinx.android.synthetic.main.ac_get_screen_param.*


/**
 * 加载Fragment
 */
class LoadingFragmentActivity :BaseActivity() {
    override fun isWantTitleBar()=true

    override fun getLayoutId()=R.layout.ac_loading_fragment

    override fun initData() {
    }
}