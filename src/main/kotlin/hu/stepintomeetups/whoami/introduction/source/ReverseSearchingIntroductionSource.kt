package hu.stepintomeetups.whoami.introduction.source

import net.dv8tion.jda.api.JDA
import org.slf4j.LoggerFactory

/**
 * Introduction search implemented as a reverse search (present to past) through the messages in a designated
 * introduction channel.
 * @param channelMapping Maps introduction channel identifiers to guild identifiers. Designates the introduction
 *                       channel of a given guild.
 * @param jda The JDA instance we can use for querying messages.
 */
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
