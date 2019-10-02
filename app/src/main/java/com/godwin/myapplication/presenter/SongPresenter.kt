package com.godwin.myapplication.presenter

import com.godwin.myapplication.model.Song

/**
 * Created by Godwin on 9/27/2019 8:08 AM.
 *
 * @author : Godwin Joseph Kurinjikattu
 * @since : 2019
 */

interface SongPresenter {
    fun getSongs(startIndex: Int, offset: Int, callback: View)
    fun getSong(id: Long, callback: View): Song?
    fun getSongDetails(id: Long,callback: View): Song?
    interface View {
        fun onGetSongs(songs: ArrayList<Song>) {}
        fun onGetSong(song: Song) {}
    }
}