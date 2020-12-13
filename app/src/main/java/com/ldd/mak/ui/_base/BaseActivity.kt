package com.ldd.mak.ui._base

import android.os.Bundle
import android.util.Log
import android.view.View
import com.ldd.base.ui.activity.BaseLddActivity
import com.ldd.mak.R
import kotlinx.android.synthetic.main.ac_base.*
import kotlinx.android.synthetic.main.l_base_title_bar.*

/**
 * 基本Activity
 */
abstract class BaseActivity : BaseLddActivity() {
    /**是否使用DataBinding**/
    var isUseDataBinding=false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //初始化设置
        initSetting()
        //设置主布局
        setContentView(R.layout.ac_base)

        //加载子类布局
        if (getLayoutId() != 0) {
            if(isUseDataBinding){
                //初始化DataBinding
                initDataBinding()
            }else{
                //是否想要公共标题栏
                if (isWantTitleBar()) {
                    View.inflate(mContext, getLayoutId(), llBaseContent)
                } else {
                    setContentView(getLayoutId())
                }
            }
            //初始化ViewModel
            initViewModel()
            //初始化数据
            initData()
        }else{
            showToast("没有设置布局ID")
        }

    }

    /**
     * 设置标题名称
     */
    open fun setTitleBarName(name:String){
        tvDefaultTitleBarTitle.text=name
    }


    //========================================子类实现的方法=============================================================================
    /**是否想要公共标题栏 */
    protected abstract fun isWantTitleBar(): Boolean
    /**设置布局文件的ID */
    protected abstract fun getLayoutId(): Int
    /**初始化数据 */
    protected abstract fun initData()

    /**
     * 供子类BaseVMActivity 初始化ViewModel操作
     */
    protected open fun initViewModel(){}

    /**
     * 供子类BaseVMDBActivity 初始化DataBinding操作
     */
    protected open fun initDataBinding() {}

    /**
     * 初始化设置
     * 重写方法，自定义设置 如：更改沉浸式状态栏
     * */
    protected open fun initSetting() {}



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