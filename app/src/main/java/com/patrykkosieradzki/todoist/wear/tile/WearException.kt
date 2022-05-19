package com.patrykkosieradzki.todoist.wear.tile

sealed class WearException(
    override val message: String? = null,
    override val cause: Throwable? = null
) : Exception() {
    class UnknownException(
        override val cause: Throwable? = null
    ) : WearException(
        message = "Unknown exception",
        cause = cause
    )

    class PhoneUnavailableException : WearException(
        message = "Unable to reach mobile device"
    )

    class UnsupportedWatchException : WearException(
        message = "This watch does not support this feature"
    )
}