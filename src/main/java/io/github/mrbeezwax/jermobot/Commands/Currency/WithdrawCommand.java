package io.github.mrbeezwax.jermobot.Commands.Currency;

import io.github.mrbeezwax.jermobot.Commands.Command;
import io.github.mrbeezwax.jermobot.Currency.CrayonBank;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IGuild;
import sx.blah.discord.handle.obj.IUser;
import sx.blah.discord.handle.obj.Permissions;

import java.util.List;

public class WithdrawCommand implements Command {
    private static final String DESCRIPTION = "Steals crayons from the user";

    @Override
    public void runCommand(MessageReceivedEvent event, List<String> args) {
        IUser user = event.getAuthor();
        IGuild guild = event.getGuild();
        IChannel channel = event.getChannel();

        if (!user.getPermissionsForGuild(guild).contains(Permissions.ADMINISTRATOR)) {
            channel.sendMessage("User does not have permission");
            return;
        }
        if (args.size() != 2 || event.getMessage().getMentions().size() != 1) {
            channel.sendMessage("Correct syntax: >withdraw {@user} {amount}");
            return;
        }
        IUser recipient = event.getMessage().getMentions().get(0);
        try {
            int withdrawAmt = Integer.parseInt(args.get(1));
            CrayonBank.withdraw(withdrawAmt, recipient);
            channel.sendMessage("You stole **" + withdrawAmt + "** crayons <:crayons:471246763160764426>");
        } catch (NumberFormatException e) {
            channel.sendMessage("Correct syntax: >withdraw {@user} {amount}");
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
