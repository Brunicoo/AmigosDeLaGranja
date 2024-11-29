package com.example.amigosDeLaGranja

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Environment
import android.widget.Toast
import com.google.gson.Gson
import java.io.File
import java.io.FileOutputStream
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

        fun writeOnJson(context: Context, playersList: List<Player>, jsonFile: String) {

            if (Environment.getExternalStorageState() != Environment.MEDIA_MOUNTED) {
                Toast.makeText(context, "El almacenamiento externo no está disponible.", Toast.LENGTH_SHORT).show()
                return
            }

            // Obtener la ruta de la carpeta "Descargas" en el almacenamiento externo
            val downloadsFolder = File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "")


            // Crear el archivo JSON
            val archivoJson = File(downloadsFolder, jsonFile)

            // Convertir la lista de jugadores a JSON usando Gson
            val gson = Gson()
            val json = gson.toJson(mapOf("miArrayList" to playersList))

            // Escribir el JSON en el archivo
            try {
                FileOutputStream(archivoJson).use { outputStream ->
                    outputStream.write(json.toByteArray())
                }
                Toast.makeText(context, "El archivo JSON se guardó correctamente en Descargas.", Toast.LENGTH_SHORT).show()
            } catch (e: IOException) {
                Toast.makeText(context, "Error al guardar el archivo: ${e.message}", Toast.LENGTH_LONG).show()
            }
        }
    }
}
