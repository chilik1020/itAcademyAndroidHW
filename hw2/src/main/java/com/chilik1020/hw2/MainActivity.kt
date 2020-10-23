package com.chilik1020.hw2

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.chilik1020.hw2.model.DataSubject
import com.chilik1020.hw2.model.ResultSubject
import com.chilik1020.hw2.model.base.Observer
import com.chilik1020.hw2.model.entity.Message
import com.chilik1020.hw2.model.entity.Result
import com.chilik1020.hw2.util.DATASET
import com.chilik1020.hw2.util.RESULT
import com.chilik1020.hw2.util.generateRandomData
import kotlinx.android.synthetic.main.activity_main.*
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class MainActivity : AppCompatActivity() {

    companion object {
        private const val LOG_TAG = "AppTag:MainActivity"
        private const val REQUEST_COMPUTATION_CODE = 4201
    }

    private var dataSet = arrayListOf<Int>()
    private val executor: ExecutorService = Executors.newSingleThreadExecutor()
    private val resultObserver: Observer<Result> =
        Observer { msg -> printResultOfComputation(msg) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        ResultSubject.subscribe(resultObserver)

        btnSendDataAsIntent.setOnClickListener {
            dataSet = generateRandomData()
            startSecondActivity()
        }
        btnSendDataAsObservable.setOnClickListener {
            dataSet = generateRandomData()
            startDataTransmit()
            startObserverActivity()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        ResultSubject.unsubscribe(resultObserver)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_COMPUTATION_CODE) {
            data?.let {
                val result: Result? = data.getSerializableExtra(RESULT) as? Result?
                result?.run { printResultOfComputation(this) }
            }
        }
    }

    private fun startSecondActivity() {
        val intent = Intent(this, SecondActivity::class.java).apply {
            putExtra(DATASET, dataSet)
        }
        startActivityForResult(intent, REQUEST_COMPUTATION_CODE)
    }

    private fun startObserverActivity() {
        val intent = Intent(this, ObserverActivity::class.java)
        startActivity(intent)
    }

    private fun startDataTransmit() {
        executor.execute {
            while (!DataSubject.subscribed) {
                Thread.sleep(100)
            }
            dataSet.forEach {
                DataSubject.notify(Message.Next(it))
                Thread.sleep(30)
            }
            DataSubject.notify(Message.Completed)
        }
    }

    private fun printResultOfComputation(result: Result) {
        Log.d(LOG_TAG, "Среднее арифметическое множества : ${result.average}")
        Log.d(LOG_TAG, "Сумма элементов множества : ${result.sum}")
        Log.d(
            LOG_TAG,
            "Результат деления суммы первой половины на разность второй : ${result.division}"
        )
        runOnUiThread { Toast.makeText(this, "$result", Toast.LENGTH_SHORT).show() }
    }
}
