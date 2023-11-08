package edu.uw.ischool.c1ndyy.quizdroid
import android.app.Application
import android.util.Log

class QuizApp : Application() {
    data class Quiz(val question: String, val answers: Array<String>, val correct: Int)
    data class Topic(val title: String, val shortDesc: String, val longDesc: String, var quizzes: List<Quiz>)

    private val topicRepository: TopicRepository = TopicRepo()
    companion object {
        var repo: TopicRepository = TopicRepo()
    }
    override fun onCreate() {
        super.onCreate()
        repo = TopicRepo()
        Log.d("QuizApp", "QuizApp is able to run.")
    }
}

interface TopicRepository {
    fun getTopicChoices(): List<QuizApp.Topic>
    fun getTopic(topic: Int): QuizApp.Topic
    fun getQuiz(topic: Int, quiz: Int): QuizApp.Quiz
}

class TopicRepo : TopicRepository {
    private val topics: List<QuizApp.Topic> = listOf(
        QuizApp.Topic(
            "Math",
            "Math Knowledge Quiz",
            "Test your knowledge on addition, subtraction, multiplication, and division with this mathematical quiz.",
            listOf(
                QuizApp.Quiz("What is 1 + 2?", arrayOf<String>("1", "2", "3", "4"), 2),
                QuizApp.Quiz("What is 10 - 5?", arrayOf<String>("4", "5", "6", "99"), 1),
                QuizApp.Quiz("What is 6 * 6?", arrayOf<String>("100", "6", "12", "36"), 3),
                QuizApp.Quiz("What is 100 / 25?", arrayOf<String>("4", "-1", "0", "1000"), 0)
            )
        ),
        QuizApp.Topic(
            "Physics",
            "Physics Knowledge Quiz",
            "Test your knowledge on physics and see your understanding of motion and behavior through space and time.",
            listOf(
                QuizApp.Quiz(
                    "The first law of thermodynamics is also known by what name?",
                    arrayOf<String>(
                        "conservation of energy",
                        "conservation of mass",
                        "conservation of gravity",
                        "conservation of temperature"
                    ), 0
                ),
                QuizApp.Quiz(
                    "What is the unit of measure for cycles per second?",
                    arrayOf<String>("grams", "pounds", "hertz", "celcius"), 2
                ),
                QuizApp.Quiz(
                    "What is the formula for density?",
                    arrayOf<String>(
                        "d = M * V",
                        "d = M - V",
                        "d = M + V",
                        "d = M / V"
                    ), 3
                )
            )
        ),
        QuizApp.Topic(
            "Marvel Super Heroes",
            "Marvel Super Heroes Knowledge Quiz",
            "Marvel is know for their American superheroes in the MCU film franchise. Do you know your Marvel Super Heroes?",
            listOf(
                QuizApp.Quiz(
                    "Where is Captain America from?",
                    arrayOf<String>("Seattle", "Antarctica", "Brooklyn", "Miami"), 2
                ),
                QuizApp.Quiz(
                    "How many Infinity Stones are there?",
                    arrayOf<String>("1", "6", "10", "100"), 1
                ),
                QuizApp.Quiz(
                    "What’s the name of Thor’s homeworld?",
                    arrayOf<String>("Asgard", "Mars", "The Moon", "Washington"), 0
                )
            )
        )
    )
    override fun getTopicChoices(): List<QuizApp.Topic> {
        return topics
    }
    override fun getTopic(topic: Int): QuizApp.Topic {
        return topics[topic]
    }
    override fun getQuiz(topic: Int, quiz: Int): QuizApp.Quiz {
        return topics[topic].quizzes[quiz]
    }
}