package com.joshua.pageturndemo.readwidget.computer

import android.graphics.Point
import android.util.Log

class DefaultPointComputer : PointComputer {
    override fun getPageTurnedPoint(width: Int, height: Int, a: Point, isAnim: Boolean): Point {
        var a = a
        val f = getBeforePageTurnedPoint(width, height, a, isAnim)
        val a1 = 1f * (f.y - a.y) / (f.x - a.x)
        val b1 = a.y - a.x * a1
        val a2 = 1f * (a.x - f.x) / (f.y - a.y)
        val b2 = f.y.toFloat()
        val jx = (b2 - b1) / (a1 - a2)
        val j = Point(jx.toInt(), (a1 * jx + b1).toInt())
        val g = Point((f.x + a.x) / 2, (f.y + a.y) / 2)
        val agHalfPoint = Point((g.x + a.x) / 2, (g.y + a.y) / 2)
        val origin_ax = a.x
        val origin_ay = a.y
        if (j.x > agHalfPoint.x && !isAnim) {
            a = Point((j.x * 4 - f.x) / 3, (j.y * 4 - f.y) / 3)
        }
        if (a.y >= height) {
            a.y = height - 1
        }
        Log.i(
            "DefaultPointComputer",
            "j [" + j.x + " , " + j.y + "] , a [" + a.x + " , " + a.y + "] , origin_a [" + origin_ax + " , " + origin_ay + " ]"
        )
        return a
    }

    override fun getBeforePageTurnedPoint(
        width: Int,
        height: Int,
        touchPoint: Point?,
        isAnim: Boolean
    ): Point {
        return Point(width, height)
    }
}