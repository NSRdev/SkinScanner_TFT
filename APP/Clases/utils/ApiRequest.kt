package com.example.skinscanner.utils

import android.os.AsyncTask
import org.apache.http.HttpResponse
import org.apache.http.client.methods.HttpPost
import org.apache.http.entity.mime.HttpMultipartMode
import org.apache.http.entity.mime.MultipartEntityBuilder
import org.apache.http.entity.mime.content.FileBody
import org.apache.http.impl.client.DefaultHttpClient
import org.apache.http.params.BasicHttpParams
import org.apache.http.params.HttpConnectionParams
import org.apache.http.protocol.BasicHttpContext
import org.apache.http.util.EntityUtils
import java.io.File


class ApiRequest: AsyncTask<String, Void, String>() {

    private val serverIp = "34.71.83.35"

    override fun doInBackground(vararg params: String): String {
        val imagePath = params[0]
        println(imagePath)
        var httpPost = HttpPost("http://$serverIp:5000/predict")

        val builder = MultipartEntityBuilder.create()
        builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE)

        val fileBody = FileBody(File(imagePath))
        builder.addPart("image", fileBody)

        val httpEntity = builder.build()
        httpPost.entity = httpEntity

        val httpParams = BasicHttpParams()
        HttpConnectionParams.setConnectionTimeout(httpParams, 3000)

        return try {
            val httpResponse: HttpResponse =
                DefaultHttpClient(httpParams).execute(httpPost, BasicHttpContext())
            return EntityUtils.toString(httpResponse.entity)
        } catch (e: Exception) {
            return ""
        }
    }
}

