package jp.matsuura.utility

sealed class Result<out V, out E> {
    data class Success<out V>(val value: V) : Result<V, Nothing>()
    data class Failure<out E>(val error: E) : Result<Nothing, E>()
}