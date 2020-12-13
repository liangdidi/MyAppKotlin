package com.ldd.mak.ui._base

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import androidx.viewbinding.ViewBinding
import com.ldd.base.ui.activity.BaseLddActivity
import java.lang.reflect.ParameterizedType

/**
 * 基本Activity
 */
abstract class BaseVBActivity<VB:ViewBinding> : BaseLddActivity() {

    protected lateinit var mViewBinding: VB

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //利用反射，调用指定ViewBinding中的inflate方法填充视图
        val type = javaClass.genericSuperclass
        if (type is ParameterizedType) {
            val clazz = type.actualTypeArguments[0] as Class<VB>
            val method = clazz.getMethod("inflate", LayoutInflater::class.java)
            mViewBinding = method.invoke(null, layoutInflater) as VB
            setContentView(mViewBinding.root)
        }

        //初始化ViewModel
        initViewModel()
        //初始化数据
        initData()
    }


    //========================================子类实现的方法=============================================================================
    /**初始化数据 */
    protected abstract fun initData()

    /**
     * 供子类BaseVMActivity 初始化ViewModel操作
     */
    protected open fun initViewModel(){}



    private var firstTime: Long = 0
    /**
     * 标题返回键 点击事件
     */
    fun onFinishActivity(view: View) {
        Log.i(TAG, "=====onFinishActivity=====")
        //=====================防止快速点击左上角返回，关闭上个页面=======================
        val secondTime = System.currentTimeMillis()
        if (secondTime - firstTime < 800) {
            return
        }
        firstTime = secondTime
        //=====================防止快速点击左上角返回，关闭上个页面=======================
        finishActivityCustom()
    }


}