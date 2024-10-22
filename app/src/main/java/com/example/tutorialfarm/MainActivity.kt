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

class MainActivity : AppCompatActivity() {
    @RequiresApi(Build.VERSION_CODES.P)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        val handImageView = findViewById<ImageView>(R.id.handImageView)
        val gifImage = handImageView.drawable as AnimatedImageDrawable
        val animalImageView = findViewById<ImageView>(R.id.animalImageView)
        val bubleSound = MediaPlayer.create(this, R.raw.burbujas_sonido)
        val bubleImageView = findViewById<ImageView>(R.id.bubleGif)
        val bubleGif = bubleImageView.drawable as AnimatedImageDrawable
        val animalsJPG: ArrayList<Int> = arrayListOf(
            R.drawable.vaca,
            R.drawable.cerdo,
            R.drawable.gallina,
            R.drawable.conejo,
            R.drawable.oveja,
            R.drawable.caballo,
            R.drawable.perro,
            R.drawable.pato
        )

        val soundsMP3: ArrayList<Int> = arrayListOf(
            R.raw.vaca_sonido,
            R.raw.cerdo_sonido,
            R.raw.gallina_sonido,
            R.raw.rabbit,
            R.raw.oveja,
            R.raw.caballo_sonido,
            R.raw.perro_sonido,
            R.raw.pato_sonido
        )

        gifImage.start()

        CoroutineScope(Dispatchers.Main).launch {
            showAnimals(
                animalImageView,
                gifImage,
                animalsJPG,
                soundsMP3,
                bubleGif,
                bubleSound,
                bubleImageView
            )
        }
    }

    @RequiresApi(Build.VERSION_CODES.P)
    private suspend fun showAnimals(
        animalImageView: ImageView,
        gifImage: AnimatedImageDrawable,
        animalsJPG: ArrayList<Int>,
        soundsMP3: ArrayList<Int>,
        bubleGif: AnimatedImageDrawable,
        bubleSound: MediaPlayer,
        bubleImageView: ImageView
    ) {
        for (i in animalsJPG.indices) {
            animalImageView.isClickable = true
            animalImageView.setImageResource(animalsJPG[i])
            animalImageView.visibility = View.VISIBLE

            waitForClick(animalImageView)
            animalImageView.isClickable = false

            gifImage.stop()

            val animalSound = MediaPlayer.create(animalImageView.context, soundsMP3[i])
            animalSound.start()

            animalSound.setOnCompletionListener {
                it.release()
            }

            delay(3000)

            if (i<animalsJPG.size -1) {
                animalImageView.visibility = View.GONE
                bubleTransition(bubleGif, bubleSound, bubleImageView)
                delay(2000)
                bubleImageView.visibility = View.GONE
                //bubleSound.stop()
                bubleGif.stop()
                gifImage.start()
            }

            if (i>=animalsJPG.size - 1){
                finish()
            }
        }
    }


    @RequiresApi(Build.VERSION_CODES.P)
    private fun bubleTransition(
        bubleGif: AnimatedImageDrawable,
        bubleSound: MediaPlayer,
        bubleImageView: ImageView
    ) {
        bubleImageView.visibility = View.VISIBLE
        //bubleSound.start() me peta el programa si pongo este sonido
        bubleGif.stop()
        bubleGif.start()

        bubleSound.setOnCompletionListener {
            it.release()
        }
    }

    private suspend fun waitForClick(imageView: ImageView) {
        suspendCancellableCoroutine { continuation ->
            imageView.setOnClickListener {
                continuation.resume(Unit)
            }
        }
    }
}