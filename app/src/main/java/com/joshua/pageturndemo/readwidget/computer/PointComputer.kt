package com.joshua.pageturndemo.readwidget.computer

import android.graphics.Point

interface PointComputer {

    fun getPageTurnedPoint(width: Int, height: Int, touchPoint: Point, isAnim: Boolean): Point

    fun getBeforePageTurnedPoint(
        width: Int,
        height: Int,
        touchPoint: Point?,
        isAnim: Boolean
    ): Point
}