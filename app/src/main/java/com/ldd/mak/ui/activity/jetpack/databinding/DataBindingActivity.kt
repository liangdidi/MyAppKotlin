package com.ldd.mak.ui.activity.jetpack.databinding

import android.util.Log
import com.ldd.mak.R
import com.ldd.mak.databinding.AcJetpackDataBindingBinding
import com.ldd.mak.ui._base.BaseVMDBActivity

/**
 * DataBinding
 * 使用声明性格式将布局中的界面组件绑定到应用中的数据源。
 */
class DataBindingActivity:BaseVMDBActivity<MyViewModel,AcJetpackDataBindingBinding>() {
    override fun isWantTitleBar()=true
    override fun getLayoutId()= R.layout.ac_jetpack_data_binding
    override fun getViewModelClass()=MyViewModel::class.java
    override fun initData() {
        mDataBinding.myVM=mViewModel
        val test1=intent.getStringExtra("test1")
        val test2=intent.getStringExtra("test2")

        Log.i("ldd", "======test1=$test1")
        Log.i("ldd", "======test2=$test2")

    }



}