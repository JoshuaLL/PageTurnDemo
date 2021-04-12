package com.joshua.pageturndemo.readwidget

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.animation.AccelerateInterpolator
import android.widget.Scroller
import com.joshua.pageturndemo.readwidget.computer.DefaultPointComputer
import com.joshua.pageturndemo.readwidget.computer.PointComputer

class PageTurnView : View {

    private var a: Point? = null

    private var mLastA: Point? = null

    private var d: Point? = null
    private var i: Point? = null

    private var mPaint: Paint? = null

    private var mPageTurnAdapter: BasePageAdapter? = null

    private var mCurrentPosition = 0

    private var mTurnToPagePosition = 0

    private var mPointComputer: PointComputer? = null

    private var mScroller: Scroller? = null

    private var mIsStartAnim = false

    private var mCurrentPageAction = 0

    private var mCurrentTurnAction = 0

    private var mLastTouchPoint: Point? = null

    constructor(context: Context) : super(context) {
        init(context, null)
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init(context, null)
    }

    private fun init(context: Context, attrs: AttributeSet?) {
        mScroller = Scroller(context, AccelerateInterpolator())
        mPaint = Paint()
        mPaint!!.color = Color.BLACK
        mPaint!!.textSize = 50f

//        mTextPaint = new TextPaint();
//        mTextPaint.setAntiAlias(true);
//        mTextPaint.setTextSize(70);
        mPointComputer = DefaultPointComputer()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        if (mPageTurnAdapter != null) {
            mPageTurnAdapter!!.onPageLayoutChanged(measuredWidth, measuredHeight)
        }
    }

    override fun onDraw(canvas: Canvas) {
        if (mPageTurnAdapter == null || mPageTurnAdapter?.pageCount == 0) {
            canvas.drawColor(-0x1000000)
        } else {
            canvas.save()
            canvas.drawColor(-0x10000)
            mPageTurnAdapter!!.onDraw(mCurrentPosition, canvas)
            canvas.restore()

            if (hasNextPage()) {
                if (a != null) {
                    var f: Point? = null
                    //                    a = new Point(0 , getHeight() - getWidth());
                    if (mPointComputer == null) {
                        f = Point(width, height)
                    } else {
                        f = mPointComputer!!.getBeforePageTurnedPoint(
                            width,
                            height,
                            a,
                            mIsStartAnim
                        )
                        a = mPointComputer!!.getPageTurnedPoint(width, height, a!!, mIsStartAnim)
                    }
                    val g = Point((f.x + a!!.x) / 2, (f.y + a!!.y) / 2)
                    val e = Point((g.x - (f.y - g.y) xor 2 / (f.x - g.x)), f.y)
                    val h = Point(f.x, (g.y - (f.x - g.x) * (f.x - g.x) / (f.y - g.y)))
                    val c = Point(e.x - (f.x - e.x) / 2, f.y)
                    val j = Point(f.x, h.y - (f.y - h.y) / 2)
                    val b = CommonUtil.getIntersectionPoint(c, j, e, a)
                    val k = CommonUtil.getIntersectionPoint(c, j, a, h)
                    d = Point(((c.x + b!!.x) / 2 + e.x) / 2, ((c.y + b.y) / 2 + e.y) / 2)
                    i = Point(((k!!.x + j.x) / 2 + h.x) / 2, ((k.y + j.y) / 2 + h.y) / 2)
                    val path1 = Path()
                    path1.moveTo(j.x.toFloat(), j.y.toFloat())
                    path1.quadTo(h.x.toFloat(), h.y.toFloat(), k.x.toFloat(), k.y.toFloat())
                    path1.lineTo(a!!.x.toFloat(), a!!.y.toFloat())
                    path1.lineTo(b.x.toFloat(), b.y.toFloat())
                    path1.quadTo(e.x.toFloat(), e.y.toFloat(), c.x.toFloat(), c.y.toFloat())
                    path1.lineTo(f.x.toFloat(), f.y.toFloat())
                    path1.lineTo(j.x.toFloat(), j.y.toFloat())
                    path1.close()
                    val path2 = Path()
                    path2.moveTo(d!!.x.toFloat(), d!!.y.toFloat())
                    path2.lineTo(a!!.x.toFloat(), a!!.y.toFloat())
                    path2.lineTo(i!!.x.toFloat(), i!!.y.toFloat())
                    path2.lineTo(d!!.x.toFloat(), d!!.y.toFloat())
                    path2.close()
                    canvas.clipPath(path1)

                    canvas.save()
                    canvas.drawColor(-0xff0100)
                    mPageTurnAdapter!!.onDraw(mCurrentPosition + 1, canvas)
                    canvas.restore()
                    canvas.clipPath(path2)
                    canvas.drawColor(-0xffff01)

                }
            }
        }
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        if (!hasNextPage()) {
            return false
        }
        a = Point(event.x.toInt(), event.y.toInt())
        val currentTouchPoint = Point(
            event.x.toInt(), event.y.toInt()
        )
        when (event.action) {
            MotionEvent.ACTION_DOWN -> if (currentTouchPoint.x < width / 2) {
                if (currentTouchPoint.y < height / 2) {
                    changePageAction(ACTION_PREVIOUS_PAGE_TOP)
                } else {
                    changePageAction(ACTION_PREVIOUS_PAGE_BOTTOM)
                }
            } else {
                if (currentTouchPoint.y < height / 2) {
                    changePageAction(ACTION_NEXT_PAGE_TOP)
                } else {
                    changePageAction(ACTION_NEXT_PAGE_BOTTOM)
                }
            }
            MotionEvent.ACTION_MOVE -> {
                var turnAction = -1
                turnAction = if (currentTouchPoint.x < mLastTouchPoint!!.x) {
                    ACTION_TURN_START
                } else {
                    ACTION_TURN_RECOVER
                }
                if (turnAction != mCurrentTurnAction) {
                    changeTurnAction(turnAction)
                }
                invalidate()
            }
            MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                mLastA = a
                a = null
                startPageAnim()
                invalidate()
            }
        }
        mLastTouchPoint = currentTouchPoint
        return true
    }

    fun setPageTurnAdapter(adapter: BasePageAdapter?) {
        mPageTurnAdapter = adapter
        mCurrentPosition = 0
        invalidate()
        if (measuredWidth != 0 && measuredHeight != 0) {
            mPageTurnAdapter!!.pageLayoutChange(measuredWidth, measuredHeight)
        }
    }

    private fun hasNextPage(): Boolean {
        return !(mPageTurnAdapter == null || mPageTurnAdapter?.pageCount == 0 || mCurrentPosition == mPageTurnAdapter?.pageCount ?:0 - 1)
    }

    private fun hasLastPage(): Boolean {
        return !(mPageTurnAdapter == null || mPageTurnAdapter?.pageCount == 0 || mCurrentPosition == 0)
    }

    private fun startPageAnim() {
        endPageAnim()
        when (mCurrentPageAction) {
            ACTION_NEXT_PAGE_TOP -> {
            }
            ACTION_NEXT_PAGE_BOTTOM -> {
            }
            ACTION_PREVIOUS_PAGE_TOP -> {
            }
            ACTION_PREVIOUS_PAGE_BOTTOM -> {
            }
        }
        val startX = mLastA!!.x
        val startY = mLastA!!.y
        var dx = 0
        var dy = 0
        when (mCurrentTurnAction) {
            ACTION_TURN_RECOVER -> {
                dx = width - startX
                dy = height - startY
                mTurnToPagePosition = mCurrentPosition
            }
            ACTION_TURN_START -> {
                dx = -width - startX
                dy = height - startY
                mTurnToPagePosition = mCurrentPosition + 1
            }
        }
        mIsStartAnim = true
        mScroller!!.startScroll(startX, startY, dx, dy, TURN_PAGE_ANIM_DURATION)
    }

    private fun changePageAction(pageAction: Int) {
        mCurrentPageAction = pageAction
    }

    private fun changeTurnAction(turnAction: Int) {
        mCurrentTurnAction = turnAction
    }

    private fun endPageAnim() {
        if (mIsStartAnim) {
            mIsStartAnim = false
            //            mLastA = null;
            a = null
            mCurrentPosition = mTurnToPagePosition
            mCurrentPageAction = -1
            mCurrentTurnAction = -1
            invalidate()
        }
    }

    fun setPointComputer(pointComputer: PointComputer?) {
        mPointComputer = pointComputer
    }

    override fun computeScroll() {
        if (mLastA != null && mIsStartAnim) {
            if (mScroller!!.computeScrollOffset() && mScroller!!.currX != width && mScroller!!.currY != height) {
                val currX = mScroller!!.currX
                val currY = mScroller!!.currY
                mLastA!!.x = currX
                mLastA!!.y = currY
                a = mLastA
                invalidate()
            } else {
                // 动画执行完毕
                endPageAnim()
            }
        } else {
            // 并没有动画开始
        }
    }

    companion object {

        private const val TURN_PAGE_ANIM_DURATION = 500

        private const val ACTION_NEXT_PAGE_TOP = 1

        private const val ACTION_NEXT_PAGE_BOTTOM = 2

        private const val ACTION_PREVIOUS_PAGE_TOP = 3

        private const val ACTION_PREVIOUS_PAGE_BOTTOM = 4

        private const val ACTION_TURN_START = 1

        private const val ACTION_TURN_RECOVER = 2
    }
}