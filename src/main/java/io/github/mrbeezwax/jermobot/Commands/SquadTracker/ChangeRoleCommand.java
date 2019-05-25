package io.github.mrbeezwax.jermobot.Commands.SquadTracker;

import io.github.mrbeezwax.jermobot.Commands.Command;
import io.github.mrbeezwax.jermobot.Main;
import io.github.mrbeezwax.jermobot.SquadTracker.Squad;
import io.github.mrbeezwax.jermobot.SquadTracker.SquadMember;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IUser;

import java.util.List;

public class ChangeRoleCommand implements Command {
    private static final String DESCRIPTION = "Change your role";

    @Override
    public void runCommand(MessageReceivedEvent event, List<String> args) {
        IChannel channel = event.getChannel();
        IUser user = event.getAuthor();
        if (args.size() != 1) {
            channel.sendMessage("Correct syntax: >changerole {role}");
            return;
        }
        for (Squad s : Main.squadList) {
            for (int i = 0; i < s.getPlayerList().size(); i++) {
                if (s.getPlayerList().get(i).getUser() == user) {
                    s.getPlayerList().get(i).changeRole(args.get(0));
                    channel.sendMessage("Role changed successfully");
                    return;
                }
            }
        }
        channel.sendMessage("You are not in any squad");
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
