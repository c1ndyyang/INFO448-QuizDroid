package edu.uw.ischool.c1ndyy.quizdroid

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.BaseAdapter
import android.widget.ListView
import android.widget.TextView
import androidx.appcompat.widget.Toolbar

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val quizView = findViewById<ListView>(R.id.quizView)
        val prefBar = findViewById<Toolbar>(R.id.prefBar)
        setSupportActionBar(prefBar)
        // val names = arrayOf("Math", "Physics", "Marvel Super Heroes")

        val topics = QuizApp.repo.getTopicChoices()
        // val adapter = ArrayAdapter(this,  android.R.layout.simple_list_item_2, topics)

        /*
        val arrayAdapter: ArrayAdapter<String> = ArrayAdapter(
            this, android.R.layout.simple_list_item_1, names
        )
        */

        quizView.adapter = TopicListAdapter(this, topics)
        quizView.setOnItemClickListener{ _, _, position, _ ->
            val selectedItem = topics[position]
            val intent = Intent(this, OverviewActivity::class.java)
            intent.putExtra("quizTopic", position)
            startActivity(intent)
        }

    }

}

class TopicListAdapter(private val context: Context, private val topics: List<QuizApp.Topic>) : BaseAdapter() {
    override fun getCount(): Int {
        return topics.size
    }
    override fun getItem(position: Int): Any {
        return topics[position]
    }
    override fun getItemId(position: Int): Long {
        return position.toLong()
    }
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val topic = getItem(position) as QuizApp.Topic
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = inflater.inflate(R.layout.listview_layout, null)

        val topicTextView = view.findViewById<TextView>(R.id.topicTextView)
        val shortDescTextView = view.findViewById<TextView>(R.id.shortDescTextView)

        topicTextView.text = topic.title
        //shortDescTextView.text = topic.shortDesc

        return view
    }
}




















