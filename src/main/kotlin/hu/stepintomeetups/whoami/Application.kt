package hu.stepintomeetups.whoami

import hu.stepintomeetups.whoami.bot.Bot
import hu.stepintomeetups.whoami.configuration.StartupConfiguration
import hu.stepintomeetups.whoami.introduction.source.ReverseSearchingIntroductionSource
import net.dv8tion.jda.api.JDABuilder

fun main() {
    val configuration = StartupConfiguration.fromEnvironmentVariables()
    val jda = JDABuilder.createLight(configuration.botToken)
        .build()

    val introductionSource = ReverseSearchingIntroductionSource(
        channelMapping = mapOf(
            configuration.guildIdentifier to configuration.introductionChannelIdentifier
        ),
        jda = jda
    )

    val bot = Bot(
        configuration = configuration,
        introductionSource = introductionSource
    )

    jda.addEventListener(bot)

    bot.configureJDA(jda)
}
