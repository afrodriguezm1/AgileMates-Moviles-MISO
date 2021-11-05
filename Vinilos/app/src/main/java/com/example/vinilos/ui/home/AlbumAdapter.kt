package com.example.vinilos.ui.home

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.example.vinilos.R
import com.example.vinilos.models.Album
import com.squareup.picasso.Picasso
import java.lang.Exception

class AlbumAdapter(private val context: Activity, private val arraylist: ArrayList<Album>): ArrayAdapter<Album>(context, R.layout.album_item, arraylist) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        val inflater: LayoutInflater = LayoutInflater.from(context)
        val view: View = inflater.inflate(R.layout.album_item, null)

        val cover : ImageView = view.findViewById(R.id.albumCover)
        val name : TextView = view.findViewById(R.id.albumTitle)
        val year : TextView = view.findViewById(R.id.albumYear)
        val recordLabel : TextView = view.findViewById(R.id.albumSinger)


        name.text = arraylist[position].name
        year.text = arraylist[position].releaseDate
        recordLabel.text = arraylist[position].recordLabel
        try{
            Picasso.get()
                .load(arraylist[position].cover)
                .error(R.mipmap.ic_launcher_round)
                .into(cover)
        }
        catch (e: Exception) {

        }


        return view
    }
}