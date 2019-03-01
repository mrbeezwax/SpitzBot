package io.github.mrbeezwax.jermobot.Commands.Currency;

import io.github.mrbeezwax.jermobot.Commands.Command;
import io.github.mrbeezwax.jermobot.Currency.CrayonBank;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IGuild;

import java.util.List;

public class LeaderboardCommand implements Command {
    private static final String DESCRIPTION = "Displays the top 10 richest players";

    @Override
    public void runCommand(MessageReceivedEvent event, List<String> args) {
        IChannel channel = event.getChannel();
        IGuild guild = event.getGuild();
        IChannel crayonCheckIn = guild.getChannelByID(473880568317214730L);

        if (channel != crayonCheckIn) {
            channel.sendMessage("Please check in at " + crayonCheckIn.mention());
            return;
        }
        channel.sendMessage(CrayonBank.printLeaderboard(guild));
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
