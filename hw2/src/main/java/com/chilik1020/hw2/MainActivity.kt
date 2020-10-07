package com.chilik1020.hw2

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.chilik1020.hw2.model.DataSubject
import com.chilik1020.hw2.model.ResultSubject
import com.chilik1020.hw2.model.base.Observer
import com.chilik1020.hw2.model.entity.Message
import com.chilik1020.hw2.model.entity.Result
import com.chilik1020.hw2.util.*
import kotlinx.android.synthetic.main.activity_main.*
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class MainActivity : AppCompatActivity() {

    private val LOG_TAG = "AppTag:MainActivity"
    private val REQUEST_COMPUTATION_CODE = 4201
    private var dataSet: HashSet<Int> = hashSetOf()
    private val executor: ExecutorService = Executors.newSingleThreadExecutor()
    private val resultObserver: Observer<Result> = object : Observer<Result> {
        override fun update(msg: Result) {
            printResultOfComputation(msg)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        ResultSubject.subscribe(resultObserver)

        btnSendDataAsIntent.setOnClickListener {
            dataSet = generateRandomSet()
            startSecondActivity()
        }
        btnSendDataAsObservable.setOnClickListener {
            dataSet = generateRandomSet()
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
            if (data != null) {
                val result: Result? = data.getSerializableExtra(RESULT) as Result?
                if (result != null) {
                    printResultOfComputation(result)
                }
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
        Log.d(LOG_TAG, "Результат деления суммы первой половины на разность второй : ${result.division}")
    }
}
