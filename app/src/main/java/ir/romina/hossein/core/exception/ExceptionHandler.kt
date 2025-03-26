package ir.romina.hossein.core.exception

import java.net.SocketException

//TODO handle more exceptions
fun Exception.toAppException(): AppException {
    return when (this) {
        is SocketException -> NetworkException()
        is AppException -> this
        else -> UnknownException()
    }
}