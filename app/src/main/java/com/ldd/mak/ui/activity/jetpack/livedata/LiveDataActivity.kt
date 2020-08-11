package com.ldd.mak.ui.activity.jetpack.livedata

import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.ldd.mak.R
import com.ldd.mak.ui.base.BaseActivity
import kotlinx.android.synthetic.main.ac_jetpack_view_model.*

/**
 * LiveData 是一种可观察的数据存储器类，具有生命周期感知能力
 */
class LiveDataActivity :BaseActivity() {
    override fun isWantTitleBar()=true
    override fun getLayoutId()= R.layout.ac_jetpack_view_model

    private val myViewModel:MyViewModel by lazy {
        ViewModelProvider(this).get(MyViewModel::class.java)
    }

    override fun initData() {
        myViewModel.number.observe(this, Observer<Int>{
            tvNumber.text=it.toString()
        })

        btn_plus.setOnClickListener {
            myViewModel.number.value?.let {
                myViewModel.number.value = it + 1
            }
        }
        btn_minus.setOnClickListener {
            myViewModel.number.value?.let {
                myViewModel.number.value = it - 1
            }
        }
    }
}