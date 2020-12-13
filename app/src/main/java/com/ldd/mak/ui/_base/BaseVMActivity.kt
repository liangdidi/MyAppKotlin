package com.ldd.mak.ui._base

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.ldd.mak.mvvm.model._base.BaseViewModel

abstract class BaseVMActivity<VM: BaseViewModel>:BaseActivity() {
    protected lateinit var mViewModel:VM
    override fun initViewModel() {
        mViewModel= ViewModelProvider(this).get(getViewModelClass())
        mViewModel.loadingIsShow.observe(this){
            if(it){
                Log.i("ldd","===========显示加载框。。。")
            }else{
                Log.i("ldd","===========隐藏加载框。。。")
            }
        }
        mViewModel.errorMessage.observe(this){
            Toast.makeText(this,it, Toast.LENGTH_SHORT).show()
        }
    }
    abstract fun getViewModelClass():Class<VM>
}