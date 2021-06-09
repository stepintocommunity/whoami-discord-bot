package hu.stepintomeetups.whoami

import hu.stepintomeetups.whoami.configuration.StartupConfiguration

fun main(args: Array<String>) {
    val a = StartupConfiguration.fromEnvironmentVariables()
    print(a)
}
