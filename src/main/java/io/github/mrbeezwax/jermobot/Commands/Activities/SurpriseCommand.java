package io.github.mrbeezwax.jermobot.Commands.Activities;

import io.github.mrbeezwax.jermobot.Commands.Command;
import io.github.mrbeezwax.jermobot.Activities.Surprise;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;
import sx.blah.discord.handle.obj.IGuild;
import sx.blah.discord.handle.obj.IUser;
import sx.blah.discord.handle.obj.Permissions;

import java.util.List;

public class SurpriseCommand implements Command {
    private static final String DESCRIPTION = "Creates a temporary check-in event";
    public static boolean isStarted = false;
    public static Surprise surprise;

    @Override
    public void runCommand(MessageReceivedEvent event, List<String> args) {
        IUser user = event.getAuthor();
        IGuild guild = event.getGuild();
        if (!user.getPermissionsForGuild(guild).contains(Permissions.ADMINISTRATOR)) {
            event.getChannel().sendMessage("User does not have permission");
            return;
        }
        if (args.size() != 3) {
            event.getChannel().sendMessage("Correct syntax: >surprise {keyword} {amount} {minutes}");
            return ;
        }
        if (isStarted) {
            event.getChannel().sendMessage("There is already an ongoing surprise check-in");
            return;
        }
        try {
            surprise = new Surprise(args.get(0), Integer.parseInt(args.get(1)));
            surprise.startTimer(Integer.parseInt(args.get(2)));
            isStarted = true;
            event.getChannel().sendMessage("Surprise Check-In Started with:"
                    + " Keyword = " + args.get(0)
                    + ", Amount = " + args.get(1)
                    + ", Minutes = " + args.get(2));
        }
        catch (NumberFormatException e) {
            event.getChannel().sendMessage("Correct syntax: >surprise {keyword} {amount} {minutes}");
        }
    }

    @Override
    public String getDescription() {
        return DESCRIPTION;
    }

    @Override
    public boolean forAdmin() {
        return true;
    }
}
