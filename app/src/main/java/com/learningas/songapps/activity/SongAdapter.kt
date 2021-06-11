package com.learningas.songapps.activity

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.learningas.songapps.R
import com.learningas.songapps.room.Song
import kotlinx.android.synthetic.main.adapter_main.view.*

class SongAdapter (var songs: ArrayList<Song>, var listener: OnAdapterListener) :
    RecyclerView.Adapter<SongAdapter.SongViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SongViewHolder {
        return SongViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(
                    R.layout.adapter_main,
                    parent,
                    false
                )
        )
    }

    override fun getItemCount() = songs.size

    override fun onBindViewHolder(holder: SongViewHolder, position: Int) {
        val song = songs[position]
        holder.view.text_title.text = song.judul
        holder.view.text_title.setOnClickListener {
            listener.onClick(song)
        }
        holder.view.icon_edit.setOnClickListener {
            listener.onUpdate(song)
        }
        holder.view.icon_delete.setOnClickListener {
            listener.onDelete(song)
        }
    }

    class SongViewHolder(val view: View) : RecyclerView.ViewHolder(view)

    fun setData(newList: List<Song>) {
        songs.clear()
        songs.addAll(newList)
    }

    interface OnAdapterListener {
        fun onClick(song: Song)
        fun onUpdate(song: Song)
        fun onDelete(song: Song)
    }
}