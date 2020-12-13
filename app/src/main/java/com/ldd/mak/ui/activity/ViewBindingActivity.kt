package com.ldd.mak.ui.activity

import com.ldd.mak.databinding.AcViewBindingBinding
import com.ldd.mak.ui._base.BaseVBActivity

/**
 * ViewBinding使用
 */
class ViewBindingActivity:BaseVBActivity<AcViewBindingBinding>() {
    override fun initData() {
        mViewBinding.tv1.text="我是TextView1"
        mViewBinding.tv2.text="我是TextView2"
        mViewBinding.tv3.text="我是TextView3"
        mViewBinding.tv4.text="我是TextView4"
    }
}