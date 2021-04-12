package com.joshua.pageturndemo.readwidget

import android.content.Context
import android.graphics.Canvas

abstract class BasePageAdapter(protected var mContext: Context) {
    private var mPageWidth = 0

    private var mPageHeight = 0

    abstract val pageCount: Int

    abstract fun onDraw(position: Int, canvas: Canvas?)

    abstract fun onPageLayoutChanged(pageWidth: Int, pageHeight: Int)

    fun pageLayoutChange(pageWidth: Int, pageHeight: Int) {
        mPageWidth = pageWidth
        mPageHeight = pageHeight
        onPageLayoutChanged(pageWidth, pageHeight)
    }

    fun notifyDataSetChanged() {
        onPageLayoutChanged(mPageWidth, mPageHeight)
    }
}