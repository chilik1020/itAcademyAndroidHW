package com.chilik1020.hw2

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.chilik1020.hw2.util.*
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val LOG_TAG = "AppTag:MainActivity"

    private val REQUEST_COMPUTATION_CODE = 4201

    private var dataSet: HashSet<Int> = hashSetOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnStartSecondActivity.setOnClickListener {
            dataSet = generateRandomSet()
            startSecondActivity()
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
}
