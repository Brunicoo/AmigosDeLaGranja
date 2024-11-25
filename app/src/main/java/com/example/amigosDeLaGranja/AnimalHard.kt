package com.example.amigosDeLaGranja

import android.widget.ImageView
import com.example.mecanicadearrastre.Animal

class AnimalHard(
    val name: String,
    imageResId: Int,
    val isSilueta: Boolean,
    val imageView: ImageView,
    var isCorrect: Boolean,
    sound: Int,
    var position: Pair<Float, Float>? = null

) : Animal(imageResId,sound)