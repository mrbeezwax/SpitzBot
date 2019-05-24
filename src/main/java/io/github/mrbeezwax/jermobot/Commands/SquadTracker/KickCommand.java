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

public class KickCommand implements Command {
    private static final String DESCRIPTION = "Kick a member from your squad";

    @Override
    public void runCommand(MessageReceivedEvent event, List<String> args) {
        IChannel channel = event.getChannel();
        IUser user = event.getAuthor();
        // Wrong args
        if (args.size() != 1) {
            channel.sendMessage("Correct syntax: >kick {id}");
            return;
        }
        // Check if leader and user id is valid
        for (Squad s : Main.squadList) {
            SquadMember leader = s.getLeader();
            if (leader.getUser() == user) {
                try {
                    int id = Integer.parseInt(args.get(0));
                    s.kickMember(id);
                    channel.sendMessage("Member kicked from squad, " + s.getTitle());
                } catch (NumberFormatException e) {
                    channel.sendMessage("Enter a valid id number from 2-4");
                } catch (SquadTrackerException e) {
                    channel.sendMessage(e.toString());
                }
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
