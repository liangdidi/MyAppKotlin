package com.ldd.mak.ui.activity.jetpack.databinding

import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.ldd.mak.R
import com.ldd.mak.databinding.AcJetpackDataBindingBinding
import com.ldd.mak.ui.base.BaseActivity

/**
 * DataBinding
 * 使用声明性格式将布局中的界面组件绑定到应用中的数据源。
 */
class DataBindingActivity:BaseActivity() {
    override fun isWantTitleBar()=true
    override fun getLayoutId()= 0

    lateinit var binding:AcJetpackDataBindingBinding
    private val myViewModel: MyViewModel by lazy {
        ViewModelProvider(this).get(MyViewModel::class.java)
    }

    override fun initData() {
        binding=DataBindingUtil.setContentView(this,R.layout.ac_jetpack_data_binding)
        binding.myVM=myViewModel
        binding.lifecycleOwner=this
    }
}