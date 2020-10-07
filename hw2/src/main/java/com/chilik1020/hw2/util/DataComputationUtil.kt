package com.chilik1020.hw2.util

fun getSumOfSet(data : HashSet<Int>) : Double {
    var sum = 0.0
    data.forEach { sum += it }
    return sum
}

fun getSubtraction(data: HashSet<Int>) : Double {
    val list = data.toList()
    var result : Double  = list[0].toDouble()
    list.subList(1, list.size - 1).forEach {
        result -= it
    }
    return result
}

fun getAverageOfSet(data : HashSet<Int>) : Double {
    if (data.isEmpty())
        return 0.0

    val sum = getSumOfSet(data)
    val size = data.size
    return sum/size
}

fun getDivisionOfSum1HalfAndSubtraction2half(data : HashSet<Int>) : Double {
    if (data.size < 2)
        return 0.0

    val list = data.toList()
    val firstHalfSum =  getSumOfSet(list.subList(0, list.size/2 - 1).toHashSet())
    val secondHalfSubtraction = getSubtraction(list.subList(list.size/2, list.size - 1).toHashSet())

    return firstHalfSum/secondHalfSubtraction
}