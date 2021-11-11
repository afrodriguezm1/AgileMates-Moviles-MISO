package com.example.vinilos.ui.artistas

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.vinilos.R
import com.example.vinilos.databinding.PerformerItemBinding
import com.example.vinilos.models.Performer
import com.squareup.picasso.Picasso

class PerformerAdapter : RecyclerView.Adapter<PerformerAdapter.PerformerViewHolder>() {
    var performers :List<Performer> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PerformerViewHolder {
        val withDataBinding: PerformerItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            PerformerViewHolder.LAYOUT,
            parent,
            false)
        return PerformerViewHolder(withDataBinding)
    }

    override fun onBindViewHolder(holder: PerformerViewHolder, position: Int) {
        holder.viewDataBinding.also {
            it.performer = performers[position]
            try{
                Picasso.get()
                    .load(performers[position].image)
                    .error(R.mipmap.ic_launcher_round)
                    .into(holder.viewDataBinding.imageCover)
            }
            catch (e: Exception) { }
        }

    }

    override fun getItemCount(): Int {
        return performers.size
    }

    class PerformerViewHolder(val viewDataBinding: PerformerItemBinding) :
        RecyclerView.ViewHolder(viewDataBinding.root) {
        companion object {
            @LayoutRes
            val LAYOUT = R.layout.performer_item
        }
    }
}
