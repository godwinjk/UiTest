package com.godwin.myapplication.presenter

import com.godwin.myapplication.R
import com.godwin.myapplication.model.Song
import kotlin.random.Random

/**
 * Created by Godwin on 9/27/2019 8:07 AM.
 *
 * @author : Godwin Joseph Kurinjikattu
 * @since : 2019
 */

class SongsInteractor : SongPresenter {
    override fun getSongs(startIndex: Int, offset: Int, callback: SongPresenter.View) {
        //TODO handle Async fetch
        //TODO convert to main thread
        callback.onGetSongs(getSongs())
    }

    override fun getSong(id: Long, callback: SongPresenter.View): Song? {
        return getSongs().find { song -> song.id == id }
    }

    override fun getSongDetails(id: Long, callback: SongPresenter.View): Song? {
        return getSongs().find { song -> song.id == id }
    }

    private fun getSongs(): ArrayList<Song> {
        val random = Random(20)
        val songList: ArrayList<Song> = arrayListOf()

        songList.add(
            Song(
                "Godwin",
                random.nextLong(),
                "Entertainment",
                "Snow Patrol",
                R.drawable.sample1,
                fav = false
            )
        )
        songList.add(
            Song(
                "Godwin",
                random.nextLong(),
                "Entertainment",
                "Snow Patrol",
                R.drawable.sample2,
                fav = true
            )
        )
        songList.add(
            Song(
                "Godwin",
                random.nextLong(),
                "Entertainment",
                "Snow Patrol",
                R.drawable.sample3,
                fav = false
            )
        )
        songList.add(
            Song(
                "Godwin",
                random.nextLong(),
                "Entertainment",
                "Snow Patrol",
                R.drawable.sample4,
                fav = false
            )
        )
        songList.add(
            Song(
                "Godwin",
                random.nextLong(),
                "Entertainment",
                "Snow Patrol",
                R.drawable.sample5,
                fav = true
            )
        )
        songList.add(
            Song(
                "Godwin",
                random.nextLong(),
                "Entertainment",
                "Snow Patrol",
                R.drawable.sample6,
                fav = false
            )
        )
        songList.add(
            Song(
                "Godwin",
                random.nextLong(),
                "Entertainment",
                "Snow Patrol",
                R.drawable.sample7,
                fav = false
            )
        )
        songList.add(
            Song(
                "Godwin",
                random.nextLong(),
                "Entertainment",
                "Snow Patrol",
                R.drawable.sample8,
                fav = true
            )
        )
        songList.add(
            Song(
                "Godwin",
                random.nextLong(),
                "Entertainment",
                "Snow Patrol",
                R.drawable.sample1,
                fav = false
            )
        )
        songList.add(
            Song(
                "Godwin",
                random.nextLong(),
                "Entertainment",
                "Snow Patrol",
                R.drawable.sample2,
                fav = true
            )
        )

        return songList;
    }
}