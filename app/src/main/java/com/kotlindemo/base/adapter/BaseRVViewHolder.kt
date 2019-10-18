package com.kotlindemo.base.adapter

import android.annotation.SuppressLint
import android.util.SparseArray
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

/***********************************************************
 *
 *                   .::::.
 *                  .::::::::.
 *                 :::::::::::  COME ON BABY
 *             ..:::::::::::'
 *           '::::::::::::'
 *             .::::::::::
 *        '::::::::::::::..
 *             ..::::::::::::.
 *           ``::::::::::::::::
 *            ::::``:::::::::'        .:::.
 *           ::::'   ':::::'       .::::::::.
 *         .::::'      ::::     .:::::::'::::.
 *        .:::'       :::::  .:::::::::' ':::::.
 *       .::'        :::::.:::::::::'      ':::::.
 *      .::'         ::::::::::::::'         ``::::.
 *  ...:::           ::::::::::::'              ``::.
 * ```` ':.          ':::::::::'                  ::::..
 *                    '.:::::'                    ':'````..
 *
 **************************************************************
 * @author: 李刘欢
 * @date：2019/10/18 10:54
 * @version:1.0.0
 * @description: BaseRVViewHolderHolder.kt
 */

@Suppress("UNCHECKED_CAST")
class BaseRVViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private var mViews: SparseArray<View>? = null

    init {
        mViews = SparseArray()
    }

    fun <T : View> getView(viewId: Int): T {
        var view: View? = mViews?.get(viewId)
        if (view == null) {
            view = itemView.findViewById(viewId)
            mViews?.put(viewId, view)
        }
        return view as T
    }

    @Deprecated("可以用getView")
    internal fun <T : ViewGroup> getViewGroup(viewId: Int): T {
        var view: View? = mViews?.get(viewId)
        if (view == null) {
            view = itemView.findViewById(viewId)
            mViews?.put(viewId, view)
        }
        return view as T
    }

    fun setText(viewId: Int, text: CharSequence): BaseRVViewHolder {
        val view = getView<TextView>(viewId)
        view.text = text
        return this
    }

    fun setHintText(viewId: Int, text: CharSequence): BaseRVViewHolder {
        val view = getView<TextView>(viewId)
        view.hint = text
        return this
    }

    fun setImageResource(viewId: Int, resId: Int): BaseRVViewHolder {
        val iv = getView<ImageView>(viewId)
        iv.setImageResource(resId)
        return this
    }

    fun setImagePath(viewId: Int, imageLoader: HolderImageLoader): BaseRVViewHolder {
        val iv = getView<ImageView>(viewId)
        imageLoader.loadImage(iv, imageLoader.path)
        return this
    }

    abstract class HolderImageLoader(val path: String) {
        abstract fun loadImage(iv: ImageView, path: String)
    }

    fun setViewVisibility(viewId: Int, visibility: Int): BaseRVViewHolder {
        getView<View>(viewId).visibility = visibility
        return this
    }

    fun setOnItemClickListener(listener: View.OnClickListener) {
        itemView.setOnClickListener(listener)
    }

    fun setOnItemLongClickListener(listener: View.OnLongClickListener) {
        itemView.setOnLongClickListener(listener)
    }

}