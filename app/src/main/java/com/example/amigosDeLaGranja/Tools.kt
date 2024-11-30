package com.example.amigosDeLaGranja

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Environment
import android.widget.Toast
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import com.google.gson.reflect.TypeToken
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

            val downloadsFolder = File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "")

            val archivoJson = File(downloadsFolder, jsonFile)

            val gson = Gson()
            val json = gson.toJson(mapOf("PLAYERS" to playersList))

            try {
                FileOutputStream(archivoJson).use { outputStream ->
                    outputStream.write(json.toByteArray())
                }
                Toast.makeText(context, "El archivo JSON se guardó correctamente en Descargas.", Toast.LENGTH_SHORT).show()
            } catch (e: IOException) {
                Toast.makeText(context, "Error al guardar el archivo: ${e.message}", Toast.LENGTH_LONG).show()
            }
        }
        fun readFromJson(context: Context, jsonFile: String): MutableList<Player>? {
            if (Environment.getExternalStorageState() != Environment.MEDIA_MOUNTED &&
                Environment.getExternalStorageState() != Environment.MEDIA_MOUNTED_READ_ONLY) {
                Toast.makeText(context, "El almacenamiento externo no está disponible para lectura.", Toast.LENGTH_SHORT).show()
                return null
            }

            val downloadsFolder = File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "")
            val archivoJson = File(downloadsFolder, jsonFile)

            if (!archivoJson.exists()) {
                Toast.makeText(context, "El archivo JSON no existe.", Toast.LENGTH_SHORT).show()
                return null
            }

            return try {
                val json = archivoJson.readText()

                val gson = Gson()
                val type = object : TypeToken<Map<String, List<Player>>>() {}.type
                val data = gson.fromJson<Map<String, List<Player>>>(json, type)

                ArrayList(data["PLAYERS"] ?: emptyList())
            } catch (e: IOException) {
                Toast.makeText(context, "Error al leer el archivo: ${e.message}", Toast.LENGTH_LONG).show()
                null
            } catch (e: JsonSyntaxException) {
                Toast.makeText(context, "Error en el formato del JSON: ${e.message}", Toast.LENGTH_LONG).show()
                null
            }
        }
    }
}
