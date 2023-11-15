package edu.uw.ischool.c1ndyy.quizdroid

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.appcompat.app.AppCompatActivity
import java.net.URL

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
        })

    }
}