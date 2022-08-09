package com.widget.puzzle

import android.content.Context
import android.graphics.Bitmap
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.TextPaint
import android.text.TextUtils
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.text.style.ImageSpan
import android.util.AttributeSet
import android.view.View
import androidx.appcompat.widget.AppCompatTextView
import java.util.ArrayList
import java.util.regex.Pattern

/**
 * @description 自定义部分颜色,图片+点击监听的textview
 */
class DiyStyleTextView : AppCompatTextView {
    private var colorRegex: String? = null
    private var color = 0
    private var underlineText = false
    private var imageRegex: String? = null
    private var bitmap: Bitmap? = null

    constructor(context: Context?) : super(context!!) {}
    constructor(context: Context?, attrs: AttributeSet?) : super(context!!, attrs) {}

    fun setUnderlineText(underlineText: Boolean): DiyStyleTextView {
        this.underlineText = underlineText
        return this
    }

    /**
     * 设置需要改变颜色的文本
     * @param colorRegex　需要改变颜色的内容
     * @param color　颜色
     * @return
     */
    fun setColorRegex(colorRegex: String?, color: Int): DiyStyleTextView {
        movementMethod = LinkMovementMethod.getInstance()
        this.colorRegex = colorRegex
        this.color = color
        return this
    }

    fun setImageRegex(imageRegex: String?, bitmap: Bitmap?): DiyStyleTextView {
        movementMethod = LinkMovementMethod.getInstance()
        this.imageRegex = imageRegex
        this.bitmap = bitmap
        return this
    }

    override fun setText(text: CharSequence, type: BufferType) {
        super.setText(setTextStyle(text, false), type)
    }

    fun setDiyTextColor(text: CharSequence?, regularExpression: String?, color: Int, mDiyTextClick: DiyTextClick?) {
        setColorRegex(regularExpression, color).setDiyTextClickListenner(mDiyTextClick).setTextStyle(text, true)
    }

    fun setDiyTextColor(text: CharSequence?, regularExpression: String?, color: Int) {
        setDiyTextColor(text, regularExpression, color, null)
    }

    fun setDiyTextImage(text: CharSequence?, regularExpression: String?, bitmap: Bitmap?, mDiyTextClick: DiyTextClick?) {
        setImageRegex(regularExpression, bitmap).setDiyTextClickListenner(mDiyTextClick).setTextStyle(text, true)
    }

    fun setDiyTextImage(text: CharSequence?, regularExpression: String?, bitmap: Bitmap?) {
        setDiyTextImage(text, regularExpression, bitmap, null)
    }

    private val indexArr: MutableList<Int> = ArrayList()
    private val strArr: MutableList<String> = ArrayList()
    fun setTextStyle(text: CharSequence?, flag: Boolean): CharSequence? {
        if (TextUtils.isEmpty(text)) {
            if (flag) super.setText(text)
            return text
        }
        val styledText = SpannableStringBuilder(text)
        if (!TextUtils.isEmpty(colorRegex)) {
            indexArr.clear()
            strArr.clear()
            val p = Pattern.compile(colorRegex)
            val m = p.matcher(text)
            while (m.find()) {
                strArr.add(m.group())
                indexArr.add(m.start())
            }
            for (i in indexArr.indices) {
                val index = indexArr[i]
                val clickText = strArr[i]
                styledText.setSpan(
                        TextViewClickSpan(clickText),
                        index,
                        index + clickText.length,
                        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
            }
        }
        if (!TextUtils.isEmpty(imageRegex)) {
            indexArr.clear()
            strArr.clear()
            val p = Pattern.compile(imageRegex)
            val m = p.matcher(text)
            while (m.find()) {
                strArr.add(m.group())
                indexArr.add(m.start())
            }
            for (i in indexArr.indices) {
                val index = indexArr[i]
                val clickText = strArr[i]
                styledText.setSpan(
                        ImageSpan(context, bitmap!!),
                        index,
                        index + clickText.length,
                        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
                styledText.setSpan(
                        TextViewClickSpan(clickText),
                        index,
                        index + clickText.length,
                        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
            }
        }
        if (flag) super.setText(styledText)
        return styledText
    }

    private inner class TextViewClickSpan internal constructor(private val clickText: String) : ClickableSpan() {
        override fun updateDrawState(ds: TextPaint) {
            ds.color = color
            ds.isUnderlineText = underlineText //下划线
        }

        override fun onClick(widget: View) { //点击事件
            if (diyTextClickListenner != null) diyTextClickListenner!!.diyTextClick(clickText)
        }
    }

    private var diyTextClickListenner: DiyTextClick? = null

    interface DiyTextClick {
        fun diyTextClick(s: String?)
    }

    fun setDiyTextClickListenner(mDiyTextClick: DiyTextClick?): DiyStyleTextView {
        diyTextClickListenner = mDiyTextClick
        isClickable = true
        return this
    }
}