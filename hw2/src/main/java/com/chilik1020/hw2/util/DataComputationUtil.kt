package com.chilik1020.hw2.util

fun getSumOfSet(data : List<Int>) = data.sum()

fun getSubtraction(data: List<Int>) : Int {
    if (data.isEmpty()) {
        return 0
    }
    var result  = data[0]
    data.subList(1, data.size).forEach {
        result -= it
    }
    return result
}

fun getAverageOfSet(data : List<Int>) : Double {
    if (data.isEmpty()) {
        return 0.0
    }
    val sum = getSumOfSet(data)
    return sum.toDouble()/data.size
}

fun getDivisionOfSum1HalfAndSubtraction2half(data : List<Int>) : Double {
    if (data.size < 2) {
        return 0.0
    }
    val size = data.size
    val firstHalfSum =  getSumOfSet(data.subList(0, size/2))
    val secondHalfSubtraction = getSubtraction(data.subList(size/2, size))
    return firstHalfSum.toDouble()/secondHalfSubtraction.toDouble()
}