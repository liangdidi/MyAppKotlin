package com.ldd.base.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

/**
 * 基本的ViewHolder
 */
class BaseListVH {
    lateinit var mConvertView: View
    private val mapView = mutableMapOf<Int, View>()

    constructor()
    constructor(mContext: Context, layoutId: Int, parent: ViewGroup?) : this() {
        mConvertView = LayoutInflater.from(mContext).inflate(layoutId, parent, false)
        mConvertView.tag = this
    }

    fun get(mContext: Context, layoutId: Int, convertView: View?, parent: ViewGroup?): BaseListVH {
        return if (null == convertView) {
            BaseListVH(mContext, layoutId, parent)
        } else {
            convertView.tag as BaseListVH
        }
    }

    fun <T : View> getView(viewId: Int): T {
        var view = mapView[viewId]
        if (null == view) {
            view = mConvertView.findViewById(viewId)
            mapView[viewId] = view
        }
        return view as T
    }

}