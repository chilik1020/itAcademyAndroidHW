package com.chilik1020.hw2

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.chilik1020.hw2.model.IntSubject
import com.chilik1020.hw2.util.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private val LOG_TAG = "AppTag:MainActivity"

    private val REQUEST_COMPUTATION_CODE = 4201

    private var dataSet: HashSet<Int> = hashSetOf()

    private val subject = IntSubject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnSendDataAsIntent.setOnClickListener {
            dataSet = generateRandomSet()
            startSecondActivity()
        }

        btnSendDataAsObservable.setOnClickListener {
            lifecycleScope.launch {
                subject.startDataTrans()
            }
            startObserverActivity()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_COMPUTATION_CODE) {
            if (data != null) {
                val averageValue = data.getDoubleExtra(AVERAGE,0.0)
                val sum = data.getDoubleExtra(SUM, 0.0)
                val devision = data.getDoubleExtra(DEVISION, 0.0)

                Log.d(LOG_TAG, "Среднее : $averageValue")
                Log.d(LOG_TAG, "Сумма : $sum")
                Log.d(LOG_TAG, "Деление суммы первой половины на разность второй : $devision")
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
        val intent = Intent(this, ObserverActivity::class.java).apply {
            putExtra(SUBJECT, subject)
        }

        startActivityForResult(intent, REQUEST_COMPUTATION_CODE)
    }
}
