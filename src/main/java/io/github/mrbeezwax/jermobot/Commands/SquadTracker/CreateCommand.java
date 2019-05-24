package io.github.mrbeezwax.jermobot.Commands.SquadTracker;

import io.github.mrbeezwax.jermobot.Commands.Command;
import io.github.mrbeezwax.jermobot.Main;
import io.github.mrbeezwax.jermobot.SquadTracker.Squad;
import io.github.mrbeezwax.jermobot.SquadTracker.SquadMember;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;
import sx.blah.discord.handle.obj.IChannel;
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
        SquadMember leader = new SquadMember(user, args.get(0));
        for (Squad s : Main.squadList) {
            if (s.getPlayerList().contains(leader)) {
                channel.sendMessage("You are already part of a squad");
                return;
            }
        }
        // Create the squad
        StringBuilder builder = new StringBuilder();
        for (String p : args.subList(1, args.size())) {
            builder.append(p).append(" ");
        }
        String title = builder.toString();
        title = title.substring(0, title.length() - 1);
        Main.squadList.add(new Squad(leader, title));
        channel.sendMessage("Squad " + title + " created successfully");
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
