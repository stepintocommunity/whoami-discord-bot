package hu.stepintomeetups.whoami.bot

import hu.stepintomeetups.whoami.configuration.StartupConfiguration
import hu.stepintomeetups.whoami.introduction.source.IntroductionSource
import net.dv8tion.jda.api.JDA
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter
import org.slf4j.LoggerFactory.getLogger

class Bot(private val configuration: StartupConfiguration, private val introductionSource: IntroductionSource) : ListenerAdapter() {
    companion object {
        @JvmStatic
        private val logger = getLogger(Bot::class.java)
    }

    private val whoisCommand = WhoisCommand(
        introductionSource = introductionSource,
    )

    fun configureJDA(jda: JDA) {
        logger.info("Configuring JDA.")

        jda.upsertCommand(WhoisCommand.COMMAND).queue {
            logger.info("Command ${it.name} upserted successfully.")
        }
    }

    override fun onSlashCommand(event: SlashCommandEvent) {
        logger.debug("Received slash command with name ${event.name}")

        if (!event.isFromGuild) {
            return
        }

        val targetGuild = event.guild
            ?.id
            ?: return

        if (targetGuild != configuration.guildIdentifier) {
            logger.warn("Slash command originates from an unknown guild: $targetGuild")
            return
        }

        when (event.name) {
            WhoisCommand.COMMAND.name -> whoisCommand.handle(event)
            else -> return
        }
    }
}
