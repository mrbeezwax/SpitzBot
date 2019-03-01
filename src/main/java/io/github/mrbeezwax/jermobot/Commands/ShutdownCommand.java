package io.github.mrbeezwax.jermobot.Commands;

import io.github.mrbeezwax.jermobot.Data.Reference;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;

import java.util.List;

public class ShutdownCommand implements Command {
    private static final String DESCRIPTION = "Shuts down the bot; Can only be used by FriendlyNood";

    @Override
    public void runCommand(MessageReceivedEvent event, List<String> args) {
        if (event.getAuthor().getLongID() != 153674722029207554L) event.getChannel().sendMessage("Hey, you're not FriendlyNood!");
        else {
            event.getChannel().sendMessage("Nap Time for Spitz :zzz:");

            // Save Files
            Reference.saveFiles();

            System.out.println("Bot shutting down");
            System.exit(0);
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
