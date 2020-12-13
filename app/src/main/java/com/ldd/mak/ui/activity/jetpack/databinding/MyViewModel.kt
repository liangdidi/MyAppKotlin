package com.ldd.mak.ui.activity.jetpack.databinding

import androidx.lifecycle.MutableLiveData
import com.ldd.mak.mvvm.model._base.BaseViewModel

/**
 * ViewModel 类让数据可在发生屏幕旋转等配置更改后继续留存。
 * LiveData 是一种可观察的数据存储器类，具有生命周期感知能力
 * DataBinding 以声明方式将可观察数据绑定到界面元素
 */
class MyViewModel: BaseViewModel() {
    val number:MutableLiveData<Int> by lazy {
        MutableLiveData<Int>()
    }

    init {
        number.value=0
    }

    fun plus(){
        number.value?.let {
            number.value = it + 1
        }
    }
    fun minus(){
        number.value?.let {
            number.value = it - 1
        }
    }

}