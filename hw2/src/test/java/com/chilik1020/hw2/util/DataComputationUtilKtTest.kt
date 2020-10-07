package com.chilik1020.hw2.util

import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test

internal class DataComputationUtilKtTest {

    private var dataSet = hashSetOf<Int>()

    @Before
    fun before() {
        for (i in 0..9){
            dataSet.add(i)
        }
    }

    @Test
    fun sumOfSetTest() {
        val expected = 45.0
        val sum = getSumOfSet(dataSet)
        Assert.assertEquals(expected, sum, 0.0)
    }

    @Test
    fun getSubtractionTest()  {

    }

    @Test
    fun getAverageOfSetTest() {
        val expected = 4.5
        val average = getAverageOfSet(dataSet)
        Assert.assertEquals(expected, average, 0.0)
    }

    @After
    fun after() {
        dataSet.clear()
    }
}