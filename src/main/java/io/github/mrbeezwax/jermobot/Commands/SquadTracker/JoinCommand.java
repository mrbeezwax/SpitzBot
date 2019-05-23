package io.github.mrbeezwax.jermobot.Commands.SquadTracker;

import io.github.mrbeezwax.jermobot.Commands.Command;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;

import java.util.List;

public class JoinCommand implements Command {
    private static final String DESCRIPTION = "Join an existing squad";

    @Override
    public void runCommand(MessageReceivedEvent event, List<String> args) {

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
