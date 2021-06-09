package hu.stepintomeetups.whoami.configuration

import java.lang.RuntimeException

/**
 * Exception thrown when the bot is supplied erroneous/malformed configuration values.
 * @param message A human-readable message attached to the exception.
 */
class InvalidConfigurationException(message: String) : RuntimeException(message)
