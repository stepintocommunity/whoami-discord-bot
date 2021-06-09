package hu.stepintomeetups.whoami.introduction.source

import net.dv8tion.jda.api.JDA

class ReverseSearchingIntroductionSource(private val JDA: JDA) : IntroductionSource {
    override fun provideIntroductionFor(context: IntroductionSource.SearchContext): String? {
        val introductionChannel = JDA.getTextChannelById(context.channelIdentifier) ?: return null

        return introductionChannel.iterableHistory
            .find { it.author.id == context.userIdentifier }
            ?.contentRaw
    }
}
