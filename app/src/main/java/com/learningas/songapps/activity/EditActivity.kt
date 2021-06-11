package com.learningas.songapps.activity

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.learningas.songapps.R
import com.learningas.songapps.room.Constant
import com.learningas.songapps.room.Song
import com.learningas.songapps.room.SongDB
import kotlinx.android.synthetic.main.activity_edit.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class EditActivity : AppCompatActivity() {

    private val db by lazy { SongDB(this) }
    private var songId = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit)
        setupView()
        setupLstener()
    }

    private fun setupView(){
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        when (intentType()) {
            Constant.TYPE_CREATE -> {
                supportActionBar!!.title = "TAMBAH LAGU"
                button_save.visibility = View.VISIBLE
                button_update.visibility = View.GONE
            }
            Constant.TYPE_READ -> {
                supportActionBar!!.title = "LIHAT"
                button_save.visibility = View.GONE
                button_update.visibility = View.GONE
                getSong()
            }
            Constant.TYPE_UPDATE -> {
                supportActionBar!!.title = "EDIT LAGU"
                button_save.visibility = View.GONE
                button_update.visibility = View.VISIBLE
                getSong()
            }
        }
    }

    private fun setupLstener(){
        button_save.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                db.songDao().addSong(
                    Song(
                        0,
                        edit_title.text.toString(),
                        edit_song.text.toString()
                    )
                )
                finish()
            }
        }
        button_update.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                db.songDao().updateSong(
                    Song(
                        songId,
                        edit_title.text.toString(),
                        edit_song.text.toString()
                    )
                )
                finish()
            }
        }
    }

    private fun getSong(){
        songId = intent.getIntExtra("song_id", 0)
        CoroutineScope(Dispatchers.IO).launch {
            val songs = db.songDao().getSong(songId).get(0)
            edit_title.setText( songs.judul )
            edit_song.setText( songs.lirik )
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return super.onSupportNavigateUp()
    }

    private fun intentType(): Int {
        return intent.getIntExtra("intent_type", 0)
    }
}