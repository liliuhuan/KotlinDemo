package com.kotlindemo.ui.adapter

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.View
import androidx.core.app.ActivityCompat
import androidx.core.app.ActivityOptionsCompat
import com.kotlindemo.Constants
import com.kotlindemo.R
import com.kotlindemo.base.adapter.BaseRVAdapter
import com.kotlindemo.base.adapter.BaseRVViewHolder
import com.kotlindemo.durationFormat
import com.kotlindemo.mode.HomeBean
import com.kotlindemo.ui.activity.VideoDetailActivity
import com.kotlindemo.util.ImageLoader


/************************************************************
 *
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
 *************************************************************
 * @author: 李刘欢
 * @date：2019/10/18 15:49
 * @version:1.0.0
 * @description: FollowHorizontalAdapter
 */
class FollowHorizontalAdapter(private val mContext : Context, mData : ArrayList<HomeBean.Issue.Item>):
    BaseRVAdapter<HomeBean.Issue.Item>(mData) {

    override fun layoutId(viewType: Int): Int =  R.layout.item_follow_horizontal

    override fun bindData(holderBaseRV: BaseRVViewHolder, data: HomeBean.Issue.Item, position: Int) {
        val horizontalItemData = data.data
        ImageLoader.loadImage(mContext,data.data?.cover?.feed!!,holderBaseRV.getView(R.id.iv_cover_feed),R.drawable.placeholder_banner)
        holderBaseRV.setText(R.id.tv_title, horizontalItemData?.title ?: "")
        val timeFormat = durationFormat(horizontalItemData?.duration)
        with(holderBaseRV) {
            if (horizontalItemData?.tags != null && horizontalItemData.tags.size > 0) {
                setText(R.id.tv_tag, "#${horizontalItemData.tags[0].name} / $timeFormat")
            }else{
                setText(R.id.tv_tag,"#$timeFormat")
            }

            setOnItemClickListener(listener = View.OnClickListener {
                goToVideoPlayer(mContext as Activity, holderBaseRV.getView(R.id.iv_cover_feed), data)
            })
        }
    }
    private fun goToVideoPlayer(activity: Activity, view: View, itemData: HomeBean.Issue.Item) {
        val intent = Intent(activity, VideoDetailActivity::class.java)
        intent.putExtra(Constants.BUNDLE_VIDEO_DATA, itemData)
        intent.putExtra(VideoDetailActivity.TRANSITION, true)
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            val pair = androidx.core.util.Pair(view, VideoDetailActivity.IMG_TRANSITION)
            val activityOptions = ActivityOptionsCompat.makeSceneTransitionAnimation(activity, pair)
            ActivityCompat.startActivity(activity, intent, activityOptions.toBundle())
        } else {
            activity.startActivity(intent)
            activity.overridePendingTransition(R.anim.anim_in, R.anim.anim_out)
        }
    }
}