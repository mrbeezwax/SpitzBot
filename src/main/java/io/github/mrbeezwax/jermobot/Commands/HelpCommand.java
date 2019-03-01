package io.github.mrbeezwax.jermobot.Commands;

import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;
import sx.blah.discord.handle.obj.Permissions;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class HelpCommand implements Command {
    private static final String DESCRIPTION = "Displays all commands for the bot";
    private static final Map<String, String> COMMAND_DESCRIPTIONS = CommandHandler.getCommandDescriptions();
    private static final Map<String, String> COMMAND_DESCRIPTIONS_USER = CommandHandler.getCommandDescriptionsUser();

    @Override
    public void runCommand(MessageReceivedEvent event, List<String> args) {
        if (!args.isEmpty()) {
            event.getChannel().sendMessage(COMMAND_DESCRIPTIONS.getOrDefault(args.get(0), "That command does not exist"));
        } else {
            if (event.getAuthor().getPermissionsForGuild(event.getGuild()).contains(Permissions.ADMINISTRATOR))
                event.getAuthor().getOrCreatePMChannel().sendMessage(getFormattedString(true));
            else event.getAuthor().getOrCreatePMChannel().sendMessage(getFormattedString(false));
        }
    }

    @Override
    public String getDescription() {
        return DESCRIPTION;
    }

    @Override
    public boolean forAdmin() {
        return false;
    }

    private String getFormattedString(boolean forAdmin) {
        Set<Map.Entry<String, String>> entrySet;
        String formattedString;
        if (!forAdmin) {
            entrySet = COMMAND_DESCRIPTIONS_USER.entrySet();
            formattedString = "```\nUser Commands for Spitz Bot\n";
        }
        else {
            entrySet = COMMAND_DESCRIPTIONS.entrySet();
            formattedString = "```\nAll Commands for Spitz Bot\n";
        }

        for (Map.Entry<String, String> entry : entrySet) {
            formattedString += "   >" + entry.getKey() + " - " + entry.getValue() + "\n";
        }

        formattedString += "```";
        return formattedString;
    }

}
