package com.chilik1020.hw2

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.chilik1020.hw2.model.entity.Result
import com.chilik1020.hw2.util.*

class SecondActivity : AppCompatActivity() {
    companion object {
        private const val LOG_TAG = "AppTag:SecondActivity"
    }

    private lateinit var dataSet: ArrayList<Int>
    private var averageValue: Double = 0.0
    private var sum: Int = 0
    private var division: Double = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        intent.extras?.let {
            dataSet = it.getSerializable(DATASET) as ArrayList<Int>

            Log.d(LOG_TAG, "Размер : ${dataSet.size}")
            Log.d(LOG_TAG, "Множество : $dataSet")

            processDataSet()
            returnResultOfComputations()
        }
    }

    private fun processDataSet() {
        averageValue = getAverageOfSet(dataSet)
        sum = getSumOfSet(dataSet)
        division = getDivisionOfSum1HalfAndSubtraction2half(dataSet)
    }

    private fun returnResultOfComputations() {
        val intent = Intent().apply {
            putExtra(RESULT, Result(averageValue, sum, division))
        }
        setResult(Activity.RESULT_OK, intent)
        finish()
    }
}