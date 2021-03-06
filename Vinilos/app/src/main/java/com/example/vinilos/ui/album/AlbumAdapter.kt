package com.example.vinilos.ui.album

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.vinilos.R
import com.example.vinilos.databinding.AlbumItemBinding
import com.example.vinilos.models.Album
import com.squareup.picasso.Picasso

class AlbumsAdapter : RecyclerView.Adapter<AlbumsAdapter.AlbumViewHolder>(){

    var albums :List<Album> = emptyList()
        @SuppressLint("NotifyDataSetChanged")
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlbumViewHolder {
        val withDataBinding: AlbumItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            AlbumViewHolder.LAYOUT,
            parent,
            false)
        return AlbumViewHolder(withDataBinding)
    }

    override fun onBindViewHolder(holder: AlbumViewHolder, position: Int) {
        holder.viewDataBinding.also {
            it.album = albums[position]
            try{
                Picasso.get()
                    .load(albums[position].cover)
                    .error(R.mipmap.ic_launcher_round)
                    .into(holder.viewDataBinding.imageCover)
            }
            catch (e: Exception) { }
            val performer = holder.viewDataBinding.textView3
            var performersString = ""
            for (i in 0 until albums[position].performers.size) {
                if(i+1 > albums[position].performers.size){
                    performersString += (albums[position].performers[i].name + ", ")
                }
                else {
                    performersString += albums[position].performers[i].name
                }
            }
            performer.setText(performersString)
            holder.viewDataBinding.root.setOnClickListener{
                val action = AlbumFragmentDirections.actionAlbumListToAlbumDetail(albums[position].id)
                holder.viewDataBinding.root.findNavController().navigate(action)
            }
        }

    }

    override fun getItemCount(): Int {
        return albums.size
    }

    class AlbumViewHolder(val viewDataBinding: AlbumItemBinding) :
        RecyclerView.ViewHolder(viewDataBinding.root) {
        companion object {
            @LayoutRes
            val LAYOUT = R.layout.album_item
        }
    }
}