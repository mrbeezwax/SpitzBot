package io.github.mrbeezwax.jermobot.Commands;

import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;

import java.util.List;
import java.util.Random;

public class CoinFlipCommand implements Command {
    private static final String DESCRIPTION = "Flip a coin";

    @Override
    public void runCommand(MessageReceivedEvent event, List<String> args) {
        Random random = new Random();
        int side = random.nextInt(2);
        if (side == 0) event.getChannel().sendMessage("Heads");
        else event.getChannel().sendMessage("Tails");
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
