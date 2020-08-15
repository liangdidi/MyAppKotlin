package com.ldd.mak.ui._base

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProvider
import com.ldd.base.ui.activity.BaseLddActivity
import com.ldd.mak.R
import com.ldd.mak.mvvm.viewmodel.BaseViewModel
import kotlinx.android.synthetic.main.ac_base.*
import kotlinx.android.synthetic.main.l_base_title_bar.*
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type


abstract class BaseMVVMActivity<VM : BaseViewModel, DB : ViewDataBinding> : BaseLddActivity() {

    lateinit var viewModel: VM
    lateinit var dataBinding: DB

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //初始化设置
        initSetting()
        //设置主布局
        setContentView(R.layout.ac_base)

        //加载子类布局
        if (0 != getLayoutId()) {
            //是否想要公共标题栏
            dataBinding = if (isWantTitleBar()) {
                DataBindingUtil.inflate(layoutInflater, getLayoutId(), llBaseContent, true)
            } else {
                DataBindingUtil.setContentView(this, getLayoutId())
            }
            dataBinding.lifecycleOwner = this

            //创建ViewModel
            createViewModel()

            //初始化数据
            initData()
        } else {
            showToast("没有设置布局ID")
        }

    }

    /**创建ViewModel */
    private fun createViewModel() {
        //当前对象的直接超类的 Type
        val type: Type = javaClass.genericSuperclass
        // 参数化类型，即泛型
        val mClass = if (type is ParameterizedType) {
            //返回此类泛型列表
            val actualTypeArguments = type.actualTypeArguments
            actualTypeArguments[0] as Class<VM>
        } else {
            BaseViewModel::class.java as Class<VM>
        }
        viewModel = ViewModelProvider(this).get(mClass)
    }


    /**
     * 设置标题名称
     */
    open fun setTitleBarName(name: String) {
        tvDefaultTitleBarTitle.text = name
    }


    //========================================子类实现的方法=============================================================================
    /**是否想要公共标题栏 */
    protected abstract fun isWantTitleBar(): Boolean

    /**设置布局文件的ID */
    protected abstract fun getLayoutId(): Int

    /**初始化数据 */
    protected abstract fun initData()

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