package com.joshua.pageturndemo.readwidget

import android.content.Context
import android.graphics.Canvas
import android.text.Layout
import android.text.StaticLayout
import android.text.TextPaint
import android.text.TextUtils
import java.util.*

open class TextPageAdapter : BasePageAdapter {

    private var mText: String? = null

    private var mPageTextList: MutableList<String?>? = null

    private var mTextPaint: TextPaint? = null

    private var mPageWidth = 0

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, mText: String?) : super(context) {
        this.mText = mText
        init()
    }

    private fun init() {
        mTextPaint = TextPaint()
        mTextPaint!!.isAntiAlias = true
        mTextPaint!!.textSize = CommonUtil.dp2px(mContext, 30f).toFloat()
        mTextPaint!!.color = -0x1000000
        mPageTextList = ArrayList()
    }

    override val pageCount: Int
        get() = mPageTextList!!.size

    override fun onDraw(position: Int, canvas: Canvas?) {
        mPageTextList?.let {
            if(position >= it.size) return@let
            val pageText = it[position]
            val staticLayout = getStaticLayout(pageText, mPageWidth)
            staticLayout.draw(canvas)
        }
    }

    override fun onPageLayoutChanged(pageWidth: Int, pageHeight: Int) {
        if (pageWidth > 0) {
            mPageWidth = pageWidth
            mPageTextList!!.clear()
            if (!TextUtils.isEmpty(mText)) {
                val staticLayout = getStaticLayout(mText, pageWidth)
                val lineHeight = staticLayout.getLineBottom(0) - staticLayout.getLineTop(0)
                val pageMaxlineCount = pageHeight / lineHeight
                if (staticLayout.lineCount > pageMaxlineCount) {

                    val maxPageTextNum = staticLayout.getLineEnd(pageMaxlineCount)
                    var beginIndex = 0
                    while (beginIndex < mText!!.length) {
                        var endIndex = beginIndex + maxPageTextNum
                        if (endIndex > mText!!.length) {
                            endIndex = mText!!.length
                        }
                        val pageText = mText!!.substring(beginIndex, endIndex)
                        mPageTextList!!.add(pageText)
                        beginIndex = endIndex
                    }
                } else {
                    mPageTextList!!.add(mText)
                }
            }
        }
    }

    private fun getStaticLayout(text: String?, width: Int): StaticLayout {
        return StaticLayout(
            text,
            mTextPaint,
            width,
            Layout.Alignment.ALIGN_NORMAL,
            1.0f,
            0.5f,
            true
        )
    }

    fun setText(text: String?) {
        mText = text
        notifyDataSetChanged()
    }
}