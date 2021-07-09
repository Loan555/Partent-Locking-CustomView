package com.loan555.customview_canvas_animation.views

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.Log
import android.view.View

const val myTag = "aaa"

class EmotionalFaceView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {
    // Paint object for coloring and styling
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)

    // Some colors for the face background, eyes and mouth.
    private var faceColor = Color.YELLOW
    private var eyesColor = Color.BLACK
    private var mouthColor = Color.WHITE
    private var borderColor = Color.BLACK

    // Face border width in pixels
    private var borderWidth = 4.0f

    // View size in pixels
    var size = 320
    private val mouthPath = Path()

    override fun draw(canvas: Canvas?) {
        Log.d(myTag,"draw")
        super.draw(canvas)

        drawFaceBackground(canvas)
        drawEyes(canvas)
        drawMouth(canvas)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        Log.d(myTag,"onMeasure")
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        setMeasuredDimension(size, size)
//        setMeasuredDimension(size,size)
    }

    private fun drawFaceBackground(canvas: Canvas?) {
        // 1
        paint.color = faceColor
        paint.style = Paint.Style.FILL
        // 2
        val radius = size / 2f

        // 3
        canvas?.drawCircle(size / 2f, size / 2f, radius, paint)

        // 4
        paint.color = borderColor
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = borderWidth

        // 5
        canvas?.drawCircle(size / 2f, size / 2f, radius - borderWidth / 2f, paint)
    }

    private fun drawEyes(canvas: Canvas?) {
// 1
        paint.color = eyesColor
        paint.style = Paint.Style.FILL

// 2
        val leftEyeRect = RectF(size * 0.32f, size * 0.23f, size * 0.43f, size * 0.50f)

        canvas?.drawOval(leftEyeRect, paint)

// 3
        val rightEyeRect = RectF(size * 0.57f, size * 0.23f, size * 0.68f, size * 0.50f)

        canvas?.drawOval(rightEyeRect, paint)
    }

    private fun drawMouth(canvas: Canvas?) {
// 1 move to (x0,y0)
        mouthPath.moveTo(size * 0.22f, size * 0.7f)
// 2 vẽ một đường cong bắt đầu từ điểm (x0, y0) đi qua các điểm (x1, y1) và (x2, y2)
        mouthPath.quadTo(size * 0.50f, size * 0.70f, size * 0.78f, size * 0.70f)
// 3 vẽ 1 đường cong từ điểm (x2, y2) tới điẻm (x0, y0) đi qua điểm (x3, y3)
        mouthPath.quadTo(size * 0.50f, size * 0.90f, size * 0.22f, size * 0.70f)
// 4
        paint.color = mouthColor
        paint.style = Paint.Style.FILL
// 5
        canvas?.drawPath(mouthPath, paint)
    }
}