package io.github.mrbeezwax.jermobot.Commands.Currency;

import io.github.mrbeezwax.jermobot.Commands.Command;
import io.github.mrbeezwax.jermobot.Currency.CrayonBank;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;
import sx.blah.discord.handle.obj.Permissions;

import java.util.List;

public class AllBalancesCommand implements Command {
    private static final String DESCRIPTION = "Displays balance of all users";

    @Override
    public void runCommand(MessageReceivedEvent event, List<String> args) {
        if (!event.getAuthor().getPermissionsForGuild(event.getGuild()).contains(Permissions.ADMINISTRATOR)) {
            event.getChannel().sendMessage("User does not have permission");
            return;
        }
        if (args.size() > 0) {
            event.getChannel().sendMessage("Correct syntax: >allbalances");
            return;
        }
        event.getChannel().sendMessage(CrayonBank.getAllBalances(event.getGuild()));
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
