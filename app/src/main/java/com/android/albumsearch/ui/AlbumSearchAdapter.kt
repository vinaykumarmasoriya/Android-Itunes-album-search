package com.android.albumsearch.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.android.albumsearch.R
import com.android.albumsearch.model.Album
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.album_item.view.*
import org.jetbrains.anko.sdk27.coroutines.onClick
import java.time.ZoneId
import java.time.format.DateTimeFormatter

class AlbumSearchAdapter(var data: List<Album>, private val onClick: (Album) -> Unit) :
    RecyclerView.Adapter<AlbumSearchAdapter.AlbumHolder>() {


    override fun getItemCount(): Int = data.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlbumHolder {
        val inflated =
            LayoutInflater.from(parent.context).inflate(R.layout.album_item, parent, false)
        return AlbumHolder(inflated)
    }

    override fun onBindViewHolder(holder: AlbumHolder, position: Int) {
        holder.bind(data[position])
        holder.view.onClick { onClick(data[position]) }
    }

    class AlbumHolder(v: View) : RecyclerView.ViewHolder(v) {

        var view: View = v
        private var album: Album? = null

        fun bind(album: Album) {
            this.album = album

            loadImage(view.albumArtwork, album.artworkUrl)
            view.artist_name_textview.text = album.artistName
            view.track_name_textview.text = album.trackName
            view.collection_name_textview.text = album.collectionName
            view.collection_price_textview.text = album.collectionPrice.toString()
            view.release_date_textview.text = album.releaseDate
                .toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate().format(DateTimeFormatter.ISO_DATE)
        }

        private fun loadImage(imageView: ImageView, url: String) {
            val circularProgress = CircularProgressDrawable(view.context)
            circularProgress.strokeWidth = 5f
            circularProgress.centerRadius = 30f
            circularProgress.start()

            Glide.with(view.context)
                .asBitmap()
                .placeholder(circularProgress)
                .error(R.drawable.ic_launcher_background)
                .load(url)
                .override(100, 100) // Can be 2000, 2000
                .into(imageView)
                .waitForLayout()
        }
    }
}