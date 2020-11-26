package com.xylitol.luckydraw

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.xylitol.luckydraw.MainActivity.Companion.iconArray


/**
 * @Author zlm
 * @Date 2020-11-26 15:52
 * @Desc 抽奖转盘的适配器
 */
class LuckyDrawAdapter(var onClick: LuckyDrawView.DrawClickListener) :
    RecyclerView.Adapter<LuckyDrawAdapter.LuckyDrawItem>() {

    var posMap =
        mapOf<Int, Int>(0 to 0, 1 to 1, 2 to 2, 3 to 7, 4 to 8, 5 to 3, 6 to 6, 7 to 5, 8 to 4)
    private var selectPosition = -1//当前选中需要常亮的

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LuckyDrawItem {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item_lucky_draw, parent, false)
        return LuckyDrawItem(view, onClick)
    }

    override fun onBindViewHolder(holder: LuckyDrawItem, position: Int) {
        holder.init(posMap[position], selectPosition)
    }

    override fun getItemCount(): Int {
        return 9
    }

    fun setSelectionPosition(selectPos: Int) {
        val lastPos = selectPosition
        selectPosition = selectPos
        if (lastPos != -1) {
            notifyItemChanged(reversePosition(lastPos))
        } else {
            notifyDataSetChanged()
        }
        notifyItemChanged(reversePosition(selectPos))
    }

    /**
     * 获取真实坐标
     */
    private fun reversePosition(selectPos: Int): Int {
        for ((key, value) in posMap.entries) {
            if (value == selectPos) {
                return key
            }
        }
        return -1
    }

    class LuckyDrawItem(itemView: View, var onClick: LuckyDrawView.DrawClickListener) :
        RecyclerView.ViewHolder(itemView) {
        //这里做一个映射，将真实位置和抽奖机位置进行映射

        private var ivBg: ImageView = itemView.findViewById(R.id.iv_bg)
        private var ivLottery: ImageView = itemView.findViewById(R.id.iv_lottery)
        private var tvName: TextView = itemView.findViewById(R.id.tv_lottery_name)
        private var viewShadow: View = itemView.findViewById(R.id.view_shadow)

        /**
         * 将坐标传入，里面屏蔽adapter的内容，全部展示看到的坐标
         */
        fun init(fakePos: Int?, selectPos: Int) {
            if (fakePos == 8) {
                //抽奖按钮
                ivBg.setBackgroundResource(R.mipmap.icon_lottery)
                ivLottery.visibility = View.GONE
                tvName.visibility = View.GONE
                viewShadow.visibility = View.GONE
                ivBg.setOnClickListener {
                    onClick.onClickDraw()
                }
            } else {
                ivBg.setBackgroundResource(R.mipmap.bg_lottery_item)
                ivLottery.visibility = View.VISIBLE
                tvName.visibility = View.VISIBLE
                viewShadow.visibility =
                    if (selectPos == -1 || selectPos == fakePos) View.GONE else View.VISIBLE
                ivLottery.setImageResource(iconArray[fakePos!!])
                tvName.text = "ac娘$fakePos"
            }
        }

    }
}