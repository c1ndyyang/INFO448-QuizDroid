package edu.uw.ischool.c1ndyy.quizdroid

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import org.json.JSONObject

class OverviewActivity : AppCompatActivity() {

    val quizChoice: JSONObject = JSONObject("""{
        "Math": {
            "BriefDescription": "Test your knowledge on addition, subtraction, multiplication, and division with this mathematical quiz.",
            "NumberOfQuestions": "4"
        },
        "Physics": {
            "BriefDescription": "Test your knowledge on physics and see your understanding of motion and behavior through space and time.",
            "NumberOfQuestions": "3"
        },
        "Marvel Super Heroes": {
            "BriefDescription" : "Marvel is know for their American superheroes in the MCU film franchise. Do you know your Marvel Super Heroes?",
            "NumberOfQuestions": "3"
        }
    }""")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_overview)

        val topic = getIntent().getStringExtra("quizTopic")
        val topicOverview = findViewById<TextView>(R.id.quizTopic)
        topicOverview.setText("$topic Quiz Overview")
        var topicName = topic

        val briefDescr = quizChoice.getJSONObject(topic).getString("BriefDescription")
        val quizOverview = findViewById<TextView>(R.id.quizOverview)
        quizOverview.setText(briefDescr)

        val numQuestions = quizChoice.getJSONObject(topic).getString("NumberOfQuestions")
        val numOverview = findViewById<TextView>(R.id.numQuestions)
        numOverview.setText("Number of questions: $numQuestions")

        val beginButton = findViewById<Button>(R.id.beginButton)
        beginButton.setOnClickListener (View.OnClickListener {
            val intent = Intent(this, QuizActivity::class.java)
            intent.putExtra("quizTopic", topicName)
            startActivity(intent)
        })
    }
}