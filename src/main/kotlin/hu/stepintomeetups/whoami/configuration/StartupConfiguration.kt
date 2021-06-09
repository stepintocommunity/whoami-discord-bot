package hu.stepintomeetups.whoami.configuration

import java.lang.NumberFormatException

/**
 * Configuration loaded by the bot on startup.
 * @property botToken The token of the bot, assigned by Discord.
 * @property introductionChannelIdentifier The identifier of the channel, where users place their introductions.
 * @property guildIdentifier The identifier of the only guild this bot will ever listen on. Messages from other
 *                           guilds will be rejected.
 */
data class StartupConfiguration(
    val botToken: String,
    val introductionChannelIdentifier: String,
    val guildIdentifier: String,
    val introductionRetentionSeconds: Int,
) {
    companion object {
        const val BOT_TOKEN_VARIABLE = "WHOAMI_BOT_TOKEN"
        const val INTRODUCTION_CHANNEL_IDENTIFIER_VARIABLE = "WHOAMI_INTRODUCTION_CHANNEL_IDENTIFIER"
        const val GUILD_IDENTIFIER_VARIABLE = "WHOAMI_GUILD_IDENTIFIER"
        const val INTRODUCTION_RETENTION_SECONDS_VARIABLE = "WHOAMI_INTRODUCTION_RETENTION_SECONDS"

        /**
         * Attempts to construct a new [StartupConfiguration] instance by loading
         * environment variables.
         * @throws InvalidConfigurationException If any of the required environment variables is missing or is malformed.
         * @return A new [StartupConfiguration] instance.
         */
        fun fromEnvironmentVariables() = StartupConfiguration(
            getEnvOrThrow(BOT_TOKEN_VARIABLE),
            getEnvOrThrow(INTRODUCTION_CHANNEL_IDENTIFIER_VARIABLE),
            getEnvOrThrow(GUILD_IDENTIFIER_VARIABLE),
            ensureGreaterThanZero(INTRODUCTION_RETENTION_SECONDS_VARIABLE, getEnvOrThrow(INTRODUCTION_RETENTION_SECONDS_VARIABLE))
        )

        private fun getEnvOrThrow(key: String) =
            System.getenv(key) ?: throw InvalidConfigurationException("The following environment variable is missing: $key")

        private fun ensureGreaterThanZero(key: String, value: String): Int {
            val intValue: Int
            try {
                intValue = value.toInt()
            } catch (e: NumberFormatException) {
                throw InvalidConfigurationException("$key must be an integer value!")
            }

            if (intValue < 1) {
                throw InvalidConfigurationException("$key must be greater than 0!")
            }

            return intValue
        }
    }
}
