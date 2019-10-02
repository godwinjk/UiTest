package com.godwin.myapplication.customview

import android.content.Context
import android.view.MotionEvent
import androidx.appcompat.widget.AppCompatSeekBar

/**
 * Created by Godwin on 10/2/2019 2:25 PM.
 *
 * @author : Godwin Joseph Kurinjikattu
 * @since : 2019
 */
class CustomSeekbar(context: Context?) : AppCompatSeekBar(context) {
    var touchEnabled: Boolean = false

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        return touchEnabled
    }

    override fun performClick(): Boolean {
        return touchEnabled
    }
}