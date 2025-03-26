package ir.romina.hossein.core.base.domain

import ir.romina.hossein.core.exception.AppException

sealed class OperationResult<T> {
    class Success<T>(val data: T, val message: String? = null) : OperationResult<T>()
    class Failure<T>(
        val exception: AppException,
        val data: T? = null
    ) : OperationResult<T>()
}