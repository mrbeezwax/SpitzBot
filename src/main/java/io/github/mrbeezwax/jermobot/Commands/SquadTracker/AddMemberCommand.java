package io.github.mrbeezwax.jermobot.Commands.SquadTracker;

import io.github.mrbeezwax.jermobot.Commands.Command;
import io.github.mrbeezwax.jermobot.Main;
import io.github.mrbeezwax.jermobot.SquadTracker.Squad;
import io.github.mrbeezwax.jermobot.SquadTracker.SquadMember;
import io.github.mrbeezwax.jermobot.SquadTracker.SquadTrackerException;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IUser;

import java.util.List;

public class AddMemberCommand implements Command {
    private static final String DESCRIPTION = "Add a member to your squad";

    @Override
    public void runCommand(MessageReceivedEvent event, List<String> args) {
        IChannel channel = event.getChannel();
        IUser leader = event.getAuthor();
        if (args.size() != 2 || event.getMessage().getMentions().size() != 1) {
            channel.sendMessage("Correct syntax: >addmember {@name} {role}");
            return;
        }
        // Get the user to add
        IUser member = event.getMessage().getMentions().get(0);
        SquadMember sMember = new SquadMember(member, args.get(1));
        // Get squad
        for (Squad s : Main.squadList) {
            // Check if leader
            if (s.getLeader().getUser() == leader) {
                // If leader, check if not part of any squad
                for (Squad s2 : Main.squadList) {
                    if (s2.getPlayerList().contains(sMember)) {
                        channel.sendMessage("They are already in a squad");
                        return;
                    }
                }
                try {
                    s.addMember(sMember);
                    channel.sendMessage("Squad member added successfully");
                    return;
                } catch (SquadTrackerException e) {
                    channel.sendMessage(e.toString());
                }
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
