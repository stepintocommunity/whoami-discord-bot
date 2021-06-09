package hu.stepintomeetups.whoami.bot

import hu.stepintomeetups.whoami.configuration.StartupConfiguration
import hu.stepintomeetups.whoami.introduction.source.IntroductionSource
import net.dv8tion.jda.api.JDA
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent
import net.dv8tion.jda.api.events.message.GenericMessageEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter
import net.dv8tion.jda.api.interactions.commands.OptionType
import net.dv8tion.jda.api.interactions.commands.build.CommandData

class Bot(private val configuration: StartupConfiguration, private val introductionSource: IntroductionSource) : ListenerAdapter() {
    companion object {
        const val USER_OPTION_NAME = "user"

        val WHOIS_COMMAND: CommandData = CommandData(
            "whois",
            "Display the introduction of the specified user."
        ).addOption(
            OptionType.USER,
            USER_OPTION_NAME,
            "The name of the user you want to know more about.",
            true
        )
    }

    fun configureJDA(jda: JDA) {
        jda.upsertCommand(WHOIS_COMMAND).queue()
    }

    override fun onSlashCommand(event: SlashCommandEvent) {
        if (event.name != WHOIS_COMMAND.name || !event.isFromGuild) {
            return
        }

        val targetGuild = event.guild
            ?.id
            ?: return

        if (targetGuild != configuration.guildIdentifier) {
            return
        }

        val targetUser = event.options.find { it.name == USER_OPTION_NAME }
            ?.asUser
            ?.id

        if (targetUser == null) {
            event
                .reply("User not found on the server. Are you sure that you spelled the username correctly?")
                .setEphemeral(true)
                .queue()

            return
        }

        val introduction = introductionSource.provideIntroductionFor(
            IntroductionSource.SearchContext(
                userIdentifier = targetUser,
                channelIdentifier = configuration.introductionChannelIdentifier
            )
        )

        if (introduction == null) {
            event
                .reply("This user has not posted an introduction yet :(")
                .setEphemeral(true)
                .queue()
        } else {
            event
                .reply(introduction)
                .setEphemeral(true)
                .queue()
        }
    }
}