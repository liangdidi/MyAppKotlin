package com.ldd.mak.ui.activity

import com.ldd.mak.R
import com.ldd.mak.ui._base.BaseActivity


/**
 * 加载Fragment
 */
class LoadingFragmentActivity : BaseActivity() {
    override fun isWantTitleBar()=true

    override fun getLayoutId()=R.layout.ac_loading_fragment

    override fun initData() {
    }
}