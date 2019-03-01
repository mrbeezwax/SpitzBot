package io.github.mrbeezwax.jermobot.Commands.Activities;

import io.github.mrbeezwax.jermobot.Commands.Command;
import io.github.mrbeezwax.jermobot.Main;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;
import sx.blah.discord.handle.obj.IUser;
import sx.blah.discord.handle.obj.Permissions;

import java.util.List;

public class ResetCrayonLimitCommand implements Command {
    private static final String DESCRIPTION = "Resets crayon limit for trivia";

    @Override
    public void runCommand(MessageReceivedEvent event, List<String> args) {
        if (event.getAuthor().getPermissionsForGuild(event.getGuild()).contains(Permissions.ADMINISTRATOR)) {
            if (args.isEmpty()) {
                Main.playerCrayonLimit.clear();
                event.getChannel().sendMessage("Limit reset");
            }
            else {
                if (event.getMessage().getMentions().size() == 1) {
                    IUser user = event.getMessage().getMentions().get(0);
                    Main.playerCrayonLimit.put(user.getLongID(), 0);
                    event.getChannel().sendMessage("Limit reset for " + user.getDisplayName(event.getGuild()));
                }
            }
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
