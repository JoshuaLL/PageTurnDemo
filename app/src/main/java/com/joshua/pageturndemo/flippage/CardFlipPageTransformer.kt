package com.joshua.pageturndemo.flippage

import android.view.View
import androidx.viewpager.widget.ViewPager

class CardFlipPageTransformer : ViewPager.PageTransformer {
    var isScalable = true
    private var flipOrientation = VERTICAL
    override fun transformPage(page: View, position: Float) {
        val percentage = 1 - Math.abs(position)
        page.cameraDistance = 12000f
        setVisibility(page, position)
        setTranslation(page)
        setSize(page, position, percentage)
        setRotation(page, position, percentage)
    }

    private fun setVisibility(page: View, position: Float) {
        if (position < 0.5 && position > -0.5) {
            page.visibility = View.VISIBLE
        } else {
            page.visibility = View.INVISIBLE
        }
    }

    private fun setTranslation(page: View) {
        val viewPager = page.parent as ViewPager
        val scroll = viewPager.scrollX - page.left
        page.translationX = scroll.toFloat()
    }

    private fun setSize(page: View, position: Float, percentage: Float) {
        // Do nothing, if its not scalable
        if (!isScalable) return
        page.scaleX = if (position != 0f && position != 1f) percentage else 1f
        page.scaleY = if (position != 0f && position != 1f) percentage else 1f
    }

    private fun setRotation(page: View, position: Float, percentage: Float) {
        if (flipOrientation == VERTICAL) {
            if (position > 0) {
                page.rotationY = -180 * (percentage + 1)
            } else {
                page.rotationY = 180 * (percentage + 1)
            }
        } else {
            if (position > 0) {
                page.rotationX = -180 * (percentage + 1)
            } else {
                page.rotationX = 180 * (percentage + 1)
            }
        }
    }

    fun getFlipOrientation(): Int {
        return flipOrientation
    }

    fun setFlipOrientation(flipOrientation: Int) {
        this.flipOrientation = if (flipOrientation > 1) VERTICAL else HORIZONTAL
    }

    companion object {
        const val HORIZONTAL = 1
        const val VERTICAL = 2
    }
}