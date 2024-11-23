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
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class AudioActivity : AppCompatActivity() {

    object constantsProject {
        const val playersList = "PLAYERLIST"
        const val index = "INDEX"
    }

    @SuppressLint("MissingInflatedId")
    @RequiresApi(Build.VERSION_CODES.P)

    private lateinit var audio: MediaPlayer

    @RequiresApi(Build.VERSION_CODES.P)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_audio)

        val imageHector = findViewById(R.id.imageHector) as ImageView
        val btonSkip = findViewById(R.id.buttonSkip) as ImageView
        val clickSound = MediaPlayer.create(this, R.raw.menu_sound)
        val textView = findViewById(R.id.talkHector) as TextView

        val intent = intent
        val playersList : MutableList<Player> = intent.getParcelableArrayListExtra(EasyActivity.constantsProject.playersList)!!
        val index = intent.getIntExtra(EasyActivity.constantsProject.index, -1)

        val moveAnimation: Animation = AnimationUtils.loadAnimation(this, R.anim.move_up_down)
        imageHector.startAnimation(moveAnimation)

         audio = MediaPlayer.create(this, R.raw.audio_hector)
         audio.start()

        lifecycleScope.launch {
            wordsAudio(textView)
        }

        btonSkip.setOnClickListener(){
            clickSound.start()
            audio.stop()
            audio.release()
            Tools.createActivity(this, TutorialEasyActivity::class.java, index, playersList)

            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }

        audio.setOnCompletionListener {
            audio.stop()
            it.release()

            Tools.createActivity(this, TutorialEasyActivity::class.java, index, playersList)

            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        }
    }

    suspend fun wordsAudio(textView : TextView)
    {
        textView.text = "¡Hola, muy buenas, amigos! Soy el granjero Héctor. "
        delay(5000)
        textView.text = "¡Y tengo un problema! Solo tú me puedes ayudar a solucionarlo."
        delay(5000)
        textView.text = "¿Podrías ayudarme? He perdido varios animales de mi granja y no sé por dónde están."
        delay(6000)
        textView.text = "¡Solo tú puedes encontrarlos! ¡Mucha suerte!"

    }
}