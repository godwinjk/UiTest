package com.godwin.myapplication

import android.os.Bundle
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.RelativeSizeSpan
import android.text.style.StyleSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import android.widget.TextView
import androidx.core.text.toSpannable
import androidx.core.view.ViewCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.NavHostFragment.findNavController
import androidx.navigation.navOptions
import androidx.recyclerview.widget.LinearLayoutManager
import com.godwin.myapplication.adapter.MusicListAdapter
import com.godwin.myapplication.model.Song
import com.godwin.myapplication.presenter.SongPresenter
import com.godwin.myapplication.presenter.SongsInteractor
import kotlinx.android.synthetic.main.layout_music_list.sbDuration
import kotlinx.android.synthetic.main.layout_music_list.tvMusicName
import kotlinx.android.synthetic.main.song_list_fragment.*


class SongListFragment : Fragment(), SeekBar.OnSeekBarChangeListener, SongPresenter.View,
    MusicListAdapter.OnItemClickListener {
    override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
        song?.progress = progress
        song?.let { songAdapter.notifyItemChanged(it.position, it) }
    }

    override fun onStartTrackingTouch(seekBar: SeekBar?) {
    }

    override fun onStopTrackingTouch(seekBar: SeekBar?) {
    }

    private var song: Song? = null

    override fun onClicked(item: Song, position: Int) {
        song = item
        setDetailsToBottomView(item)
    }

    override fun onSeekTo(item: Song, progress: Int, position: Int) {
        sbDuration.progress = item.progress
    }

    companion object {
        fun newInstance() = SongListFragment()
    }

    lateinit var songAdapter: MusicListAdapter
    val songInteracter: SongPresenter = SongsInteractor()
    private lateinit var viewModel: SongListViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.song_list_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(SongListViewModel::class.java)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        songAdapter = MusicListAdapter()
        songAdapter.setItemClickListener(this)
        rvSongList.layoutManager = LinearLayoutManager(activity)
        rvSongList.adapter = songAdapter
        sbDuration.setOnSeekBarChangeListener(this)

        clBottomView.isVisible = false

        songInteracter.getSongs(0, 10, this)

        bottomClickListener()

    }

    override fun onGetSongs(songs: ArrayList<Song>) {
        super.onGetSongs(songs)

        songAdapter.setSongs(songs)
    }

    private fun bottomClickListener() {
        ivUpArrow.setOnClickListener { view ->
            val options = navOptions {
                anim {
                    enter = R.anim.slide_in_up
                    exit = R.anim.slide_out_down
                    popEnter = R.anim.slide_in_up
                    popExit = R.anim.slide_out_down
                }

            }

            ViewCompat.setTransitionName(clBottomView,"cl")
            ViewCompat.setTransitionName(sbDuration,"sb")
            ViewCompat.setTransitionName(ivPlayPause,"playPause")

            val extras = FragmentNavigatorExtras(
                ivPlayPause to "playPause",
                clBottomView to "cl",
                sbDuration to "sb"
            )
            val bundle = Bundle()
            bundle.putParcelable("song", song)
            findNavController(this).navigate(R.id.songDetails, bundle, null, extras)
        }
    }


    private fun setDetailsToBottomView(song: Song) {
        clBottomView.isVisible = true
        sbDuration.progress = song.progress

        val builder = SpannableStringBuilder()
        builder
            .append(
                song.name,
                StyleSpan(android.graphics.Typeface.BOLD),
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
            )
            .append("  ${song.artist}",RelativeSizeSpan(.8f),Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)

        tvSongName.setText(builder.toSpannable(), TextView.BufferType.SPANNABLE)
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }
}
