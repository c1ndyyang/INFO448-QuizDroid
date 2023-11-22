package edu.uw.ischool.c1ndyy.quizdroid

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.provider.Settings
import android.util.Log
import android.widget.Toast
import androidx.work.CoroutineWorker
import androidx.work.Worker
import androidx.work.WorkerParameters
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.net.HttpURLConnection
import java.net.URL
import okhttp3.OkHttpClient
import okhttp3.Request

class DownloadWorker (appContext: Context, workerParams: WorkerParameters) : Worker(appContext, workerParams) {
    override fun doWork(): Result {
        return try {
            val sharedPrefs = applicationContext.getSharedPreferences("DOWNLOAD_PREFERENCE", Context.MODE_PRIVATE)
            val defaultUrl: String = sharedPrefs.getString("jsonURL", "defaultURL") ?: "defaultURL" //jsonURL may be wrong
            val file = File(applicationContext.filesDir, "questions.json")

            if (!isNetworkOnline()) {
                Toast.makeText(applicationContext, "Please connect to the internet and try again.", Toast.LENGTH_LONG).show();
                return Result.retry()
            }

            //create backup
            Toast.makeText(applicationContext, "Downloading url...", Toast.LENGTH_LONG).show();
            if (file.exists()) {
                file.copyTo(File(file.parentFile, "questions_backup.json"), overwrite = true)
            }

            applicationContext.sendBroadcast(Intent("DOWNLOADING"))

            if (defaultUrl.isNullOrEmpty()) {
                Toast.makeText(applicationContext, "There is no URL to download.", Toast.LENGTH_LONG).show();
                restore()
                Result.retry()
            }

            val client = OkHttpClient()
            val request = Request.Builder().url(defaultUrl).build()
            val clientResponse = client.newCall(request).execute()
            if (!clientResponse.isSuccessful) {
                Toast.makeText(applicationContext, "Could not download file. Please try again.", Toast.LENGTH_LONG).show();
                restore()
                Result.retry()
            }

            Log.d("DownloadWorker", "Downloaded file saved.")
            applicationContext.sendBroadcast(Intent("DATA_UPDATED"))
            Result.success()

        } catch (e: Exception) {
            Log.e("DownloadWorker", "Download error: ${e.message}")
            applicationContext.sendBroadcast(Intent("DOWNLOAD_FAILED"))
            restore()
            Result.failure()
        }
    }

    private fun isNetworkOnline(): Boolean {
        val connectivityManager = applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetworkInfo = connectivityManager.activeNetworkInfo
        return activeNetworkInfo?.isConnected == true
    }

    private fun restore() {
        val backup = File(applicationContext.filesDir, "questions_backup.json")
        if (backup.exists()) {
            backup.copyTo(File(applicationContext.filesDir, "questions.json"), overwrite = true)
        }
    }
}