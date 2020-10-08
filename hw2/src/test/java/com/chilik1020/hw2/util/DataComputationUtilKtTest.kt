package com.chilik1020.hw2.util

import org.junit.Assert
import org.junit.Test

internal class DataComputationUtilKtTest {

    private val DELTA = 0.000000001

    private var dataSet0 = arrayListOf<Int>()
    private val set0ExpectedSum = 45
    private val set0ExpectedSubstr = -45
    private val set0ExpectedAverage = 4.5
    private val set0ExpectedDivision = -0.4

    private var dataSet1 = arrayListOf<Int>(
        -80, 55, 99, 88, -26, 58, -90, -27, -2, -28, -47, 21,
        -16, 9, 38, -78, -4, 30, -99, -63, 43, 18, 70, -58, -31, 0, 19, -12, -66
    )
    private val set1ExpectedSum = -179
    private val set1ExpectedSubstr = 19
    private val set1ExpectedAverage = -6.172413793103448
    private val set1ExpectedDivision = 0.0520446096654275

    init {
        for (i in 0..9) {
            dataSet0.add(i)
        }
    }

    @Test
    fun sumOfSetTest() {
        Assert.assertEquals(set0ExpectedSum, getSumOfSet(dataSet0))
        Assert.assertEquals(set1ExpectedSum, getSumOfSet(dataSet1))
    }

    @Test
    fun getSubtractionTest() {
        Assert.assertEquals(set0ExpectedSubstr, getSubtraction(dataSet0))
        Assert.assertEquals(set1ExpectedSubstr, getSubtraction(dataSet1))
    }

    @Test
    fun getAverageOfSetTest() {
        Assert.assertEquals(set0ExpectedAverage, getAverageOfSet(dataSet0), DELTA)
        Assert.assertEquals(set1ExpectedAverage, getAverageOfSet(dataSet1), DELTA)
    }

    @Test
    fun getDivisionOfSum1HalfAndSubtraction2halfTest() {
        Assert.assertEquals(
            set0ExpectedDivision,
            getDivisionOfSum1HalfAndSubtraction2half(dataSet0),
            DELTA
        )
        Assert.assertEquals(
            set1ExpectedDivision,
            getDivisionOfSum1HalfAndSubtraction2half(dataSet1),
            DELTA
        )
    }
}