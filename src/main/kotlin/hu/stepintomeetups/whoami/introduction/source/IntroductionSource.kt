package hu.stepintomeetups.whoami.introduction.source

interface IntroductionSource {
    fun provideIntroductionFor(context: SearchContext): String?

    data class SearchContext(
        val userIdentifier: String,
        val channelIdentifier: String,
    )
}
