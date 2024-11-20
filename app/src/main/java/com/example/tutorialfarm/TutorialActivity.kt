package com.example.tutorialfarm

import android.graphics.drawable.AnimatedImageDrawable
import android.media.MediaPlayer
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume

class TutorialActivity : AppCompatActivity() {
    object userNameConstants{
        const val userName = "USER"
    }
    @RequiresApi(Build.VERSION_CODES.P)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.second_activity)

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