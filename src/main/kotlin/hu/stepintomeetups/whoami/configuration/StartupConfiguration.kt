package hu.stepintomeetups.whoami.configuration

import java.lang.IllegalStateException

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
) {
    companion object {
        const val BOT_TOKEN_VARIABLE = "WHOAMI_BOT_TOKEN"
        const val INTRODUCTION_CHANNEL_IDENTIFIER_VARIABLE = "WHOAMI_INTRODUCTION_CHANNEL_IDENTIFIER"
        const val GUILD_IDENTIFIER_VARIABLE = "WHOAMI_GUILD_IDENTIFIER"

        /**
         * Attempts to construct a new [StartupConfiguration] instance by loading
         * environment variables.
         * @throws IllegalStateException If any of the required environment variables is missing.
         * @return A new [StartupConfiguration] instance.
         */
        fun fromEnvironmentVariables(): StartupConfiguration {
            return StartupConfiguration(
                getEnvOrThrow(BOT_TOKEN_VARIABLE),
                getEnvOrThrow(INTRODUCTION_CHANNEL_IDENTIFIER_VARIABLE),
                getEnvOrThrow(GUILD_IDENTIFIER_VARIABLE),
            )
        }

        private fun getEnvOrThrow(key: String) =
            System.getenv(key) ?: throw IllegalStateException("The following environment variable is missing: $key")
    }
}
