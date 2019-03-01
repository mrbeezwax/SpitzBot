package io.github.mrbeezwax.jermobot.Events;

import io.github.mrbeezwax.jermobot.Commands.Activities.SurpriseCommand;
import io.github.mrbeezwax.jermobot.Activities.Surprise;
import io.github.mrbeezwax.jermobot.Currency.CrayonBank;
import sx.blah.discord.api.events.EventSubscriber;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;
import sx.blah.discord.handle.obj.IChannel;

public class SurpriseEvent {
    @EventSubscriber
    public void onCheckIn(MessageReceivedEvent event) {
        IChannel crayonCheckIn = event.getGuild().getChannelByID(473880568317214730L);
        //IChannel crayonCheckIn = event.getChannel();
        if (event.getChannel() != crayonCheckIn) return;
        if (!SurpriseCommand.isStarted) return;
        Surprise surprise = SurpriseCommand.surprise;
        if (surprise.getParticipants().contains(event.getAuthor())) return;
        if (event.getMessage().getContent().equals(surprise.getKeyWord())) {
            CrayonBank.add(surprise.getAmount(), event.getAuthor());
            event.getChannel().sendMessage("You have received **" + surprise.getAmount() + "** crayons");
            surprise.getParticipants().add(event.getAuthor());
        }
    }
}
