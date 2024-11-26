package com.example.amigosDeLaGranja

import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.VideoView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.tutorialfarm.R

class TutorialHardActivity : AppCompatActivity() {
    object constantsProject {
        const val playersList = "PLAYERLIST"
        const val index = "INDEX"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_hard)

        val videoView = findViewById<VideoView>(R.id.videoView)
        val uri = Uri.parse("android.resource://" + packageName + "/" + R.raw.tutorial_dificil)

        val intent = intent
        val index = intent.getIntExtra(HardActivity.constantsProject.index, -1)
        val playersList : MutableList<Player> = intent.getParcelableArrayListExtra(HardActivity.constantsProject.playersList)!!

        videoView.visibility = View.VISIBLE
        videoView.setVideoURI(uri)

        videoView.start()

        videoView.setOnCompletionListener {
            Tools.createActivity(this, HardGame::class.java, index, playersList)
            finish()
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }
    }
}