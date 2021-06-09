package hu.stepintomeetups.whoami.introduction.source

import net.dv8tion.jda.api.JDA
import org.slf4j.LoggerFactory

class ReverseSearchingIntroductionSource(private val JDA: JDA) : IntroductionSource {
    companion object {
        @JvmStatic
        private val logger = LoggerFactory.getLogger(ReverseSearchingIntroductionSource::class.java)
    }

    override fun provideIntroductionFor(context: IntroductionSource.SearchContext): String? {
        logger.info("Searching for the introduction of ${context.userIdentifier} in ${context.channelIdentifier}")

        val introductionChannel = JDA.getTextChannelById(context.channelIdentifier) ?: return null

        return introductionChannel.iterableHistory
            .find { it.author.id == context.userIdentifier }
            ?.contentRaw
    }
}
