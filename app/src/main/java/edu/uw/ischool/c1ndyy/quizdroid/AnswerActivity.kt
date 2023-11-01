package edu.uw.ischool.c1ndyy.quizdroid

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView

class AnswerActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_answer)

        val userAnswer = getIntent().getStringExtra("userAnswer")
        val correctAnswer = getIntent().getStringExtra("correctAnswer")
        val numCorrect = getIntent().getIntExtra("numCorrect", 0)
        val totalQuestions = getIntent().getStringExtra("totalQuestions")
        val questionNumber = getIntent().getIntExtra("questionNumber", 0)
        val topic = getIntent().getStringExtra("topic")

        val youAnswered = findViewById<TextView>(R.id.userAnswer)
        youAnswered.setText(userAnswer)
        val correctAnswerIs = findViewById<TextView>(R.id.correctAnswer)
        correctAnswerIs.setText(correctAnswer)

        val numOutOfNum = findViewById<TextView>(R.id.totalQuestionsCorrect)
        numOutOfNum.setText("You have $numCorrect out of $totalQuestions questions correct.")

        val nextButton = findViewById<Button>(R.id.nextOrFinishButton)

        if ((questionNumber + 1).toString() == totalQuestions) {
            nextButton.setText("Finish")
        }

        nextButton.setOnClickListener (View.OnClickListener {
            if (nextButton.text == "Finish") {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            } else {
                val intent = Intent(this, QuizActivity::class.java)
                intent.putExtra("numCorrect", numCorrect)
                intent.putExtra("questionNumber", questionNumber + 1)
                intent.putExtra("quizTopic", topic)
                startActivity(intent)
            }
        })

    }
}