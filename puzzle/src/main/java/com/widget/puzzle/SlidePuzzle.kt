package com.widget.puzzle

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Bitmap
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.animation.doOnEnd
import com.widget.puzzle.databinding.LayoutSlidePuzzleBinding
import kotlin.math.abs

class SlidePuzzle : ConstraintLayout {

    private lateinit var mTipText: DiyStyleTextView
    private lateinit var mPuzzle: Puzzle
    private lateinit var mSlideBar: SlideBar
    private var onVerify: ((Boolean) -> Unit)? = null
    private lateinit var ratioStr: String

    fun setOnVerifyListener(listener: (Boolean) -> Unit) {
        onVerify = listener
    }

    fun setDimensionRatio(ratioStr: String) {
        this.ratioStr = ratioStr
        val constraintSet = ConstraintSet()
        constraintSet.clone(binding.cl)
        constraintSet.setDimensionRatio(R.id.puzzle, ratioStr)
        constraintSet.applyTo(binding.cl)

    }

    fun setBitmap(bitmap: Bitmap) {
        mPuzzle.setBitmap(bitmap)
        mSlideBar.setOnDragListener { progress, useTime, verify ->
            //同步滑块位置
            mPuzzle.setProgress(progress)
            //停止滑动时验证
            if (verify) {
                verify(abs(progress * 0.9f - mPuzzle.getCurRandomX()) < 0.018f, useTime)
            } else {
                if (progress == 0f) {
                    binding.bottomTip.tag = null
                    binding.bottomTip.animate().apply {
                        duration = 400
                    }.alpha(1f).scaleY(1f).scaleX(1f).start()
                } else {
                    if (null == binding.bottomTip.tag?.toString()) {
                        binding.bottomTip.tag = "dealAnimate"
                        binding.bottomTip.animate().alpha(0.4f).scaleY(1.1f).scaleX(1.1f).start()
                    }
                }
            }

        }
    }

    private fun verify(isSuccess: Boolean, useTime: Float?) {
        mTipText.text = if (isSuccess && useTime != null) {
            String.format(
                "验证成功: 耗时%.1f秒,打败了%d%%的用户!",
                useTime,
                (99 - ((if (useTime > 1f) useTime - 1f else 0f) / 0.1f)).toInt()
            )
        } else {
            "验证失败: 请重新拖曳滑块到正确的位置!"
        }
        val h = mTipText.height
//      mTipText.translationY = 0f
        mTipText.animate().alpha(1f).translationY(0f).scaleY(1f).start()
        ValueAnimator.ofFloat(1f, 0f).apply {
//            addUpdateListener {
//                mPuzzle.setProgress((1 + it.animatedValue as Float) * mPuzzle.getProgress())
//            }
            duration = 600
            start()
            doOnEnd {
//                mTipText.visibility = View.GONE
                mTipText.animate().alpha(0f).scaleY(0f).translationY(h.toFloat() / 2).start()

            }
        }
        if (isSuccess) {
            mPuzzle.showSuccessAnim()
            mSlideBar.reset()
            onVerify?.invoke(true)
        } else {
            mSlideBar.reset()
            onVerify?.invoke(false)
        }
    }

    private lateinit var binding: LayoutSlidePuzzleBinding
    private fun initView() {
        binding = LayoutSlidePuzzleBinding.inflate(LayoutInflater.from(context), this, true)
        mPuzzle = binding.puzzle
        mSlideBar = binding.slideBar
        mTipText = binding.tipsText
        mTipText.setColorRegex("验证|成功|失败|正确|[\\d\\.%]+", -0x8aeaf)

    }

    constructor(context: Context) : super(context) {
        initView()
    }

    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet) {
        initView()
    }

    constructor(context: Context, attributeSet: AttributeSet, defStyle: Int) : super(
        context,
        attributeSet,
        defStyle
    ) {
        initView()
    }
}