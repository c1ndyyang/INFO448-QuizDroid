package edu.uw.ischool.c1ndyy.quizdroid

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import org.json.JSONObject

class QuizActivity : AppCompatActivity() {

    /*
    val quizQuestions: JSONObject = JSONObject("""{
        "Math": {
            "NumberOfQuestions": "4",
            "QuestionsList": [
                { "Question": "What is 1 + 2?",
                  "Answer": "3",
                  "AnswerOptions": ["1", "2", "3", "4"] },
        
                { "Question": "What is 10-5?",
                  "Answer": "5",
                  "AnswerOptions": ["4", "5", "6", "99"] },
        
                { "Question": "What is 6 * 6?",
                  "Answer": "36",
                  "AnswerOptions": ["100", "6", "12", "36"] },
        
                { "Question": "What is 100 / 25?",
                  "Answer": "4",
                  "AnswerOptions": ["4", "-1", "0", "1000"] }
            ]
        },
        
        "Physics": {
            "NumberOfQuestions": "3",
            "QuestionsList": [
                { "Question": "The first law of thermodynamics is also known by what name?",
                  "Answer": "conservation of energy",
                  "AnswerOptions": [
                        "conservation of energy", 
                        "conservation of mass", 
                        "conservation of gravity",
                        "conservation of temperature" ] },
        
                { "Question": "What is the unit of measure for cycles per second?",
                  "Answer": "hertz",
                  "AnswerOptions": [
                        "grams", 
                        "pounds", 
                        "hertz",
                        "celcius" ] },
        
                { "Question": "What is the formula for density?",
                  "Answer": "d = M / V",
                  "AnswerOptions": [
                        "d = M * V", 
                        "d = M - V", 
                        "d = M + V",
                        "d = M / V" ] }
            ]
        },
        "Marvel Super Heroes": {
            "NumberOfQuestions": "3",
            "QuestionsList": [
                { "Question": "Where is Captain America from?",
                  "Answer": "Brooklyn",
                  "AnswerOptions": [
                        "Seattle", 
                        "Antarctica", 
                        "Brooklyn",
                        "Miami" ] },
        
                { "Question": "How many Infinity Stones are there?",
                  "Answer": "6",
                  "AnswerOptions": [ "1", "6", "10", "100" ] },
        
                { "Question": "What’s the name of Thor’s homeworld?",
                  "Answer": "Asgard",
                  "AnswerOptions": [
                        "Asgard", 
                        "Mars", 
                        "The Moon",
                        "Washington" ] }
            ]
        }
    }""")
     */

    var userAnswer: String = ""
    var userCorrect: Int = 0
    var isCorrect: Boolean = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz)

        val topic = getIntent().getIntExtra("quizTopic", 0)
        val topicChoice = QuizApp.repo.getTopic(topic)

        var questionNum = getIntent().getIntExtra("questionNumber", 0)
        userCorrect = getIntent().getIntExtra("numCorrect", 0)

        //render question number
        val numberOfQuestion = findViewById<TextView>(R.id.questionNum)
        numberOfQuestion.setText("Question " + (questionNum + 1))

        //render question
        val quizQuestion = findViewById<TextView>(R.id.quizQuestion)
        val question = QuizApp.repo.getQuiz(topic, questionNum).text
        quizQuestion.setText("$question")

        var submitButton = findViewById<Button>(R.id.submitButton)

        //render options
        val option1 = findViewById<RadioButton>(R.id.oneOption)
        option1.setText(QuizApp.repo.getQuiz(topic, questionNum).answers[0])
        val option2 = findViewById<RadioButton>(R.id.twoOption)
        option2.setText(QuizApp.repo.getQuiz(topic, questionNum).answers[1])
        val option3 = findViewById<RadioButton>(R.id.threeOption)
        option3.setText(QuizApp.repo.getQuiz(topic, questionNum).answers[2])
        val option4 = findViewById<RadioButton>(R.id.fourOption)
        option4.setText(QuizApp.repo.getQuiz(topic, questionNum).answers[3])

        val radioGroup = findViewById<RadioGroup>(R.id.radioGroup)
        radioGroup.setOnCheckedChangeListener { group, checkedId ->
            userAnswer = when (checkedId) {
                R.id.oneOption -> option1.text.toString()
                R.id.twoOption -> option2.text.toString()
                R.id.threeOption -> option3.text.toString()
                R.id.fourOption -> option4.text.toString()
                else -> ""
            }
            if (userAnswer != "") {
                submitButton.isEnabled = true
            }
        }

        submitButton.setOnClickListener (View.OnClickListener {
            val correctAnswer = QuizApp.repo.getQuiz(topic, questionNum).answer
            val totalQuestions = topicChoice.questions.size


            if (userAnswer == QuizApp.repo.getQuiz(topic, questionNum).answers[correctAnswer]) {
                isCorrect = true
                userCorrect = userCorrect + 1
            }
            val intent = Intent(this, AnswerActivity::class.java)
            intent.putExtra("userAnswer", userAnswer)
            intent.putExtra("correctAnswer", correctAnswer)
            intent.putExtra("numCorrect", userCorrect)
            intent.putExtra("totalQuestions", totalQuestions)
            intent.putExtra("questionNumber", questionNum)
            intent.putExtra("quizTopic", topic)
            startActivity(intent)
        })
    }
}