package io.github.mrbeezwax.jermobot.Commands.Currency;

import io.github.mrbeezwax.jermobot.Commands.Command;
import io.github.mrbeezwax.jermobot.Currency.CrayonBank;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IUser;

import java.util.List;

public class BalanceCommand implements Command {
    private static final String DESCRIPTION = "Shows your balance";

    @Override
    public void runCommand(MessageReceivedEvent event, List<String> args) {
        IChannel crayonCheckIn = event.getChannel();
        IChannel channel = event.getChannel();
        IUser user = event.getAuthor();

        if (channel != crayonCheckIn) {
            channel.sendMessage("Please type in " + crayonCheckIn.mention());
            return;
        }
        channel.sendMessage("You have **" + CrayonBank.getBalance(user) + "** crayons <:crayons:471246763160764426>");
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
