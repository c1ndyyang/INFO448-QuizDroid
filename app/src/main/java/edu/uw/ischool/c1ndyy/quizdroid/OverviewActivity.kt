package edu.uw.ischool.c1ndyy.quizdroid

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import org.json.JSONObject

class OverviewActivity : AppCompatActivity() {

    /*
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
     */

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_overview)

        val topic = getIntent().getIntExtra("quizTopic", 0)
        val topicChoice = QuizApp.repo.getTopic(topic)
        val topicOverview = findViewById<TextView>(R.id.quizTopic)
        topicOverview.setText("${topicChoice.title} Quiz Overview")
        var topicName = topicChoice

        val quizOverview = findViewById<TextView>(R.id.quizOverview)
        quizOverview.setText("${topicChoice.longDesc}")

        val numOverview = findViewById<TextView>(R.id.numQuestions)
        numOverview.setText("Number of questions: ${topicChoice.quizzes.size}")

        val beginButton = findViewById<Button>(R.id.beginButton)
        beginButton.setOnClickListener (View.OnClickListener {
            val intent = Intent(this, QuizActivity::class.java)
            intent.putExtra("quizTopic", topic)
            startActivity(intent)
        })
    }
}