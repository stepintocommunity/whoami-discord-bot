package hu.stepintomeetups.whoami.introduction.source

/**
 * Provides introduction messages for a given user on a given guild.
 */
interface IntroductionSource {
    /**
     * Retrieves the introduction message contents for the specified context. Returns
     * `null` if there is no introduction message found.
     * @param context The context we are searching in.
     * @return The contents of the introduction message if found, or `null`.
     */
    fun provideIntroductionFor(context: SearchContext): String?

    /**
     * Parameterization of the introduction search.
     * @property userIdentifier The user for whom we want to retrieve the introduction.
     * @property guildIdentifier The guild in which we are searching for the introduction.
     */
    data class SearchContext(
        val userIdentifier: String,
        val guildIdentifier: String,
    )
}
