package com.example.tutorialfarm

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import android.graphics.drawable.AnimatedImageDrawable
import android.media.MediaPlayer
import android.os.Build
import android.provider.MediaStore.Audio.Media
import android.view.View
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
    @RequiresApi(Build.VERSION_CODES.P)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        val handImageView = findViewById<ImageView>(R.id.handImageView)
        val gifImage = handImageView.drawable as AnimatedImageDrawable
        val animalImageView = findViewById<ImageView>(R.id.animalImageView)
        val animalSound = MediaPlayer.create(this, R.raw.vaca_sonido)

        gifImage.start()

        CoroutineScope(Dispatchers.Main).launch {
            showAnimals(
                animalImageView,
                gifImage,
                animalSound,
                handImageView
            )
        }
    }

    @RequiresApi(Build.VERSION_CODES.P)
    private suspend fun showAnimals(
        animalImageView: ImageView,
        gifImage: AnimatedImageDrawable,
        animalSound : MediaPlayer,
        handImageView: ImageView
    ) {
            animalImageView.visibility = View.VISIBLE
            handImageView.visibility = View.VISIBLE
            animalImageView.isClickable = true

            waitForClick(animalImageView)
            animalImageView.isClickable = false

            gifImage.stop()

            animalSound.start()

            animalSound.setOnCompletionListener {
                it.release()
                finish()
            }
        }

    @RequiresApi(Build.VERSION_CODES.P)
    private suspend fun waitForClick(imageView: ImageView) {
        suspendCancellableCoroutine { continuation ->
            imageView.setOnClickListener {
                continuation.resume(Unit)
            }
        }
    }
}