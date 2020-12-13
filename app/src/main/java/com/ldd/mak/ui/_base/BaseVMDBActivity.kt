package com.ldd.mak.ui._base

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.ldd.mak.mvvm.model._base.BaseViewModel
import kotlinx.android.synthetic.main.ac_base.*

abstract class BaseVMDBActivity<VM: BaseViewModel,DB: ViewDataBinding>:BaseVMActivity<VM>() {

    protected lateinit var mDataBinding: DB

    override fun onCreate(savedInstanceState: Bundle?) {
        isUseDataBinding=true
        super.onCreate(savedInstanceState)
    }

    override fun initDataBinding() {
        //是否想要公共标题栏
        mDataBinding = if (isWantTitleBar()) {
            DataBindingUtil.inflate(layoutInflater, getLayoutId(), llBaseContent, true)
        } else {
            DataBindingUtil.setContentView(this, getLayoutId())
        }
        mDataBinding.lifecycleOwner=this
    }

}