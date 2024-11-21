package com.example.tutorialfarm

import android.annotation.SuppressLint
import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity

class EasyActivity : AppCompatActivity() {

    object userNameConstants {
        const val userName = "USER"
    }
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_easy)

        val btonEasy = findViewById(R.id.btonEasy) as Button
        val loginSound = MediaPlayer.create(this, R.raw.login_sound)

        btonEasy.setOnClickListener()
        {
            loginSound.start()
            val intent = Intent(this, AudioActivity::class.java)
            startActivity(intent)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
            loginSound.setOnCompletionListener {
                loginSound.stop()
                it.release()
            }
        }
    }
}