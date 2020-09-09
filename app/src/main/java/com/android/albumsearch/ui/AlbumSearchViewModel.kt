package com.android.albumsearch.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.albumsearch.model.Album
import com.android.albumsearch.model.Outcome
import com.android.albumsearch.repository.AlbumRepository

class AlbumSearchViewModel(private val repository: AlbumRepository) : ViewModel()  {

    private val albums: MutableLiveData<List<Album>> by lazy {
        MutableLiveData<List<Album>>().also {
            it.value = emptyList()
        }
    }

    private val empty: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>().also {
            it.value = true
        }
    }

    private val loading: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>().also {
            it.value = false
        }
    }

    private val requestErrorMessage: MutableLiveData<String?> by lazy {
        MutableLiveData<String?>().also {
            it.value = null
        }
    }

    private val selectedAlbum: MutableLiveData<Album?> by lazy {
        MutableLiveData<Album?>().also {
            it.value = null
        }
    }

    fun queryAlbums(query: String) {
        loading.value = true
        empty.value = false
        repository.getAlbums(query) { outcome ->
            loading.value = false
            when (outcome) {
                is Outcome.Success -> {
                    albums.value = outcome.value.results
                    empty.value = outcome.value.results.isEmpty()
                    requestErrorMessage.value = null
                }
                is Outcome.Error -> {
                    albums.value = emptyList()
                    empty.value = true
                    requestErrorMessage.value = outcome.message
                }
            }
        }
    }

    fun didSelectAlbum(album: Album) {
        selectedAlbum.value = album
    }

    fun didUnselectAlbum() {
        selectedAlbum.value = null
    }

    fun didShowError() {
        requestErrorMessage.value = null
    }

    fun getAlbums() = albums as LiveData<List<Album>>
    fun isEmpty() = empty as LiveData<Boolean>
    fun isLoading() = loading as LiveData<Boolean>
    fun getRequestErrorMessage() = requestErrorMessage as LiveData<String?>
    fun selectedAlbum() = selectedAlbum as LiveData<Album?>
}