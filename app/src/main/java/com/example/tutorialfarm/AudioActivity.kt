package com.example.tutorialfarm

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import android.media.MediaPlayer
import android.os.Build
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.annotation.RequiresApi

class AudioActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    @RequiresApi(Build.VERSION_CODES.P)
    private lateinit var audio: MediaPlayer
    @RequiresApi(Build.VERSION_CODES.P)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_audio)

        // ** cojer la variable userName ** //

        val imageHector = findViewById(R.id.imageHector) as ImageView
        val btonSkip = findViewById(R.id.buttonSkip) as ImageView

        val moveAnimation: Animation = AnimationUtils.loadAnimation(this, R.anim.move_up_down)
        imageHector.startAnimation(moveAnimation)

        val buttonAnimation = AnimationUtils.loadAnimation(this, R.anim.scale_animation)

         audio = MediaPlayer.create(this, R.raw.audio_hector)
        audio.start()

        btonSkip.setOnClickListener(){
            audio.stop()
            btonSkip.startAnimation(buttonAnimation)
            val intent = Intent(this, TutorialActivity :: class.java)
            //intent.putExtra(SecondActivity.userNameConstants.userName, userName)
            startActivity(intent)

            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        }

        audio.setOnCompletionListener {
            audio.stop()
            val intent = Intent(this, TutorialActivity :: class.java)
            //intent.putExtra(SecondActivity.userNameConstants.userName, userName)
            startActivity(intent)

            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        }
    }
}