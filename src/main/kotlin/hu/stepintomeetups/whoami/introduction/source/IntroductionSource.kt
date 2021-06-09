package hu.stepintomeetups.whoami.introduction.source

/**
 * Provides introduction strings for
 */
interface IntroductionSource {
    /**
     *
     */
    fun provideIntroductionFor(context: SearchContext): String?

    /**
     *
     */
    data class SearchContext(
        val userIdentifier: String,
        val guildIdentifier: String,
    )
}
