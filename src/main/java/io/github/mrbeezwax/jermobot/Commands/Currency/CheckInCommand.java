package io.github.mrbeezwax.jermobot.Commands.Currency;

import io.github.mrbeezwax.jermobot.Commands.Command;
import io.github.mrbeezwax.jermobot.Currency.CheckInHandler;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;
import sx.blah.discord.handle.obj.*;

import java.util.List;

public class CheckInCommand implements Command {
    private static final String DESCRIPTION = "Receive 10 crayons every 24 hours";

    @Override
    public void runCommand(MessageReceivedEvent event, List<String> args) {
        IChannel crayonCheckIn = event.getGuild().getChannelByID(473880568317214730L);
        IUser user = event.getAuthor();
        IGuild guild = event.getGuild();
        IChannel channel = event.getChannel();

        boolean isSub = checkIfSub(guild, user);
        boolean isDono = checkIfDono(guild, user);
        if (channel != crayonCheckIn) {
            channel.sendMessage("Please type in " + crayonCheckIn.mention());
            return;
        }
        if (CheckInHandler.checkIn(user, isSub, isDono)) {
            if (isDono) channel.sendMessage("Thanks for checking in! **30** crayons have been added to your balance <:crayons:471246763160764426>");
            else if (isSub) channel.sendMessage("Thanks for checking in! **20** crayons have been added to your balance <:crayons:471246763160764426>");
            else channel.sendMessage("Thanks for checking in! **10** crayons have been added to your balance <:crayons:471246763160764426>");
        } else channel.sendMessage(CheckInHandler.getCooldownTimeLeft(user));
    }

    @Override
    public String getDescription() {
        return DESCRIPTION;
    }

    @Override
    public boolean forAdmin() {
        return false;
    }

    /**
     * checkIfSub() method
     * Checks if the user is a sub in Jermo's Discord
     * @return True or False
     */
    private boolean checkIfSub(IGuild guild, IUser user) {
        IRole subRole = guild.getRoleByID(473015096986042378L);
        if (subRole == null) return false;
        return user.hasRole(subRole);
    }

    /**
     * checkIfDono() method
     * Checks if the user is a donator in Jermo's Discord
     * @return True or False
     */
    private boolean checkIfDono(IGuild guild, IUser user) {
        IRole donoRole = guild.getRoleByID(472146644020756480L);
        if (donoRole == null) return false;
        return user.hasRole(donoRole);
    }
}
