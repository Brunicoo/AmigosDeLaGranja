package com.example.tutorialfarm

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import android.graphics.drawable.AnimatedImageDrawable
import android.media.MediaPlayer
import android.os.Build
import android.provider.MediaStore.Audio.Media
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.view.isVisible
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume
import kotlin.random.Random
import kotlin.random.Random.Default.nextInt

class MainActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    @RequiresApi(Build.VERSION_CODES.P)
    private lateinit var audio: MediaPlayer
    @RequiresApi(Build.VERSION_CODES.P)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        // ** cojer la variable userName ** //

        val imageHector = findViewById(R.id.imageHector) as ImageView

        val moveAnimation: Animation = AnimationUtils.loadAnimation(this, R.anim.move_up_down)
        imageHector.startAnimation(moveAnimation)

         audio = MediaPlayer.create(this, R.raw.audio_hector)
        audio.start()

        audio.setOnCompletionListener {
            audio.stop()
            val intent = Intent(this, SecondActivity :: class.java)
            //intent.putExtra(SecondActivity.userNameConstants.userName, userName)
            startActivity(intent)

            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        }
    }
}