package com.godwin.myapplication.model

import android.os.Parcelable
import androidx.annotation.DrawableRes
import kotlinx.android.parcel.Parcelize

/**
 * Created by Godwin on 9/25/2019 7:44 AM.
 *
 * @author : Godwin Joseph Kurinjikattu
 * @since : 2019
 */
@Parcelize
data class Song(
    var name: String,
    var id: Long,
    var genre: String,
    var artist: String, @DrawableRes var resId: Int,
    var progress: Int = 0,
    var progressEnabled: Boolean = false,
    var position: Int = 0,
    var fav: Boolean = false
) : Parcelable