package com.godwin.myapplication

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.godwin.myapplication.model.Song

class SongListViewModel : ViewModel() {
    val song: MutableLiveData<Song> by lazy {
        MutableLiveData<Song>()
    }
}
