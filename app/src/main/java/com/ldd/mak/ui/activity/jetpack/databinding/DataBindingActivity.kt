package com.ldd.mak.ui.activity.jetpack.databinding

import com.ldd.mak.R
import com.ldd.mak.databinding.AcJetpackDataBindingBinding
import com.ldd.mak.ui._base.BaseMVVMActivity

/**
 * DataBinding
 * 使用声明性格式将布局中的界面组件绑定到应用中的数据源。
 */
class DataBindingActivity:BaseMVVMActivity<MyViewModel,AcJetpackDataBindingBinding>() {
    override fun isWantTitleBar()=true
    override fun getLayoutId()= R.layout.ac_jetpack_data_binding

    override fun initData() {
        dataBinding.myVM=viewModel
    }

}