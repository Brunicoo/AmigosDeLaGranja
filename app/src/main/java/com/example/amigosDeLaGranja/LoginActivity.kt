package com.example.amigosDeLaGranja

import android.media.MediaPlayer
import android.os.Bundle
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.tutorialfarm.R


class LoginActivity : AppCompatActivity() {

    object constantsProject {
        const val playersList = "PLAYERLIST"
        const val index = "INDEX"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)

        val btonEasy = findViewById(R.id.btonEasy) as Button
        val btonHard = findViewById(R.id.btonHard) as Button
        val btonStart = findViewById(R.id.btonStart) as Button
        val userNameEditText = findViewById(R.id.userName) as EditText
        var difficulty: Boolean? = null
        var playersList: MutableList<Player> = mutableListOf()
        val tries: MutableList<Try> = mutableListOf()
        var index: Int

        playersList = Tools.readFromJson(this, "resultados.json", playersList)!!




        btonEasy.setOnClickListener()
        {
            difficulty = false
            btonEasy.setBackgroundResource(R.drawable.button_selected_easy)
            btonHard.setBackgroundResource(R.drawable.rectangle_borders2)
        }

        btonHard.setOnClickListener()
        {
            difficulty = true
            btonHard.setBackgroundResource(R.drawable.button_selected_hard)
            btonEasy.setBackgroundResource(R.drawable.rectangle_borders)
        }


        btonStart.setOnClickListener {
            val shake: Animation = AnimationUtils.loadAnimation(this, R.anim.shake)
            if (userNameEditText.text.isEmpty()) {

                userNameEditText.startAnimation(shake);

                Toast.makeText(this, "¡You must enter your name!", Toast.LENGTH_LONG).show()
            } else {
                val userName = userNameEditText.text.toString()
                if (difficulty != null) {
                    index = checkUser(playersList, tries, userName)

                    val loginSound = MediaPlayer.create(this, R.raw.login_sound)
                    loginSound.start()

                    if (difficulty == false) {
                        Tools.createActivity(this, EasyActivity::class.java, index, playersList)
                        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
                        finish()
                    } else {
                        Tools.createActivity(this, HardActivity::class.java, index, playersList)
                        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
                        finish()
                    }

                    loginSound.setOnCompletionListener {
                        loginSound.stop()
                    }
                } else {
                    btonEasy.startAnimation(shake)
                    btonHard.startAnimation(shake)
                    Toast.makeText(this, "¡You must choose the dificulty", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    private fun checkUser(
        playersList: MutableList<Player>,
        tries: MutableList<Try>,
        userName: String
    ): Int {
        val userExists = playersList.find { it.name.equals(userName, ignoreCase = true) }
        var index: Int

        if (userExists == null) {
            val player = Player(userName, tries)
            playersList.add(player)
            index = playersList.indexOfFirst { it.name == player.name }

        } else {
            val player = userExists
            index = playersList.indexOfFirst { it.name == player.name }


        }
        return index
    }
}