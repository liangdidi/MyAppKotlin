package com.ldd.mak.ui.activity

import android.Manifest
import android.app.Activity
import com.ldd.base.util.PermissionUtil
import com.ldd.mak.R
import kotlinx.android.synthetic.main.ac_permission.*

/**
 * 申请权限
 */
class PermissionActivity : BaseActivity() {
    override fun isWantTitleBar() = true

    override fun getLayoutId() = R.layout.ac_permission

    override fun initData() {
        setTitleBarName("申请权限")


        btnApplySinge.setOnClickListener {
            PermissionUtil
                .init()
                .setPermissions(Manifest.permission.CAMERA)
                .setCallBack(object : PermissionUtil.PermissionCheckCallBack {
                    override fun onEnterNextStep() {
                        showToast("申请成功")
                    }
                })
                .requestPermissions(mContext as Activity)
        }
        btnApplyMore.setOnClickListener {
            PermissionUtil
                .init()
                .setPermissions(
                    Manifest.permission.CAMERA,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                )
                .setCallBack(object : PermissionUtil.PermissionCheckCallBack {
                    override fun onEnterNextStep() {
                        showToast("申请成功")
                    }
                })
                .requestPermissions(mContext as Activity)
        }
    }
}