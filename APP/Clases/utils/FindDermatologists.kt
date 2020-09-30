package com.example.skinscanner.utils

import android.content.Context
import android.location.Location
import android.os.AsyncTask
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import androidx.core.widget.ContentLoadingProgressBar
import com.example.skinscanner.model.dermatologist.DermatologistRecord
import com.example.skinscanner.model.dermatologist.gson.Response
import com.example.skinscanner.model.dermatologist.gson.Result
import com.google.gson.Gson
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

class FindDermatologists(val context: Context,
                         private val progressBar: ContentLoadingProgressBar):
    AsyncTask<Void, Void, List<DermatologistRecord>>() {

    private val keyword = "Clinica dermatologica"
    private val apiKey = "AIzaSyA6FKaG5J0G901RgXOHLxFvP97DD8e8_84"
    private val type = "doctor"
    private val rankBy = "distance"
    private lateinit var url: URL
    private val wrongKeywords = arrayOf("hair", "rehabilitación", "estética", "peluquería", "dental",
        "dentista", "dermolaser", "laser", "dermoshop", "policlinico", "podólogo")
    private lateinit var dermatologistsArray: ArrayList<DermatologistRecord>

    private val location: Location = Gps(context).requestLocation()

    override fun doInBackground(vararg params: Void?): List<DermatologistRecord> {
        dermatologistsArray = ArrayList()

        try {
            url = URL(
                "https://maps.googleapis.com/maps/api/place/nearbysearch/json?"
                        + "location=${location.latitude},${location.longitude}"
                        + "&rankby=$rankBy"
                        + "&type=$type"
                        + "&keyword=$keyword"
                        + "&key=$apiKey"
            )

            val urlConnection: HttpURLConnection = url.openConnection() as HttpURLConnection
            urlConnection.requestMethod = "POST"
            urlConnection.setRequestProperty("Content-Type", "application/json; utf-8")
            urlConnection.setRequestProperty("Accept", "application/json")
            urlConnection.doOutput = true
            urlConnection.connectTimeout = 4000

            val stream: InputStream = urlConnection.content as InputStream
            val bufferedReader: BufferedReader = BufferedReader(
                InputStreamReader(
                    stream,
                    "utf-8"
                ), 8
            )
            val stringBuilder = StringBuilder()

            BufferedReader(bufferedReader).use { r ->
                r.lineSequence().forEach {
                    stringBuilder.append(it + "\n")
                }
            }
            stream.close()
            bufferedReader.close()

            val jsonObject: JSONObject = JSONObject(stringBuilder.toString())
            val response: Response =
                Gson().fromJson(jsonObject.toString(), Response::class.java)

            for (result in response.results) {
                if (checkWrongKeywords(result)) {
                    val dermatologistRecord = DermatologistRecord(
                        0,
                        result.name,
                        result.vicinity,
                        result.rating,
                        result.geometry.location.lat,
                        result.geometry.location.lng
                    )
                    dermatologistsArray.add(dermatologistRecord)
                }
            }
        } catch (ex: Exception) {
            return emptyList()
        }
        return dermatologistsArray
    }

    override fun onPreExecute() {
        super.onPreExecute()
        progressBar.show()
    }

    override fun onPostExecute(result: List<DermatologistRecord>?) {
        super.onPostExecute(result)
        progressBar.hide()
    }

    private fun checkWrongKeywords(result: Result): Boolean {
        for (str in wrongKeywords) {
            if (result.name.toLowerCase().contains(str)) return false
        }
        return true
    }
}