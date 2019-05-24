package io.github.mrbeezwax.jermobot.Commands.SquadTracker;

import io.github.mrbeezwax.jermobot.Commands.Command;
import io.github.mrbeezwax.jermobot.Main;
import io.github.mrbeezwax.jermobot.SquadTracker.Squad;
import io.github.mrbeezwax.jermobot.SquadTracker.SquadMember;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IUser;

import java.util.List;

public class DisbandCommand implements Command {
    private static final String DESCRIPTION = "Disband your squad";

    @Override
    public void runCommand(MessageReceivedEvent event, List<String> args) {
        IChannel channel = event.getChannel();
        IUser user = event.getAuthor();
        for (Squad s : Main.squadList) {
            SquadMember leader = s.getLeader();
            if (leader.getUser() == user) {
                Main.squadList.remove(s);
                return;
            }
        }
        channel.sendMessage("You are not a leader of any squad");
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
