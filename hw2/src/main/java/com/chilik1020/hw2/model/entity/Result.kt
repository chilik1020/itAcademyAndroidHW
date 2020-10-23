package com.chilik1020.hw2.model.entity

import java.io.Serializable

data class Result(
    val average: Double,
    val sum: Int,
    val division: Double
) : Serializable