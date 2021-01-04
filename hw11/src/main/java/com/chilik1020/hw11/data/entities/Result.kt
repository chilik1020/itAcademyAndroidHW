package com.chilik1020.hw11.data.entities

sealed class Result<out T : Any> {
    data class Success<out T : Any>(val data: T) : Result<T>()
    data class Failure(val error: Throwable?) : Result<Nothing>()
}