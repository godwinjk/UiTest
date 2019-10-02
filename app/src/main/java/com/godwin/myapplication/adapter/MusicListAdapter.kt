package com.godwin.myapplication.adapter

import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.godwin.myapplication.R
import com.godwin.myapplication.model.Song
import kotlinx.android.synthetic.main.layout_music_list.view.*
import kotlinx.android.synthetic.main.layout_music_list.view.ivThumbNail
import kotlinx.android.synthetic.main.layout_music_list.view.sbDuration
import kotlinx.android.synthetic.main.song_details_fragment.view.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

/**
 * Created by Godwin on 9/24/2019 10:03 PM.
 *
 * @author : Godwin Joseph Kurinjikattu
 * @since : 2019
 */

class MusicListAdapter : RecyclerView.Adapter<MusicListAdapter.MusicItemHolder>(), CoroutineScope {
    private var callback: MusicListAdapter.OnItemClickListener? = null
    private var previousItem: Song? = null

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main

    private var songs: ArrayList<Song> = arrayListOf()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MusicItemHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.layout_music_list, parent, false)
        return MusicItemHolder(view)
    }

    override fun getItemCount(): Int {
        return songs.size
    }

    override fun onBindViewHolder(holder: MusicItemHolder, position: Int) {
        val song = songs[position]
        song.position = position

        holder.tvSongName.text = song.name
        holder.tvArtist.text = song.artist

//        launch {
//            fetchImage(holder, song)
//        }

        Glide.with(holder.itemView.context)
            .load(song.resId)
            .into(holder.ivThumbnail)
        Glide.with(holder.itemView.context)
            .load(if (song.fav) R.drawable.ic_star_black_24dp else R.drawable.ic_star_border_black_24dp)
            .into(holder.ivFav)
        updateBackground(holder, song)

        ViewCompat.setTransitionName(holder.ivThumbnail, "thumbNail")
        ViewCompat.setTransitionName(holder.ivFav, "fav")

        setTextColor(song, holder)
        updateSeekBarProgress(holder, song)

        addBackGroundClickListener(holder, song)
    }

    private fun updateBackground(
        holder: MusicItemHolder,
        song: Song
    ) {
        val isUp:Boolean = previousItem?.let { it.position>song.position } ?: false
        holder.clBackground.setProgressEnabled(song.progressEnabled, true,isUp)
        holder.clBackground.setProgress(song.progress)
    }

    private fun updateSeekBarProgress(
        holder: MusicItemHolder,
        song: Song
    ) {
        holder.sbDuration.visibility = if (song.progressEnabled) View.VISIBLE else View.INVISIBLE
        holder.sbDuration.progress = song.progress
    }

    private fun setTextColor(
        song: Song,
        holder: MusicItemHolder
    ) {
        if (song.progressEnabled) {
            holder.tvArtist.let {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    it.setTextColor(it.context.resources.getColor(R.color.blue, it.context.theme))
                } else {
                    it.setTextColor(it.context.resources.getColor(R.color.blue))
                }
            }
            holder.tvSongName.let {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    it.setTextColor(it.context.resources.getColor(R.color.blue, it.context.theme))
                } else {
                    it.setTextColor(it.context.resources.getColor(R.color.blue))
                }
            }
        } else {
            holder.tvArtist.let {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    it.setTextColor(it.context.resources.getColor(R.color.black, it.context.theme))
                } else {
                    it.setTextColor(it.context.resources.getColor(R.color.black))
                }
            }
            holder.tvSongName.let {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    it.setTextColor(it.context.resources.getColor(R.color.black, it.context.theme))
                } else {
                    it.setTextColor(it.context.resources.getColor(R.color.black))
                }
            }
        }
    }

    private fun addBackGroundClickListener(holder: MusicItemHolder, song: Song) {
        holder.clBackground.setOnClickListener { view ->
            previousItem.let {
                if (previousItem != song) {
                    previousItem?.progressEnabled = false
                    previousItem?.position?.let { it1 -> notifyItemChanged(it1) }
                }
            }
            previousItem = song
            song.progressEnabled = true
            notifyItemChanged(holder.adapterPosition, song)
            callback?.onClicked(song, holder.adapterPosition)
        }
    }

    private suspend fun fetchImage(holder: MusicItemHolder, song: Song) {
        withContext(Dispatchers.Default) {
            holder.ivThumbnail.setImageResource(song.resId)
        }
    }

    fun clearSongList() {
        this.songs.clear()
    }

    fun addSongs(songs: ArrayList<Song>) {
        this.songs.addAll(songs)
        notifyDataSetChanged()
    }

    fun setSongs(songs: ArrayList<Song>) {
        this.songs = songs
        notifyDataSetChanged()
    }

    fun setItemClickListener(listener: OnItemClickListener) {
        this.callback = listener
    }

    class MusicItemHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val tvSongName = itemView.tvMusicName
        val tvArtist = itemView.tvArtist
        val ivThumbnail = itemView.ivThumbNail
        val sbDuration = itemView.sbDuration
        val clBackground = itemView.clMusicItem
        val ivFav = itemView.ivFavourite

//        init {
//            sbDuration.touchEnabled = false
//        }
    }

    interface OnItemClickListener {
        fun onClicked(item: Song, position: Int)
        fun onSeekTo(item: Song, progress: Int, position: Int)
    }
}