package com.example.tutorialfarm

class Player(var name: String, val tries: MutableList<Try> = mutableListOf()) {

    data class Player(
        val userName: String,
        val tries: Int
    )

    fun addTry(newTry: Try) {
        this.tries.add(newTry)
    }
}