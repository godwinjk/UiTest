package com.godwin.myapplication.customview

import android.animation.ValueAnimator
import android.content.Context
import android.content.res.TypedArray
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import androidx.constraintlayout.widget.ConstraintLayout
import com.godwin.myapplication.R
import com.godwin.myapplication.utils.L

/**
 * Created by Godwin on 9/24/2019 7:49 AM.
 *
 * @author : Godwin Joseph Kurinjikattu
 * @since : 2019
 */

class CustomConstraintLayout : ConstraintLayout {

    private var progressColor: Int = Color.WHITE
    private var max: Int = 100
    private var progress: Int = 50
    private var startOffset: Int = 0
    private var endOffset: Int = 0
    private var progressEnabled: Boolean = false

    private var animatedFraction: Int = 0
    private var animationUp: Boolean = false

    var paint: Paint = Paint()

    var dirtyRect: RectF = RectF(0F, 0F, 0F, 0F)

    constructor(context: Context) : super(context) {
        initialize(context)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        initialize(context, attrs)
    }

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(
        context,
        attrs,
        defStyle
    ) {
        initialize(context, attrs, defStyle)
    }

    private fun initialize(context: Context, attr: AttributeSet? = null, defStyle: Int = 0) {
        val typedArray: TypedArray = context.theme.obtainStyledAttributes(
            attr,
            R.styleable.CustomConstraintLayout,
            0,
            defStyle
        )
        try {
            progressColor =
                typedArray.getColor(R.styleable.CustomConstraintLayout_progressColor, Color.LTGRAY)
            max = typedArray.getInt(R.styleable.CustomConstraintLayout_max, 0)
            progress = typedArray.getInt(R.styleable.CustomConstraintLayout_progress, 0)
            startOffset =
                typedArray.getDimensionPixelSize(R.styleable.CustomConstraintLayout_startOffset, 0)
            endOffset =
                typedArray.getDimensionPixelSize(R.styleable.CustomConstraintLayout_endOffset, 0)

            paint.color = progressColor
            paint.isAntiAlias = true
        } catch (e: Exception) {
            L.e(javaClass.simpleName, e.localizedMessage)
        } finally {
            typedArray.recycle()
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        dirtyRect.set(0F, 0F, 0F, height.toFloat())
    }

    override fun dispatchDraw(canvas: Canvas?) {
        if (progressEnabled) {
            val drawingWidth: Float =
                (((width - startOffset - endOffset) * progress) / max).toFloat()
            dirtyRect.right = drawingWidth + startOffset

            dirtyRect.bottom =
                if (animatedFraction.toFloat() <= 0) height.toFloat() else animatedFraction.toFloat()
            canvas?.drawRect(dirtyRect, paint)
        }
        super.dispatchDraw(canvas)
    }

    fun setProgress(value: Int) {
        this.progress = value
        invalidate()
    }

    fun setStatOffset(offset: Int) {
        this.startOffset = offset
        invalidate()
    }

    fun setProgressEnabled(enabled: Boolean, animated: Boolean = false, isUp: Boolean = false) {
        if (enabled == this.progressEnabled) return

        this.progressEnabled = enabled

        if (enabled && animated) {
            animationUp = false
            val animator = ValueAnimator.ofInt(0, height)
            animator.duration = 300
            animator.addUpdateListener { animation ->
                animatedFraction = animation.animatedValue as Int
//                animatedFraction = if (animationUp) animatedFraction else height -animatedFraction
                invalidate()
            }
            animator.start()
        } else {
            animatedFraction = height
        }
        invalidate()
    }
}