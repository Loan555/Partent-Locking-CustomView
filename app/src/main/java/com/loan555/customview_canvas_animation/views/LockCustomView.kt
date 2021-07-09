package com.loan555.customview_canvas_animation.views

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import androidx.core.content.ContextCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.loan555.customview_canvas_animation.R

const val PARTITION_RATIO = 1 / 3f

class LockCustomView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {
    private var myPassword = ""
    private val _password = MutableLiveData<String>().apply {
        value = myPassword
    }
    val password: LiveData<String> = _password

    private val countRow = 3
    private val countColumn = 3
    private var rectIndex = Pair(-1, -1)
    private var touching = false
    private var startPoint: Point? = null
    private var endPoint: Point? = null

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
        strokeWidth = 12f
        color = Color.DKGRAY
    }
    private val highLightPaint = Paint().apply {
        color = ContextCompat.getColor(context!!, R.color.hilight)
        style = Paint.Style.FILL
        isAntiAlias = true
    }
    private lateinit var squares: Array<Array<Rect>>
    private lateinit var squaresData: Array<Array<String>>
    private val listPoint: ArrayList<Point> = ArrayList()

    override fun onDraw(canvas: Canvas?) {
        Log.d("aaa", "onDraw")
        super.onDraw(canvas)
        initializeTicTacToeSquares()
        if (touching) {
            drawHighlightDot(canvas)
        }
        squaresData.forEachIndexed { i, strings ->
            strings.forEachIndexed { j, s ->
                drawDot(canvas, squares[i][j])
                if (s != "")
                    drawTextInsideRectangle(canvas, squares[i][j], s)
            }
        }
        if (startPoint != null && endPoint != null)
            drawLine(canvas, startPoint!!, endPoint!!)
        drawAllLine(canvas)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        Log.d("aaa", "onMeasure")
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        squaresData = Array(countRow) { Array(countColumn) { "" } }
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        val x = event?.x
        val y = event?.y
        return when (event?.action) {
            MotionEvent.ACTION_DOWN -> {
                if (x != null && y != null) {
                    listPoint.clear()
                    myPassword = ""
                    rectIndex = getRectIndexesFor(x, y)
                    if (rectIndex.first != -1 && rectIndex.second != -1) {
                        touching = true
                        addKey(rectIndex.first, rectIndex.second)
                        postInvalidate()
                    }
                }
                true
            }
            MotionEvent.ACTION_MOVE -> {
                if (x != null && y != null) {
                    endPoint = Point(x.toInt(), y.toInt())
                    rectIndex = getRectIndexesFor(x, y)
                    if (rectIndex.first != -1 && rectIndex.second != -1) {
                        touching = true
                        addKey(rectIndex.first, rectIndex.second)
                    }
                    postInvalidate()
                }
                true
            }
            MotionEvent.ACTION_UP -> {
                touching = false
                if (myPassword.length < 4) {
                    squaresData = Array(countRow) { Array(countColumn) { "" } }
                    myPassword = ""
                    Log.e("aaa", "mat khau phai >= 4 ky tu")
                }
                listPoint.clear()
                _password.value = myPassword
                startPoint = null
                endPoint = null
                postInvalidate()
                true
            }
            MotionEvent.ACTION_CANCEL -> {
                true
            }
            else -> super.onTouchEvent(event)
        }
    }

    private fun drawAllLine(canvas: Canvas?) {
        if (listPoint.size >= 2) {
            for (i in 0..listPoint.size - 2) {
                drawLine(canvas, listPoint[i], listPoint[i + 1])
            }
        }
    }

    private fun addKey(i: Int, j: Int) {
        val newKey = (i * 3 + j + 1).toString()
        var count = false
        myPassword.forEach {
            if (it.toString() == newKey) {
                count = true
                return@forEach
            }
        }
        if (!count) {
            val rect = squares[rectIndex.first][rectIndex.second]
            val point = Point(rect.centerX(), rect.centerY())
            listPoint.add(point)
            startPoint = point
            squaresData[i][j] = newKey
            myPassword += squaresData[i][j]

            Log.d("aaa", "pass = $myPassword")
        }
    }

    private fun drawHighlightDot(canvas: Canvas?) {
        if (rectIndex.first != -1 && rectIndex.second != -1) {
            val rect = squares[rectIndex.first][rectIndex.second]
            canvas?.drawCircle(
                rect.exactCenterX(),
                rect.exactCenterY(),
                (rect.right - rect.left).toFloat(),
                highLightPaint
            )
        }
    }

    private fun getRectIndexesFor(x: Float, y: Float): Pair<Int, Int> {
        squares.forEachIndexed { i, arrayOfRects ->
            arrayOfRects.forEachIndexed { j, rect ->
                if (rect.contains(x.toInt(), y.toInt()))
                    return Pair(i, j)
            }
        }
        return Pair(-1, -1)// x, y do not lie in our view
    }

    private fun drawDot(canvas: Canvas?, rect: Rect) {
        val radio = (rect.right - rect.left) / 4f
        canvas?.drawCircle(
            rect.left.toFloat() + (radio * 2),
            rect.top.toFloat() + (2 * radio),
            radio,
            paint
        )
    }

    private fun drawLine(canvas: Canvas?, startPoint: Point, endPoint: Point) {
        canvas?.drawLine(
            startPoint.x.toFloat(),
            startPoint.y.toFloat(),
            endPoint.x.toFloat(),
            endPoint.y.toFloat(),
            paint
        )
    }

    private fun initializeTicTacToeSquares() {
        squares = Array(countRow) { Array(countColumn) { Rect() } }
        val xUnit = (width * PARTITION_RATIO).toInt() // one unit on x-axis
        val yUnit = (height * PARTITION_RATIO).toInt() // one unit on y-axis
        for (j in 0 until countRow) {
            for (i in 0 until countColumn) {
                val cx = (xUnit * (i + 0.5f)).toInt()
                val cy = (yUnit * (j + 0.5f)).toInt()
                val radio = (width * PARTITION_RATIO * 0.1).toInt()
                squares[j][i] =
                    Rect(
                        cx - radio, cy - radio, cx + radio, cy + radio
                    )
            }
        }
    }

    private fun drawTextInsideRectangle(canvas: Canvas?, rect: Rect, str: String) {
        val xOffset = paint.measureText(str) * 0.5f
        val yOffset = paint.fontMetrics.ascent * -0.4f
        val textX = (rect.exactCenterX()) - xOffset
        val textY = (rect.exactCenterY()) + yOffset
        canvas?.drawText(str, textX, textY, paint)
    }
}