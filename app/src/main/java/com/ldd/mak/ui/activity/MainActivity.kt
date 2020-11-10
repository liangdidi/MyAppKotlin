package com.ldd.mak.ui.activity

import com.ldd.mak.R
import com.ldd.mak.ui.activity.jetpack.databinding.DataBindingActivity
import com.ldd.mak.ui.activity.jetpack.livedata.LiveDataActivity
import com.ldd.mak.ui.activity.jetpack.viewmodel.ViewModelActivity
import com.ldd.mak.ui._base.BaseActivity
import com.ldd.mak.ui.adapter.MainAdapter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {
    override fun isWantTitleBar()=true
    override fun getLayoutId()= R.layout.activity_main
    override fun initData() {
        setTitleBarName("首页")

        val list= listOf("1、权限申请","2、列表嵌套","3、获取屏幕分辨率"
            ,"4、加载fragment","5、ViewModel简单使用","6、LiveData简单使用"
            ,"7、DataBinding简单使用","8、MVVM网络请求数据"
        )
        listView.adapter= MainAdapter(mContext,list)

        listView.setOnItemClickListener { parent, view, position, id ->
            when(parent.getItemAtPosition(position)){
                "1、权限申请"-> startActivityCustom(PermissionActivity::class.java)
                "2、列表嵌套"-> startActivityCustom(NestListActivity::class.java)
                "3、获取屏幕分辨率"-> startActivityCustom(GetScreenParamActivity::class.java)
                "4、加载fragment"-> startActivityCustom(LoadingFragmentActivity::class.java)
                "5、ViewModel简单使用"-> startActivityCustom(ViewModelActivity::class.java)
                "6、LiveData简单使用"-> startActivityCustom(LiveDataActivity::class.java)
                "7、DataBinding简单使用"-> startActivityCustom(DataBindingActivity::class.java)
                "8、MVVM网络请求数据"-> startActivityCustom(MVVMNetWorkActivity::class.java)
            }
        }

    }
}