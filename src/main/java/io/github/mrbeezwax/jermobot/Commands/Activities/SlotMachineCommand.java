package io.github.mrbeezwax.jermobot.Commands.Activities;

import io.github.mrbeezwax.jermobot.Activities.SlotMachine;
import io.github.mrbeezwax.jermobot.Commands.Command;
import io.github.mrbeezwax.jermobot.Currency.CrayonBank;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;
import sx.blah.discord.handle.obj.IChannel;

import java.util.List;

public class SlotMachineCommand implements Command {
    private static final String DESCRIPTION = "Test your luck on the slot machine in #slotmachine";

    @Override
    public void runCommand(MessageReceivedEvent event, List<String> args) {
        //IChannel main = event.getGuild().getChannelByID(468943651930767381L); // TEST
        IChannel main = event.getGuild().getChannelByID(512898564804968450L);
        if (event.getChannel() != main) {
            event.getChannel().sendMessage("Please bet in " + main.mention());
            return;
        }
        if (args.size() != 1) {
            event.getChannel().sendMessage("Correct syntax: >bet {bet amount 1-100}");
        } else {
            try {
                int winnings = Integer.parseInt(args.get(0));
                if (winnings < 1 || winnings > 100) {
                    event.getChannel().sendMessage("Please bet any amount between 1 and 100 crayons");
                    return;
                }
                if (winnings > CrayonBank.getBalance(event.getAuthor())) {
                    event.getChannel().sendMessage("Insufficient funds");
                    return;
                }
                CrayonBank.withdraw(winnings, event.getAuthor());
                SlotMachine game = new SlotMachine(winnings, event.getChannel(), event.getAuthor());
                game.startSlotMachine();
            } catch (NumberFormatException p) {
                event.getChannel().sendMessage("Correct syntax: >bet {bet amount 1-100}");
            }
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
