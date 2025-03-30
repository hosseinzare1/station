package ir.romina.hossein.core.exception

import java.net.SocketException

//TODO handle all exceptions base on api docs
fun Throwable.toAppException(): AppException {
    return when (this) {
        is SocketException -> NetworkException()
        is AppException -> this
        else -> UnknownException()
    }
}