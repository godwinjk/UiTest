package com.godwin.myapplication.utils

import android.content.Context
import android.util.DisplayMetrics


/**
 * Created by Godwin on 9/24/2019 9:43 PM.
 *
 * @author : Godwin Joseph Kurinjikattu
 * @since : 2019
 */
class ConvertionHelper {
    companion object {
        fun convertDpToPixel(dp: Float, context: Context): Float {
            return dp * (context.resources.displayMetrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT)
        }

        fun convertPixelsToDp(px: Float, context: Context): Float {
            return px / (context.resources.displayMetrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT)
        }
    }
}