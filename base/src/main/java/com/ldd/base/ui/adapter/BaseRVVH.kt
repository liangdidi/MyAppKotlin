package com.ldd.base.ui.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView

/**
 * 基本的RecyclerView ViewHolder
 */
class BaseRVVH(itemView: View) : RecyclerView.ViewHolder(itemView) {
    var mConvertView: View = itemView
    private val mapView = mutableMapOf<Int, View>()

    fun <T : View> getView(viewId: Int): T {
        var view = mapView[viewId]
        if (null == view) {
            view = mConvertView.findViewById(viewId)
            mapView[viewId] = view
        }
        return view as T
    }
}