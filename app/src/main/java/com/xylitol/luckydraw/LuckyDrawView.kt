package com.xylitol.luckydraw

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ValueAnimator
import android.content.Context
import android.util.AttributeSet
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

/**
 * @Author xylitol
 * @Date 2020-11-26 15:47
 * @Desc 继承RecyclerView完成一个3X3的抽奖转盘
 */
class LuckyDrawView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : RecyclerView(context, attrs, defStyleAttr) {

    private val animator = ValueAnimator()
    private var luckyIndex = 4//最终抽中的位置
    private var lotteryStatus = 0//当前可以抽奖状态
    private var onDrawClick: DrawClickListener

    private val adapter: LuckyDrawAdapter

    init {
        animator.duration = 2000
        animator.setIntValues(0, 2 * 8 + luckyIndex)
        animator.interpolator = AccelerateDecelerateInterpolator()
        animator.addUpdateListener {
            val position = it.animatedValue as Int
            setCurrentPosition(position % 8)
        }
        animator.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator?) {
                super.onAnimationEnd(animation)
                setCurrentPosition(luckyIndex)
                lotteryStatus = 0
            }
        })
        onDrawClick = object : DrawClickListener {
            override fun onClickDraw() {
                if (lotteryStatus == 0) {
                    animator.start()
                    lotteryStatus = 1
                }
            }
        }
        adapter = LuckyDrawAdapter(onDrawClick)
        setAdapter(adapter)
        layoutManager = object : GridLayoutManager(context, 3) {
            override fun canScrollVertically(): Boolean {
                return false
            }
        }
    }

    private fun setCurrentPosition(position: Int) {
        //刷新当前所在位置
        adapter.setSelectionPosition(position)
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        animator.cancel()
    }

    interface DrawClickListener {
        fun onClickDraw()
    }

}