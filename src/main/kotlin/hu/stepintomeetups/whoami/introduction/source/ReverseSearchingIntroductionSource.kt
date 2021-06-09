package hu.stepintomeetups.whoami.introduction.source

import net.dv8tion.jda.api.JDA
import org.slf4j.LoggerFactory

class ReverseSearchingIntroductionSource(private val channelMapping: Map<String, String>, private val jda: JDA) : IntroductionSource {
    companion object {
        @JvmStatic
        private val logger = LoggerFactory.getLogger(ReverseSearchingIntroductionSource::class.java)
    }

    override fun provideIntroductionFor(context: IntroductionSource.SearchContext): String? {
        logger.info("Searching for the introduction of ${context.userIdentifier} in guild ${context.guildIdentifier}")

        val introductionChannelIdentifier = channelMapping.get(context.guildIdentifier) ?: return null

        val introductionChannel = jda.getTextChannelById(introductionChannelIdentifier) ?: return null

        return introductionChannel.iterableHistory
            .find { it.author.id == context.userIdentifier }
            ?.contentRaw
    }
}
