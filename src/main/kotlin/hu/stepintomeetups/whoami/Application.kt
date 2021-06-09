package hu.stepintomeetups.whoami

import hu.stepintomeetups.whoami.bot.Bot
import hu.stepintomeetups.whoami.configuration.StartupConfiguration
import hu.stepintomeetups.whoami.introduction.source.ReverseSearchingIntroductionSource
import net.dv8tion.jda.api.JDABuilder

fun main(args: Array<String>) {
    val configuration = StartupConfiguration.fromEnvironmentVariables()
    val jda = JDABuilder.createLight(configuration.botToken)
        .build()

    val bot = Bot(
        configuration = configuration,
        introductionSource = ReverseSearchingIntroductionSource(jda)
    )

    jda.addEventListener(bot)

    bot.configureJDA(jda)
}
