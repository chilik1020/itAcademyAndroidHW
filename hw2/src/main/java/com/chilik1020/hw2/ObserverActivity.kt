package com.chilik1020.hw2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.chilik1020.hw2.model.DataSubject
import com.chilik1020.hw2.model.ResultSubject
import com.chilik1020.hw2.model.entity.Message
import com.chilik1020.hw2.model.base.Observer
import com.chilik1020.hw2.model.entity.Result
import com.chilik1020.hw2.util.getAverageOfSet
import com.chilik1020.hw2.util.getDivisionOfSum1HalfAndSubtraction2half
import com.chilik1020.hw2.util.getSumOfSet
import kotlinx.android.synthetic.main.activity_observer.*

class ObserverActivity : AppCompatActivity() {

    private val LOG_TAG = "AppTag:ObserverActivity"
    private val dataSet: HashSet<Int> = hashSetOf()
    private var averageValue: Double = 0.0
    private var sum: Double = 0.0
    private var division: Double = 0.0

    private val dataObserver: Observer<Message> = object :Observer<Message> {
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
        DataSubject.subscribe(dataObserver)
    }

    private fun nextValue(value: Int) {
        dataSet.add(value)
        runOnUiThread {tvNextValue.text = value.toString()}
    }

    private fun processDataSet() {
        runOnUiThread {
            tvNextValue.text = "Done"
            pbDataReceiving.visibility = View.GONE
        }
        Log.d(LOG_TAG, "Размер : ${dataSet.size}")
        Log.d(LOG_TAG, "Множество : $dataSet")
        averageValue = getAverageOfSet(dataSet)
        sum = getSumOfSet(dataSet)
        division = getDivisionOfSum1HalfAndSubtraction2half(dataSet)
        ResultSubject.notify(Result(averageValue, sum, division))
        finish()
    }

    override fun onDestroy() {
        super.onDestroy()
        DataSubject.unsubscribe(dataObserver)
    }
}