package com.example.amigosDeLaGranja

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.tutorialfarm.R

class HardActivity : AppCompatActivity() {

    object constantsProject {
        const val playersList = "PLAYERLIST"
        const val index = "INDEX"
    }

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_hard)

        val btonHard = findViewById(R.id.btonHard) as Button

        val intent = intent
        val index = intent.getIntExtra(LoginActivity.constantsProject.index, -1)
        val playersList : MutableList<Player> = intent.getParcelableArrayListExtra(LoginActivity.constantsProject.playersList)!!

        btonHard.setOnClickListener(){
            Tools.createActivity(this, TutorialHardActivity::class.java, index, playersList)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
            finish()
        }
    }
}