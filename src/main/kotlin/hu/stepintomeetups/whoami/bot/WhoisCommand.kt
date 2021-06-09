package hu.stepintomeetups.whoami.bot

import hu.stepintomeetups.whoami.introduction.source.IntroductionSource
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent
import net.dv8tion.jda.api.interactions.commands.OptionType
import net.dv8tion.jda.api.interactions.commands.build.CommandData
import org.slf4j.LoggerFactory

/**
 * Command handler class performing the logic of the `/whois` command.
 */
class WhoisCommand(private val introductionSource: IntroductionSource) {
    companion object {
        @JvmStatic
        private val logger = LoggerFactory.getLogger(WhoisCommand::class.java)

        const val USER_OPTION_NAME = "user"

        val COMMAND: CommandData = CommandData(
            "whois",
            "Display the introduction of the specified user."
        ).addOption(
            OptionType.USER,
            USER_OPTION_NAME,
            "The name of the user you want to know more about.",
            true
        )

        const val USER_NOT_FOUND_MESSAGE = "User not found on the server. Are you sure that you spelled the username correctly?"
        const val NO_INTRODUCTION_MESSAGE = "This user has not posted an introduction yet :("
    }

    /**
     * Handles a `/whois` command invocation.
     * @param The event corresponding to the command invocation.
     */
    fun handle(event: SlashCommandEvent) {
        logger.debug("Handling /whois command")

        val targetUser = event.options.find { it.name == USER_OPTION_NAME }
            ?.asUser
            ?.id
            ?: return replyTo(event, USER_NOT_FOUND_MESSAGE)

        val introduction = introductionSource.provideIntroductionFor(
            IntroductionSource.SearchContext(
                userIdentifier = targetUser,
                // The Bot class checks if the event is from a guild, so this cast is safe.
                guildIdentifier = event.guild?.id!!
            )
        )

        if (introduction == null) {
            logger.info("Replying with missing introduction for /whois $targetUser")

            replyTo(event, NO_INTRODUCTION_MESSAGE)
        } else {
            logger.info("Replying with actual introduction for /whois $targetUser")

            replyTo(event, introduction)
        }
    }

    private fun replyTo(event: SlashCommandEvent, rawMessage: String) {
        event
            .reply(rawMessage)
            .setEphemeral(true)
            .queue()
    }
}
