package com.ldd.mak.ui.activity.jetpack.livedata

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
/**
 * ViewModel 类让数据可在发生屏幕旋转等配置更改后继续留存。
 * LiveData 是一种可观察的数据存储器类，具有生命周期感知能力
 */
class MyViewModel:ViewModel() {
    val number:MutableLiveData<Int> by lazy {
        MutableLiveData<Int>()
    }

    init {
        number.value=0
    }
}