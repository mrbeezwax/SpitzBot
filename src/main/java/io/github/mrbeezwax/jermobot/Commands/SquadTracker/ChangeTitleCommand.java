package io.github.mrbeezwax.jermobot.Commands.SquadTracker;

import io.github.mrbeezwax.jermobot.Commands.Command;
import io.github.mrbeezwax.jermobot.Main;
import io.github.mrbeezwax.jermobot.SquadTracker.Squad;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IUser;

import java.util.List;

public class ChangeTitleCommand implements Command {
    private static final String DESCRIPTION = "Change your squad's title";

    @Override
    public void runCommand(MessageReceivedEvent event, List<String> args) {
        IChannel channel = event.getChannel();
        IUser user = event.getAuthor();
        if (args.size() < 1) {
            channel.sendMessage("Correct syntax: >changetitle {title}");
            return;
        }
        for (Squad s : Main.squadList) {
            if (s.getLeader().getUser() == user) {
                StringBuilder builder = new StringBuilder();
                for (String p : args.subList(1, args.size())) {
                    builder.append(p).append(" ");
                }
                String title = builder.toString();
                title = title.substring(0, title.length() - 1);
                s.setTitle(title);
                channel.sendMessage("Title changed successfully");
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
