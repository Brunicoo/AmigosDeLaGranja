package com.example.amigosDeLaGranja

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Point
import android.graphics.Rect
import android.media.MediaPlayer
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.Display
import android.view.MotionEvent
import android.view.View
import android.widget.Button
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.amigosDeLaGranja.AnimalHard
import com.example.amigosDeLaGranja.Try
import com.example.mecanicadearrastre.Animal
import com.example.tutorialfarm.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.util.Date
import kotlin.random.Random

class HardGame : AppCompatActivity() {
    private var dX = 0f
    private var dY = 0f
    private val threshold = 100 // Distancia en píxeles desde la parte superior
    private var selectedAnimal: AnimalHard? = null // Variable para almacenar el Animal seleccionado
    private var screenWidth = 0
    private var screenHeight = 0
    private var initialX = 0f
    private var initialY = 0f
    private var animalesEcnontrados = 0
    lateinit var burroSilueta: AnimalHard
    lateinit var cerdoSilueta: AnimalHard
    private lateinit var ovejaSilueta: AnimalHard
    lateinit var perroSilueta: AnimalHard
    lateinit var patoSilueta: AnimalHard
    lateinit var loroSilueta: AnimalHard
    lateinit var caballoSilueta: AnimalHard
    lateinit var bebeAngelSilueta: AnimalHard
    lateinit var vacaSilueta: AnimalHard
    lateinit var conejoSilueta: AnimalHard
    var animalsAdded1 = false
    var animalsAdded2 = false
    var animalsAdded3 = false
    var correctAnimal = false
    var counterErrors = 0
    var errorCounted = false

    private val posicionesIniciales: MutableList<Pair<Float, Float>> = mutableListOf()

    private var startTime = System.currentTimeMillis()  // Hora de inicio
    private var handler = Handler(Looper.getMainLooper())  // Handler para actualizar la UI
    private var timeRunnable: Runnable? = null  // Runnable para actualizar cada segundo
    private lateinit var mediaPlayer2: MediaPlayer // Declaramos el MediaPlayer globalmente
    var timeString: String = ""

    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("MissingInflatedId", "ClickableViewAccessibility", "SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activit_hard_game)
        startTimer()

        val intent = intent
        val index = intent.getIntExtra(TutorialHardActivity.constantsProject.index, -1)
        val playersList : MutableList<Player> = intent.getParcelableArrayListExtra(TutorialHardActivity.constantsProject.playersList)!!

        // Inicializar el MediaPlayer y empezar la música de fondo
        mediaPlayer2 = MediaPlayer.create(this@HardGame, R.raw.bandasonora)
        mediaPlayer2.isLooping = true  // Reproducir música en bucle
        mediaPlayer2.start()
        mediaPlayer2.setVolume(
            0.3f,
            0.3f
        ) // 0.3f es el volumen (en este caso 30% de su volumen máximo)

        // Obtener las dimensiones de la pantalla
        val display: Display = windowManager.defaultDisplay
        val size = Point()
        display.getSize(size)
        screenWidth = size.x
        screenHeight = size.y

        // Crear los objetos Animal, asignando su propio ImageView desde el layout
        val gallina = AnimalHard(
            "gallina", R.drawable.gallina, false, findViewById(R.id.gallina), false, R.raw.gallina_sonido, Pair(dpToPx(13,this), dpToPx(45, this))
        )

        val cerdo = AnimalHard(
            "cerdo", R.drawable.cerdo, false, findViewById(R.id.imgCerdo), false, R.raw.cerdo_sonido, Pair(dpToPx(13, this), dpToPx(170, this))
        )

        val oveja = AnimalHard(
            "oveja", R.drawable.oveja, false, findViewById(R.id.imgOveja), false, R.raw.oveja, Pair(dpToPx(15, this), dpToPx(294, this))
        )

        val pato = AnimalHard(
            "pato", R.drawable.pato, false, findViewById(R.id.imgPato), false, R.raw.pato_sonido, Pair(dpToPx(this.resources.getDimensionPixelSize(R.dimen.translationX_low_size), this), dpToPx(460, this))
        )

        val caballo = AnimalHard(
            "caballo", R.drawable.caballo, false, findViewById(R.id.imgCaballo), false, R.raw.caballo_sonido, Pair(dpToPx(0, this), dpToPx(557, this))
        )

        val perro = AnimalHard(
            "perro", R.drawable.perro, false, findViewById(R.id.imgPerro), false, R.raw.perro_sonido, Pair(dpToPx(1140, this), dpToPx(24, this))
        )

        val conejo = AnimalHard(
            "conejo", R.drawable.conejo, false, findViewById(R.id.imgConejo), false, R.raw.conejo, Pair(dpToPx(1160, this), dpToPx(180, this))
        )

        val vaca = AnimalHard(
            "vaca", R.drawable.vaca, false, findViewById(R.id.imgVaca), false, R.raw.vaca_sonido, Pair(dpToPx(1134, this), dpToPx(290, this))
        )

        val loro = AnimalHard(
            "loro", R.drawable.loro, false, findViewById(R.id.imgLoro), false, R.raw.loro, Pair(dpToPx(1155, this), dpToPx(450, this))
        )

        val burro = AnimalHard(
            "burro", R.drawable.burro, false, findViewById(R.id.imgBurro), false, R.raw.burro, Pair(dpToPx(1127, this), dpToPx(560, this))
        )


        bebeAngelSilueta = AnimalHard(
            "gallina", R.drawable.gallinasilueta, true, findViewById(R.id.imgSiluetaGallina), false, R.raw.gallina_sonido
        )
        cerdoSilueta = AnimalHard(
            "cerdo", R.drawable.siluetacerdo, true, findViewById(R.id.imgSiluetaCerdo), false, R.raw.cerdo_sonido
        )
        ovejaSilueta = AnimalHard(
            "oveja", R.drawable.ovejasilueta, true, findViewById(R.id.imgOvejaSilueta), false, R.raw.oveja
        )
        patoSilueta = AnimalHard(
            "pato", R.drawable.patosilueta, true, findViewById(R.id.imgPatoSilueta), false, R.raw.pato_sonido
        )
        caballoSilueta = AnimalHard(
            "caballo", R.drawable.caballosilueta, true, findViewById(R.id.imgCaballoSilueta), false, R.raw.caballo_sonido
        )
        perroSilueta = AnimalHard(
            "perro", R.drawable.perrosilueta, true, findViewById(R.id.imgPerroSilueta), false, R.raw.perro_sonido
        )
        loroSilueta = AnimalHard(
            "loro", R.drawable.lorosilueta, true, findViewById(R.id.imgLoroSilueta), false, R.raw.loro
        )
        burroSilueta = AnimalHard(
            "burro", R.drawable.burrosilueta, true, findViewById(R.id.imgBurroSilueta), false, R.raw.burro
        )
        vacaSilueta = AnimalHard(
            "vaca", R.drawable.vacasilueta, true, findViewById(R.id.imgVacaSilueta), false, R.raw.vaca_sonido
        )
        conejoSilueta = AnimalHard(
            "conejo", R.drawable.conejosilueta, true, findViewById(R.id.imgConejoSilueta), false, R.raw.conejo
        )


        var guardarPosiciones: MutableList<IntArray> = mutableListOf()
        val animales =
            listOf(perro, gallina, cerdo, oveja, pato, caballo, loro, burro, conejo, vaca)

        // Declarar animalesAnadir como MutableList
        var animalesSilueta: MutableList<AnimalHard> =
            mutableListOf(burroSilueta, cerdoSilueta, ovejaSilueta)

        setupInitialPositions(animalesSilueta, guardarPosiciones)

        // Asignar el listener de toque a cada ImageView de los animales
        animales.forEach { animal ->
            animal.imageView.setImageResource(animal.imageResId) // Asignar la imagen del animal al ImageView
            if (!animal.isSilueta) {
                setTouchListener(animal, animales, animalesSilueta, index, playersList)
            }
        }
    }

    private fun dpToPx(dp: Int, context: Context): Float {
        val density = context.resources.displayMetrics.density
        return dp * density
    }

    // Función para asignar el OnTouchListener a cada Animal
    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("ClickableViewAccessibility")

    private fun setTouchListener(
        animal: AnimalHard,
        animales: List<AnimalHard>,
        animalesAnadir: MutableList<AnimalHard>,
        index : Int,
        playersList : MutableList<Player>
    ) {

        animal.imageView.setOnTouchListener { view, event ->
            if (view.visibility == View.GONE) return@setOnTouchListener false // Ignorar si está oculto
            when (event.action) {

                MotionEvent.ACTION_DOWN -> {
                    errorCounted = false

                    dX = view.x - event.rawX
                    dY = view.y - event.rawY
                    selectedAnimal = animal
                    initialX = view.x
                    initialY = view.y
                }

                MotionEvent.ACTION_MOVE -> {
                    view.x = event.rawX + dX
                    view.y = event.rawY + dY

                    // Lógica de colisión
                    animalesAnadir.forEach { otherAnimal ->
                        if (otherAnimal != animal && isColliding(
                                animal,
                                view,
                                otherAnimal.imageView
                            )
                        ) {
                            if (animal.name == otherAnimal.name && !animal.isCorrect) {
                                otherAnimal.imageView.setImageResource(animal.imageResId)
                                animal.imageView.visibility = View.GONE
                                animal.isCorrect = true
                                otherAnimal.isCorrect = true
                                correctAnimal = true
                                animalesEcnontrados += 1
                                reproducirSonidoAnimal(animal)

                            }

                        }
                    }
                    if (animalesEcnontrados in 3..6 && !animalsAdded1) {
                        animalsAdded1 = true
                        pasarFase(
                            animalesAnadir,
                            R.id.background,
                            arrayOf(perroSilueta, bebeAngelSilueta, loroSilueta),
                            R.drawable.fondocorralgranja
                        )

                    } else if (animalesEcnontrados >= 6 && !animalsAdded2) {
                        pasarFase(
                            animalesAnadir,
                            R.id.background,
                            arrayOf(caballoSilueta, conejoSilueta, vacaSilueta, patoSilueta),
                            R.drawable.fondopaisaje
                        )
                        animalsAdded2 = true

                    } else if (animalesEcnontrados == 10 && !animalsAdded3) {
                        animalsAdded3 = true
                        stopTimer()
                        eliminateCompleteAnimals(animalesAnadir)
                        //createTry()
                        makeFinalVisible(animales, index, playersList)

                    }


                }

                MotionEvent.ACTION_UP -> {
                    view.animate()
                        .x(initialX)
                        .y(initialY)
                        .setDuration(300)
                        .start()
                    correctAnimal = false
                    if (!correctAnimal && !errorCounted) {
                        errorCounter(view.x, view.y)
                    }


                    // Actualizar animalesAnadir aquí
                }


                else -> return@setOnTouchListener false
            }

            true

        }

    }

    private fun reproducirSonidoAnimal(animal: AnimalHard) {
        // Determinar el nombre del archivo de sonido basándonos en el nombre del animal

        val nombreArchivoSonido = animal.sound

        // Si se ha encontrado un archivo de sonido válido, lo reproducimos
        if (nombreArchivoSonido != null) {
            val mediaPlayer = MediaPlayer.create(this@HardGame, nombreArchivoSonido)
            mediaPlayer.start()
        } else {
            Log.e("ReproducirSonido", "No se encontró sonido para el animal: ${animal.name}")
        }
    }

    private fun errorCounter(x: Float, y: Float) {
        counterErrors++
        var errorView = findViewById(R.id.errorsTextView) as TextView
        errorView.text = counterErrors.toString()
        errorCounted = true;
        var newImageError: ImageView = findViewById(R.id.errorImage)
        newImageError.x = x
        newImageError.y = y
        newImageError.visibility = View.VISIBLE
        CoroutineScope(Dispatchers.Main).launch {
            delay(1000)
            newImageError.visibility = View.GONE
        }
        val errorPlayer = MediaPlayer.create(this@HardGame, R.raw.error)
        errorPlayer.start()
        errorPlayer.setVolume(
            1f,
            1f
        ) // 0.3f es el volumen (en este caso 30% de su volumen máximo)
        errorPlayer.setOnCompletionListener() {
            errorPlayer.stop()
        }
    }



    // Método para verificar si dos vistas se están superponiendo
    private fun isColliding(animal: Animal, view1: View, view2: View): Boolean {
        val location1 = IntArray(2)
        val location2 = IntArray(2)
        view1.getLocationOnScreen(location1)
        view2.getLocationOnScreen(location2)
        var reaction = 0
        if (view1.width == 80) {
            reaction = 80
        } else if (view1.width == 110) {
            reaction = 90
        } else {
            reaction = 125
        }

        val rect1 = Rect(
            location1[0],
            location1[1],
            location1[0] + view1.width - reaction,
            location1[1] + view1.height - reaction
        )
        val rect2 = Rect(
            location2[0],
            location2[1],
            location2[0] + view2.width,
            location2[1] + view2.height
        )

        return Rect.intersects(rect1, rect2)
    }

    private fun pasarFase(
        animals: MutableList<AnimalHard>,
        beforeBackground: Int,
        arrayAnimales: Array<AnimalHard>,
        nextBackground: Int
    ) {
        CoroutineScope(Dispatchers.Main).launch {
            try {
                // Retraso para asegurar que la animación anterior se complete
                delay(200)

                // Elimina los animales actuales
                eliminateCompleteAnimals(animals)

                // Reproduce el sonido de completar nivel
                val mediaPlayer =
                    MediaPlayer.create(this@HardGame, R.raw.app_src_main_res_raw_levelcomplete)
                mediaPlayer.start()

                // Retraso para permitir que el sonido se reproduzca un poco
                delay(500)

                // Cambia el fondo y configura los nuevos animales
                val backgroundView = findViewById<FrameLayout>(beforeBackground)
                backgroundView.setBackgroundResource(nextBackground)

                // Limpia la lista de animales y agrega los nuevos
                animals.clear()
                animals.addAll(arrayAnimales)

                // Establece las posiciones iniciales de los nuevos animales
                val guardarPosiciones2: MutableList<IntArray> = mutableListOf()
                setupInitialPositions(animals, guardarPosiciones2)
                var newImageError: ImageView = findViewById(R.id.errorImage)
                newImageError.visibility = View.GONE
            } catch (e: Exception) {
                // Manejo de errores: imprime el mensaje de error en el Logcat
                Log.e("pasarFase", "Error al cambiar de fase: ${e.message}", e)
            }
        }
    }

    private fun startTimer() {
        // Runnable que se ejecuta cada segundo
        timeRunnable = object : Runnable {
            override fun run() {
                // Obtener el tiempo transcurrido
                val elapsedMillis = System.currentTimeMillis() - startTime
                val seconds = (elapsedMillis / 1000) % 60
                val minutes = (elapsedMillis / 1000) / 60

                // Formatear el tiempo en "MM:SS"
                timeString = String.format("%02d:%02d", minutes, seconds)

                // Actualizar el TextView con el tiempo
                val timerTextView = findViewById<TextView>(R.id.timerTextView)
                timerTextView.text = timeString

                // Reprogramar el siguiente "tick"
                handler.postDelayed(this, 1000)
            }
        }

        // Iniciar el timer
        handler.post(timeRunnable!!)
    }

    private fun eliminateCompleteAnimals(animals: MutableList<AnimalHard>) {
        for (animal in animals) {
            val view = animal.imageView
            view.visibility = View.GONE
        }
    }

    private fun setupInitialPositions(
        animalesAnadir: MutableList<AnimalHard>,
        guardarPosiciones: MutableList<IntArray>
    ) {
        animalesAnadir.forEach { animal ->
            animal.imageView.setImageResource(animal.imageResId) // Asignar la imagen al ImageView
            animal.imageView.visibility = View.VISIBLE // Asegurar que el animal sea visible
            setRandomPosition(animal, guardarPosiciones) // Posicionar en una ubicación aleatoria

        }
    }

    // Función para establecer una posición aleatoria para el ImageView de un animal
    private fun setRandomPosition(animal: AnimalHard, guardarPosiciones: MutableList<IntArray>) {
        val margin =
            200 // Ajuste del margen de distancia entre animales, valor más pequeño para tablets con pantallas más pequeñas
        var randomX: Int
        var randomY: Int

        // Asegurarse de que las posiciones aleatorias estén dentro de los límites de la pantalla
        do {
            // Usamos el tamaño de la pantalla para calcular un rango de valores adecuado
            randomX = Random.nextInt(200, screenWidth - animal.imageView.width - 400)
            randomY = Random.nextInt(600, screenHeight - animal.imageView.height - 250)

            // Log de las posiciones generadas para depuración
            Log.d("RandomPosition", "Generated position: ($randomX, $randomY)")
        } while (!isPositionValid(
                randomX,
                randomY,
                guardarPosiciones
            )
        ) // Comprobar si la posición es válida

        guardarPosiciones.add(
            intArrayOf(
                randomX,
                randomY
            )
        ) // Guardar la posición en la lista para validación

        animal.imageView.x = randomX.toFloat() // Asignar la posición X
        animal.imageView.y = randomY.toFloat() // Asignar la posición Y

        Log.d(
            "AnimalPosition",
            "Animal ${animal.name} placed at: ($randomX, $randomY)"
        ) // Log de la posición final
    }

    private fun isPositionValid(x: Int, y: Int, guardarPosiciones: MutableList<IntArray>): Boolean {
        for (posicion in guardarPosiciones) {
            if (estaEnMargen(posicion[0], x, 125) && estaEnMargen(posicion[1], y, 125)) {
                return false
            }
        }
        return true
    }

    fun estaEnMargen(numero: Int, referencia: Int, margen: Int = 10): Boolean {
        return numero in (referencia - margen)..(referencia + margen)
    }

    private fun stopTimer() {
        timeRunnable?.let { handler.removeCallbacks(it) }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createTry() {
        val date = LocalDateTime.now()
        val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        val dateString = dateFormat.format(date)
        var tryDef = Try(timeRunnable.toString().toInt(), counterErrors, "Hard", dateString)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun makeFinalVisible(animales: List<AnimalHard>, index : Int, playersList: MutableList<Player>) {
        CoroutineScope(Dispatchers.Main).launch {
            delay(300)
        }
        val oscurecerFinal = findViewById(R.id.oscurecerFinal) as View
        val mostrarFinal = findViewById(R.id.nonDimmedArea) as FrameLayout
        val textoTiempo = findViewById(R.id.finalTimeTextView) as TextView
        val textoErrores = findViewById(R.id.finalErrorCounter) as TextView
        val reiniciarJuego = findViewById(R.id.botonReplay) as ImageView
        val btonExit = findViewById(R.id.btonExit) as ImageView
        textoTiempo.text = timeString
        textoErrores.text = counterErrors.toString()
        oscurecerFinal.visibility = View.VISIBLE
        mostrarFinal.visibility = View.VISIBLE
        animalesEcnontrados = 0
        counterErrors = 0
        errorCounted = false
        animalsAdded1 = false
        animalsAdded2 = false
        animalsAdded3 = false
        reiniciarJuego.setOnClickListener {
            Tools.createActivity(this, HardGame::class.java, index, playersList)
            mediaPlayer2.stop()
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
            finish()
        }

        btonExit.setOnClickListener()
        {
            Tools.createActivitySimple(this, LoginActivity::class.java)
            mediaPlayer2.stop()
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
            finish()
        }
    }
}