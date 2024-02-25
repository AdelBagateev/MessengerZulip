package com.example.common

import kotlinx.coroutines.*

fun catchCancellationException(exception: Throwable) {
    if (exception is CancellationException) throw exception
}

suspend fun <T1, T2, R> asyncAwaitAndTransform(
    s1: suspend CoroutineScope.() -> T1,
    s2: suspend CoroutineScope.() -> T2,
    transform: suspend (T1, T2) -> R
): R {
    return coroutineScope {
        val result1 = async(block = s1)
        val result2 = async(block = s2)

        transform(
            result1.await(),
            result2.await(),
        )
    }
}

suspend fun asyncAwaitWithoutTransform(
    s1: suspend CoroutineScope.() -> Unit,
    s2: suspend CoroutineScope.() -> Unit,
) {
    return coroutineScope {
        launch(block = s1)
        launch(block = s2)
    }
}
