package com.ldd.mak.ui.fragment

import com.ldd.mak.R
import com.ldd.mak.ui._base.BaseFragment
import kotlinx.android.synthetic.main.ac_second.*

class FirstFragment: BaseFragment() {
    override fun getLayoutId()= R.layout.ac_second

    override fun initData() {
        textView2.text="测试加载Fragment"
    }
}