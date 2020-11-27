package com.chilik1020.hw8.model.entities

sealed class Result<out T : Any> {
    data class Success<out T : Any>(val data: T) : Result<T>()
    data class Failure(val error: Throwable?) : Result<Nothing>()
}