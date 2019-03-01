package io.github.mrbeezwax.jermobot.Commands.Currency;

import io.github.mrbeezwax.jermobot.Commands.Command;
import io.github.mrbeezwax.jermobot.Data.SpreadsheetReader;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;
import sx.blah.discord.handle.obj.IChannel;

import java.util.List;

public class BuyCommand implements Command {
    private static final String DESCRIPTION = "Purchase a mod from the crayon catalog";

    @Override
    public void runCommand(MessageReceivedEvent event, List<String> args) {
        IChannel channel = event.getChannel();
        if (channel != event.getGuild().getChannelByID(541724855427661825L)) return;
        if (args.size() != 2) {
            channel.sendMessage("Correct syntax: >buy {Item ID} {Quantity}");
            return;
        }
        try {
            channel.sendMessage(SpreadsheetReader.buyItem(event, args.get(0), Integer.parseInt(args.get(1))));
        } catch (NumberFormatException e) {
            channel.sendMessage("Please enter a valid quantity");
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
