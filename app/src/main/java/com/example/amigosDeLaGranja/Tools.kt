package com.example.amigosDeLaGranja

import android.content.Context
import android.content.Intent

class Tools {
    companion object {
        fun createActivity(context: Context, activityClass: Class<*>, index: Int, playersList: MutableList<Player>) {
            val intent = Intent(context, activityClass)
            intent.putParcelableArrayListExtra(LoginActivity.constantsProject.playersList, ArrayList(playersList))
            intent.putExtra(LoginActivity.constantsProject.index, index)
            context.startActivity(intent)
        }
    }
}
