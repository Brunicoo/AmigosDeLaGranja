package com.example.tutorialfarm

import android.annotation.SuppressLint
import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity

class EasyActivity : AppCompatActivity() {

    object constantsProject {
        const val playersList = "PLAYERLIST"
        const val index = "INDEX"
    }
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_easy)

        val btonEasy = findViewById(R.id.btonEasy) as Button
        val loginSound = MediaPlayer.create(this, R.raw.login_sound)
        val intent = intent

        val playersList : MutableList<Player> = intent.getParcelableArrayListExtra(LoginActivity.constantsProject.playersList)!!
        val index = intent.getIntExtra(LoginActivity.constantsProject.index, -1)

        btonEasy.setOnClickListener()
        {
            loginSound.start()
            Tools.createActivity(this, AudioActivity::class.java, index, playersList)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
            loginSound.setOnCompletionListener {
                loginSound.stop()
                it.release()
            }
        }
    }
}