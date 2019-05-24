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

public class AddCommand implements Command {
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
        // Get squad
        Squad squad = null;
        for (Squad s : Main.squadList) {
            if (s.getLeader().getUser() == leader) {
                squad = s;
                break;
            }
        }
        // If not a leader, return
        if (squad == null) {
            channel.sendMessage("You are not a leader of any squad");
            return;
        }
        // If leader, add the member to squad
        try {
            SquadMember sMember = new SquadMember(squad.getSize() + 1, member, args.get(1));
            squad.addMember(sMember);
        } catch (SquadTrackerException s) {
            channel.sendMessage(s.toString());
        }
    }

    @Override
    public String getDescription() {
        return null;
    }

    @Override
    public boolean forAdmin() {
        return false;
    }
}
