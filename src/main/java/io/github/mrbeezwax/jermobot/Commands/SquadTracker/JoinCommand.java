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

public class JoinCommand implements Command {
    private static final String DESCRIPTION = "Join an existing squad";

    @Override
    public void runCommand(MessageReceivedEvent event, List<String> args) {
        IChannel channel = event.getChannel();
        IUser user = event.getAuthor();
        // Check if args is valid
        if (args.size() != 2) {
            channel.sendMessage("Correct syntax: >join {squad id} {role}");
            return;
        }
        try {
            // Check if squad id is valid
            int id = Integer.parseInt(args.get(0));
            if (id > Main.squadList.size() || id <= 0) {
                channel.sendMessage("Please enter a valid squad id");
                return;
            }
            // Check if not in squad
            SquadMember member = new SquadMember(user, args.get(1));
            for (Squad s : Main.squadList) {
                if (s.getPlayerList().contains(member)) {
                    channel.sendMessage("You are already in a squad. Type >leave to leave your current squad");
                    return;
                }
            }
            Squad squad = Main.squadList.get(id - 1);
            squad.addMember(member);
            channel.sendMessage("You have successfully joined the squad, " + squad.getTitle());
        } catch (NumberFormatException e) {
            channel.sendMessage("Please enter a valid squad id number");
        } catch (SquadTrackerException e) {
            channel.sendMessage(e.toString());
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
}
