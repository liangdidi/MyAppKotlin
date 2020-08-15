package com.ldd.mak.ui.activity.jetpack.viewmodel

import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import com.ldd.mak.R
import com.ldd.mak.ui._base.BaseActivity
import kotlinx.android.synthetic.main.ac_jetpack_view_model.*

/**
 * ViewModel简单使用
 * ViewModel 类让数据可在发生屏幕旋转等配置更改后继续留存
 */
class ViewModelActivity : BaseActivity() {
    override fun isWantTitleBar() = true
    override fun getLayoutId() = R.layout.ac_jetpack_view_model

    val myViewModel: MyViewModel by lazy {
        ViewModelProvider(this).get(MyViewModel::class.java)
    }

    override fun initData() {
        setTitleBarName("ViewModel简单使用")
        setData()

        btn_plus.setOnClickListener {
            myViewModel.number++
            setData()
        }
        btn_minus.setOnClickListener {
            myViewModel.number--
            setData()
        }
    }

    private fun setData(){
        tvNumber.text=myViewModel.number.toString()
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.i(TAG,"=======onCreate=======")
    }
    override fun onStart() {
        super.onStart()
        Log.i(TAG,"=======onStart=======")
    }
    override fun onResume() {
        super.onResume()
        Log.i(TAG,"=======onResume=======")
    }
    override fun onPause() {
        super.onPause()
        Log.i(TAG,"=======onPause=======")
    }
    override fun onStop() {
        super.onStop()
        Log.i(TAG,"=======onStop=======")
    }
    override fun onDestroy() {
        super.onDestroy()
        Log.i(TAG,"=======onDestroy=======")
    }


}