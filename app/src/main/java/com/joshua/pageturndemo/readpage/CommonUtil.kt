package com.joshua.pageturndemo.readpage

import android.content.Context
import android.graphics.Point

object CommonUtil {

    fun dp2px(context: Context, dpValue: Float): Int {
        val scale = context.resources.displayMetrics.density
        return (dpValue * scale + 0.5).toInt()
    }

    fun getIntersectionPoint(
        lineOne_My_pointOne: Point,
        lineOne_My_pointTwo: Point,
        lineTwo_My_pointOne: Point?,
        lineTwo_My_pointTwo: Point?
    ): Point {
        val x1: Float = lineOne_My_pointOne.x.toFloat()
        val y1: Float = lineOne_My_pointOne.y.toFloat()
        val x2: Float = lineOne_My_pointTwo.x.toFloat()
        val y2: Float = lineOne_My_pointTwo.y.toFloat()
        val x3: Float = lineTwo_My_pointOne!!.x.toFloat()
        val y3: Float = lineTwo_My_pointOne.y.toFloat()
        val x4: Float = lineTwo_My_pointTwo!!.x.toFloat()
        val y4: Float = lineTwo_My_pointTwo.y.toFloat()
        val pointX = (((x1 - x2) * (x3 * y4 - x4 * y3) - (x3 - x4) * (x1 * y2 - x2 * y1))
                / ((x3 - x4) * (y1 - y2) - (x1 - x2) * (y3 - y4)))
        val pointY = (((y1 - y2) * (x3 * y4 - x4 * y3) - (x1 * y2 - x2 * y1) * (y3 - y4))
                / ((y1 - y2) * (x3 - x4) - (x1 - x2) * (y3 - y4)))
        return Point(pointX.toInt(), pointY.toInt())
    }
}