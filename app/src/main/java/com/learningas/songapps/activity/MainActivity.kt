package com.learningas.songapps.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.learningas.songapps.R
import com.learningas.songapps.room.Constant
import com.learningas.songapps.room.Song
import com.learningas.songapps.room.SongDB
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {
    private val db by lazy { SongDB(this) }
    lateinit var songAdapter: SongAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupView()
        setupListener()
        setupRecyclerView()
    }
    override fun onResume() {
        super.onResume()
        loadData()
    }

    private fun loadData(){
        CoroutineScope(Dispatchers.IO).launch {
            songAdapter.setData(db.songDao().getSongs())
            withContext(Dispatchers.Main) {
                songAdapter.notifyDataSetChanged()
            }
        }
    }

    private fun setupView (){
        supportActionBar!!.apply {
            title = "Lagu"
        }
    }

    private fun setupListener(){
        button_create.setOnClickListener {
            intentEdit(Constant.TYPE_CREATE, 0)
        }
    }

    private fun setupRecyclerView () {

        songAdapter = SongAdapter(
            arrayListOf(),
            object : SongAdapter.OnAdapterListener {
                override fun onClick(song: Song) {
                    intentEdit(Constant.TYPE_READ, song.id)
                }

                override fun onUpdate(song: Song) {
                    intentEdit(Constant.TYPE_UPDATE, song.id)
                }

                override fun onDelete(song: Song) {
                    deleteAlert(song)
                }

            })

        list_song.apply {
            layoutManager = LinearLayoutManager(applicationContext)
            adapter = songAdapter
        }

    }

    private fun intentEdit(intent_type: Int, song_id: Int) {
        startActivity(
            Intent(this, EditActivity::class.java)
                .putExtra("intent_type", intent_type)
                .putExtra("song_id", song_id)
        )

    }

    private fun deleteAlert(song: Song){
        val dialog = AlertDialog.Builder(this)
        dialog.apply {
            setTitle("Konfirmasi Hapus")
            setMessage("Yakin hapus ${song.judul}?")
            setNegativeButton("Batal") { dialogInterface, i ->
                dialogInterface.dismiss()
            }
            setPositiveButton("Hapus") { dialogInterface, i ->
                CoroutineScope(Dispatchers.IO).launch {
                    db.songDao().deleteSong(song)
                    dialogInterface.dismiss()
                    loadData()
                }
            }
        }

        dialog.show()
    }
}