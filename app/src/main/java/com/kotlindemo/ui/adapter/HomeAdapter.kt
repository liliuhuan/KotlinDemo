package com.kotlindemo.ui.adapter

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.View
import androidx.core.app.ActivityCompat
import androidx.core.app.ActivityOptionsCompat
import cn.bingoogolapple.bgabanner.BGABanner
import com.kotlindemo.Constants
import com.kotlindemo.R
import com.kotlindemo.base.adapter.BaseRVAdapter
import com.kotlindemo.base.adapter.BaseRVViewHolder
import com.kotlindemo.durationFormat
import com.kotlindemo.mode.HomeBean
import com.kotlindemo.ui.activity.VideoDetailActivity
import com.kotlindemo.util.ImageLoader
import io.reactivex.Observable


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
 * @date：2019/10/18 10:32
 * @version:1.0.0
 * @description: HomeAdapter
 */
class HomeAdapter(private val mContext: Context, mData: ArrayList<HomeBean.Issue.Item>) :
    BaseRVAdapter<HomeBean.Issue.Item>(mData) {
    // banner 作为 RecyclerView 的第一项
    var bannerItemSize = 0

    companion object {
        private const val ITEM_TYPE_BANNER = 1    //Banner 类型
        private const val ITEM_TYPE_TEXT_HEADER = 2   //textHeader
        private const val ITEM_TYPE_CONTENT = 3    //item
    }

    fun setBannerSize(count: Int) {
        this.bannerItemSize = count
    }

    override fun layoutId(viewType: Int): Int {
        return when (viewType) {
            ITEM_TYPE_BANNER -> R.layout.item_home_banner
            ITEM_TYPE_TEXT_HEADER -> R.layout.item_home_header
            else -> R.layout.item_home_content
        }
    }

    override fun bindData(holderBaseRV: BaseRVViewHolder, data: HomeBean.Issue.Item, position: Int) {
        when (getItemViewType(position)) {
            ITEM_TYPE_BANNER -> {
                val bannerItemData: ArrayList<HomeBean.Issue.Item> =
                    mData.take(bannerItemSize).toCollection(ArrayList())
                val bannerFeedList = ArrayList<String>()
                val bannerTitleList = ArrayList<String>()
                //取出banner 显示的 img 和 Title
                Observable.fromIterable(bannerItemData)
                    .subscribe { list ->
                        bannerFeedList.add(list.data?.cover?.feed ?: "")
                        bannerTitleList.add(list.data?.title ?: "")
                    }

                //设置 banner
                with(holderBaseRV) {
                    getView<BGABanner>(R.id.banner).run {
                        setAutoPlayAble(bannerFeedList.size > 1)
                        setData(bannerFeedList, bannerTitleList)
                        setAdapter { banner, _, feedImageUrl, position ->
                            ImageLoader.loadImage(mContext,feedImageUrl, banner.getItemImageView(position), R.drawable.placeholder_banner)
                        }
                    }
                }
                //没有使用到的参数在 kotlin 中用"_"代替
                holderBaseRV.getView<BGABanner>(R.id.banner).setDelegate { _, imageView, _, i ->
                    goToVideoPlayer(mContext as Activity, imageView, bannerItemData[i])
                }
            }
            ITEM_TYPE_TEXT_HEADER -> {
                holderBaseRV.setText(R.id.tvHeader, mData[position + bannerItemSize - 1].data?.text ?: "")
            }
            else -> {
                setVideoItem(holderBaseRV, mData[position + bannerItemSize - 1])
            }
        }
    }

    override fun getItemCount(): Int {
        return when {
            mData.size > bannerItemSize -> mData.size - bannerItemSize + 1
            mData.isEmpty() -> 0
            else -> 1
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when {
            position == 0 -> ITEM_TYPE_BANNER
            mData[position + bannerItemSize - 1].type == "textHeader" -> ITEM_TYPE_TEXT_HEADER
            else -> ITEM_TYPE_CONTENT
        }
    }

    private fun setVideoItem(holder: BaseRVViewHolder, item: HomeBean.Issue.Item) {
        val itemData = item.data

        val defAvatar = R.mipmap.default_avatar
        val cover = itemData?.cover?.feed
        var avatar = itemData?.author?.icon
        var tagText: String? = "#"

        // 作者出处为空，就显获取提供者的信息
        if (avatar.isNullOrEmpty()) {
            avatar = itemData?.provider?.icon
        }
        // 加载封页图
        ImageLoader.loadImage(mContext,cover, holder.getView(R.id.iv_cover_feed), R.drawable.placeholder_banner)

        // 如果提供者信息为空，就显示默认
        ImageLoader.loadImage(mContext,avatar ?: defAvatar, holder.getView(R.id.iv_avatar), R.mipmap.default_avatar)

        holder.setText(R.id.tv_title, itemData?.title ?: "")

        //遍历标签
        itemData?.tags?.take(4)?.forEach {
            tagText += (it.name + "/")
        }
        // 格式化时间
        val timeFormat = durationFormat(itemData?.duration)

        tagText += timeFormat

        holder.setText(R.id.tv_tag, tagText!!)

        holder.setText(R.id.tv_category, "#" + itemData?.category)

        holder.setOnItemClickListener(listener = View.OnClickListener {
            goToVideoPlayer(mContext as Activity, holder.getView(R.id.iv_cover_feed), item)
        })
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