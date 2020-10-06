package com.chilik1020.hw2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.chilik1020.hw2.model.IntSubject
import com.chilik1020.hw2.model.Message
import com.chilik1020.hw2.model.Observer
import com.chilik1020.hw2.util.SUBJECT
import kotlinx.android.synthetic.main.activity_observer.*
import java.io.Serializable

class ObserverActivity : AppCompatActivity() {

    private val LOG_TAG = "AppTag:ObserverActivity"

    private val dataset: HashSet<Int> = hashSetOf()
    private lateinit var subject: IntSubject

    private val observer: Observer = object : Observer {
        override fun update(msg: Message) {
            when(msg) {
                is Message.Completed -> processDataSet()
                is Message.Next -> nextValue(msg.value)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_observer)

        intent.extras?.let {
            subject = it.getSerializable(SUBJECT) as IntSubject
            subject.subscribe(observer)
           // tvNextValue.text = "Waiting for data..."
        }
    }

    private fun nextValue(value: Int) {
        Log.d(LOG_TAG, "Next value : $value")
        tvNextValue.text = value.toString()
        dataset.add(value)
    }

    private fun processDataSet() {
        Log.d(LOG_TAG, "Message.Completed's received")
    }

    override fun onDestroy() {
        super.onDestroy()
        subject.unsubscribe(observer)
    }
}