package hu.stepintomeetups.whoami.introduction.source

import java.util.Optional

interface IntroductionSource {
    fun provideIntroductionFor(userIdentifier: String): Optional<String>
}
