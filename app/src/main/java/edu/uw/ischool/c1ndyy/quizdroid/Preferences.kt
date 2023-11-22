package edu.uw.ischool.c1ndyy.quizdroid

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.os.Bundle
import android.provider.Settings
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.appcompat.app.AppCompatActivity
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import java.net.URL
import java.util.concurrent.TimeUnit

class Preferences : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_preferences)

        val urlInput = findViewById<EditText>(R.id.urlInput)
        val minuteInput = findViewById<EditText>(R.id.minuteInput)
        val submitButton = findViewById<Button>(R.id.submitButton)

        val prefBar = findViewById<Toolbar>(R.id.prefActivityBar)
        setSupportActionBar(prefBar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        //hardcode json file
        val downloadPref =  getSharedPreferences("DOWNLOAD_PREFERENCE", MODE_PRIVATE)
        var editor = downloadPref.edit()
        val json = "http://tednewardsandbox.site44.com/questions.json"
        urlInput.setText(downloadPref.getString("jsonURL", json))

        submitButton.setOnClickListener(View.OnClickListener {
            val urlData = urlInput.text.toString()
            var minute = minuteInput.text.toString().toInt()

            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("url", urlData)
            intent.putExtra("downloadMinute", minute)
            editor.commit()
            startActivity(intent)
            Toast.makeText(this, "Preference has been sumbitted.", Toast.LENGTH_LONG).show()

            //check airplane mode
            if (isAirplaneModeOn()) {
                AlertDialog.Builder(this).setTitle("Airplane Mode On").setMessage("Do you want to turn Airplane Mode off?")
                    .setPositiveButton("Turn Off") {_, _ ->
                        val intent = Intent(Settings.ACTION_AIRPLANE_MODE_SETTINGS)
                        startActivity(intent)
                    }
            } else if (!isNetworkOnline()) {
                Toast.makeText(this, "You are currently not connected to internet. Please try again.", Toast.LENGTH_LONG).show()
            } else {
                scheduleDownloadWorker(minute)
            }
        })

    }

    fun scheduleDownloadWorker(interval: Int) {
        val constraints = Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED).build()
        val downloadRequest = PeriodicWorkRequestBuilder<DownloadWorker>(
            interval.toLong(), TimeUnit.MINUTES

        ).setConstraints(constraints).build()

        WorkManager.getInstance(this).enqueueUniquePeriodicWork(
            "downloadWork",
            ExistingPeriodicWorkPolicy.REPLACE,
            downloadRequest
        )
    }

    private fun isNetworkOnline(): Boolean {
        val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetworkInfo = connectivityManager.activeNetworkInfo
        return activeNetworkInfo?.isConnectedOrConnecting == true
    }

    private fun isAirplaneModeOn(): Boolean {
        return Settings.Global.getInt(contentResolver, Settings.Global.AIRPLANE_MODE_ON, 0) != 0
    }

}