package io.github.mrbeezwax.jermobot.Commands.SquadTracker;

import io.github.mrbeezwax.jermobot.Commands.Command;
import io.github.mrbeezwax.jermobot.Main;
import io.github.mrbeezwax.jermobot.SquadTracker.Squad;
import io.github.mrbeezwax.jermobot.SquadTracker.SquadMember;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IGuild;
import sx.blah.discord.handle.obj.IUser;

import java.util.List;

public class CreateCommand implements Command {
    private static final String DESCRIPTION = "Create a squad";

    @Override
    public void runCommand(MessageReceivedEvent event, List<String> args) {
        IUser user = event.getAuthor();
        IChannel channel = event.getChannel();

        // Check if title is given
        if (args.size() < 2) {
            channel.sendMessage("Correct syntax: >create {role} {title}");
            return;
        }
        // Check if already in a squad
        SquadMember leader = new SquadMember(0, user, args.get(0));
        for (Squad s : Main.squadList) {
            if (s.getPlayerList().contains(leader)) {
                channel.sendMessage("You are already part of a squad");
                return;
            }
        }
        // Create the squad
        Main.squadList.add(new Squad(leader, args.get(1)));
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
