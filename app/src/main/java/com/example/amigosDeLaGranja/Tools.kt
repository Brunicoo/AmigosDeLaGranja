package com.example.amigosDeLaGranja

import android.content.Context
import android.content.Intent
import android.os.Environment
import android.util.Log
import android.widget.Toast
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.File
import java.io.FileReader
import java.io.FileWriter
import java.io.IOException

class Tools {
    companion object {
        fun createActivity(context: Context, activityClass: Class<*>, index: Int, playersList: MutableList<Player>) {
            val intent = Intent(context, activityClass)
            intent.putParcelableArrayListExtra(LoginActivity.constantsProject.playersList, ArrayList(playersList))
            intent.putExtra(LoginActivity.constantsProject.index, index)
            context.startActivity(intent)
        }

        fun createActivitySimple(context: Context, activityClass: Class<*>) {
            val intent = Intent(context, activityClass)
            context.startActivity(intent)
        }


        fun saveTryToJson(playersList: MutableList<Player>,context: Context) {
            val file = File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "resultados.json")

            try {

                // Serializar la lista de Player a JSON
                val gson = Gson()
                val json = gson.toJson(playersList)

                // Escribir el JSON en el archivo
                val writer = FileWriter(file)
                writer.write(json)
                writer.flush()
                writer.close()

                println("Archivo JSON guardado correctamente en: ${file.absolutePath}")
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }

        fun loadPlayers(): ArrayList<Player> {
            val playersList = ArrayList<Player>()
            val file = File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "resultados.json")

            try {
                if (!file.exists()) {
                    println("El archivo no existe.")
                    return playersList
                }

                // Verificar si el archivo está vacío
                if (file.length() == 0L) {
                    println("El archivo está vacío.")

                    return playersList
                } else {

                    // Leer el contenido del archivo
                    val reader = FileReader(file)
                    val gson = Gson()

                    // Deserializar el JSON a un array de jugadores
                    val playersArray = gson.fromJson(reader, Array<Player>::class.java)

                    // Añadir los jugadores al ArrayList
                    playersList.addAll(playersArray)

                    reader.close()
                }

            } catch (e: IOException) {
                e.printStackTrace()
                println("Error al leer el archivo JSON: ${e.message}")
            }

            return playersList
        }
    }
}
