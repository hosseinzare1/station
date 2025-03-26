package ir.romina.hossein.core.exception

abstract class AppException(
    override val message: String, val description: String
) : Exception(message) {

    override fun toString(): String {
        return "$message. $description"
    }
}

class NetworkException(
    message: String? = null,
    description: String? = null,
) : AppException(message ?: "Network Connection Failed", description ?: "Please try again")


class UnknownException(
    message: String? = null,
    description: String? = null,
) : AppException(message ?: "Unknown Exception", description ?: "Please try again")
