package com.xylitol.luckydraw

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import com.xylitol.luckydraw.MainActivity.Companion.iconArray

/**
 * @Author zlm
 * @Date 2020-11-26 18:53
 * @Desc 使用ConstraintLayout实现九宫格
 */
class LuckyDrawConstraintView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private var lotteryArray: Array<View>
    private val animator = ValueAnimator()
    private var luckyIndex = -1
    private var lotteryStatus = 0//当前可以抽奖状态

    init {
        inflate(context, R.layout.layout_lucky_draw, this)
        lotteryArray = arrayOf(
            findViewById(R.id.include_lottery0),
            findViewById(R.id.include_lottery1),
            findViewById(R.id.include_lottery2),
            findViewById(R.id.include_lottery3),
            findViewById(R.id.include_lottery4),
            findViewById(R.id.include_lottery5),
            findViewById(R.id.include_lottery6),
            findViewById(R.id.include_lottery7)
        )
        animator.duration = 2000
        animator.interpolator = AccelerateDecelerateInterpolator()
        animator.addUpdateListener {
            val position = it.animatedValue as Int
            setCurrentPosition(position % 8)
        }
        animator.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator?) {
                super.onAnimationEnd(animation)
                setCurrentPosition(luckyIndex)
                lotteryStatus = 2
            }
        })

        setLotteryInfo(luckyIndex = 4)
    }

    private fun setCurrentPosition(position: Int) {
        //刷新当前所在位置
        for ((index, view) in lotteryArray.withIndex()) {
            view.findViewById<View>(R.id.view_shadow).visibility =
                if (index == position) View.GONE else View.VISIBLE
        }
    }

    fun setLotteryInfo(
        btn: Int = R.mipmap.icon_lottery,
        luckyIndex: Int
    ) {
        this.luckyIndex = luckyIndex
        setLotteryBtn(btn)
        for ((index, view) in lotteryArray.withIndex()) {
            setLotteryItem(view, index)
        }
        animator.setIntValues(0, 2 * 8 + luckyIndex)
    }

    private fun setLotteryItem(view: View, position: Int) {
        view.findViewById<ImageView>(R.id.iv_lottery).setImageResource(iconArray[position])
        view.findViewById<TextView>(R.id.tv_lottery_name).text = "ac娘$position"
    }

    @SuppressLint("ShowToast", "CutPasteId")
    private fun setLotteryBtn(btn: Int = R.mipmap.icon_lottery) {
        findViewById<View>(R.id.include_button).findViewById<View>(R.id.iv_lottery).visibility =
            View.GONE
        findViewById<View>(R.id.include_button).findViewById<View>(R.id.tv_lottery_name).visibility =
            View.GONE
        findViewById<View>(R.id.include_button).findViewById<View>(R.id.iv_bg)
            .setBackgroundResource(btn)
        findViewById<View>(R.id.include_button).setOnClickListener {
            //开始动画
            if (lotteryStatus == 0) {
                animator.start()
                lotteryStatus = 1
            } else if (lotteryStatus == 2) {
                Toast.makeText(context, "您已没有抽奖机会", Toast.LENGTH_LONG)
            }
        }
    }
}

