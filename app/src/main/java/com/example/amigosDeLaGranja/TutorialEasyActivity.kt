package com.example.amigosDeLaGranja

import android.annotation.SuppressLint
import android.media.MediaPlayer
import android.os.Build
import android.os.Bundle
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.tutorialfarm.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume

class TutorialEasyActivity : AppCompatActivity() {

    object constantsProject {
        const val playersList = "PLAYERLIST"
        const val index = "INDEX"
    }

    @SuppressLint("MissingInflatedId")
    @RequiresApi(Build.VERSION_CODES.P)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_tutorial_easy)

        val handImageView = findViewById<ImageView>(R.id.handImageView)
        val animalImageView = findViewById<ImageView>(R.id.animalImageView)
        val animalSound = MediaPlayer.create(this, R.raw.vaca_sonido)
        val moveAnimation: Animation = AnimationUtils.loadAnimation(this, R.anim.scale_animation)

        val intent = intent
        val playersList : MutableList<Player> = intent.getParcelableArrayListExtra(AudioActivity_Easy.constantsProject.playersList)!!
        val index = intent.getIntExtra(AudioActivity_Easy.constantsProject.index, -1)

        handImageView.startAnimation(moveAnimation)

        CoroutineScope(Dispatchers.Main).launch {
            showAnimals(
                animalImageView,
                animalSound,
                handImageView,
                index,
                playersList
            )
        }
    }

    @RequiresApi(Build.VERSION_CODES.P)
    private suspend fun showAnimals(
        animalImageView: ImageView,
        animalSound : MediaPlayer,
        handImageView: ImageView,
        index : Int,
        playersList : MutableList<Player>
    ) {

        waitForClick(animalImageView)
        animalImageView.isClickable = false

        animalSound.start()

        animalSound.setOnCompletionListener {
            it.release()
            Tools.createActivity(this, EasyGame::class.java, index, playersList)
            finish()
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
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