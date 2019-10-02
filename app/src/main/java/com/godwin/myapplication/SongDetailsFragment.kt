package com.godwin.myapplication

import android.annotation.TargetApi
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewAnimationUtils
import android.view.ViewGroup
import android.widget.SeekBar
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.godwin.myapplication.model.Song
import com.godwin.myapplication.utils.ConvertionHelper
import kotlinx.android.synthetic.main.song_details_fragment.*
import kotlin.math.max


class SongDetailsFragment : Fragment(), SeekBar.OnSeekBarChangeListener {
    var song: Song? = null
    override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
        clDetails.setProgress(progress)
    }

    override fun onStartTrackingTouch(seekBar: SeekBar?) {

    }

    override fun onStopTrackingTouch(seekBar: SeekBar?) {

    }

    companion object {
        fun newInstance() = SongDetailsFragment()
    }

    private lateinit var viewModel: SongDetailsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
//        sharedElementEnterTransition = TransitionInflater.from(context).inflateTransition(android.R.transition.move)
        song = arguments?.getParcelable("song")

        val view = inflater.inflate(R.layout.song_details_fragment, container, false)
        view?.let {
            it.addOnLayoutChangeListener(object : View.OnLayoutChangeListener {

                @TargetApi(Build.VERSION_CODES.LOLLIPOP)
                override fun onLayoutChange(
                    v: View,
                    left: Int,
                    top: Int,
                    right: Int,
                    bottom: Int,
                    oldLeft: Int,
                    oldTop: Int,
                    oldRight: Int,
                    oldBottom: Int
                ) {
                    v.removeOnLayoutChangeListener(this)

                    val width = it.width
                    val height = it.height

                    val cx = width / 2
                    val cy = height - ConvertionHelper.convertDpToPixel(120.0F, activity!!)

                    val finalRadius = (max(width, height) / 2 + max(
                        (width - cx).toFloat(), (height - cy)
                    ))
                    val anim =
                        ViewAnimationUtils.createCircularReveal(v, cx, cy.toInt(), 0f, finalRadius)
                    anim.duration = 500
                    anim.start()
                }
            })
        }
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sbDuration.setOnSeekBarChangeListener(this)
        setSongDetails()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(SongDetailsViewModel::class.java)
        // TODO: Use the ViewModel
    }

    private fun setSongDetails() {
        song?.let {

            Glide.with(this).load(it.resId).into(ivThumbNail)
            Glide.with(this).load(R.drawable.sample9).into(ivStatic)
            Glide.with(this)
                .load(if (it.fav) R.drawable.ic_star_black_24dp else R.drawable.ic_star_border_black_24dp)
                .into(ivFav)

            sbDuration.progress = it.progress
            clDetails.setProgress(it.progress)
            clDetails.setProgressEnabled(true)
        }
    }
}
