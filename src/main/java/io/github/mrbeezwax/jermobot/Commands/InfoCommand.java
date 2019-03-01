package io.github.mrbeezwax.jermobot.Commands;

import io.github.mrbeezwax.jermobot.Main;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;

import java.util.List;

public class InfoCommand implements Command {
    private static final String DESCRIPTION = "Displays info about the bot";

    @Override
    public void runCommand(MessageReceivedEvent event, List<String> args) {
        event.getChannel().sendMessage("Spitz Bot " + Main.VERSION + " coded by FriendlyNood for Jermo936's Discord");
    }

    @Override
    public String getDescription() {
        return DESCRIPTION;
    }

    @Override
    public boolean forAdmin() {
        return false;
    }
}
