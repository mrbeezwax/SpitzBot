package io.github.mrbeezwax.jermobot.Events;

import io.github.mrbeezwax.jermobot.Activities.Trivia;
import io.github.mrbeezwax.jermobot.Main;
import io.github.mrbeezwax.jermobot.Currency.CrayonBank;
import sx.blah.discord.api.events.EventSubscriber;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;
import sx.blah.discord.handle.obj.*;
import java.util.*;

public class TriviaEvent {
    private static HashMap<Long, Integer> playerCrayonLimit = Main.playerCrayonLimit;
    public static HashMap<IUser, Trivia> ongoingTrivias = new HashMap<>();

    @EventSubscriber
    public void onAnswer(MessageReceivedEvent event) {
        IMessage message = event.getMessage();
        IUser user = message.getAuthor();
        IGuild guild = event.getGuild();
        IChannel channel = event.getChannel();
        IChannel triviaChannelRevamped = guild.getChannelByID(517048928055853057L);
        //IChannel triviaChannelRevamped = guild.getChannelByID(517054190359543809L);

        if (channel != triviaChannelRevamped) return;
        if (!ongoingTrivias.containsKey(user)) return;

        if (ongoingTrivias.get(user).getAnswer().equalsIgnoreCase(message.getContent())) {
            if (atLimit(user)) {
                channel.sendMessage("You are correct and earned no crayons (Daily Limit of 125 Reached)");
            } else {
                channel.sendMessage("You are correct and earned **5** crayons");
                CrayonBank.add(5, user);
                updateLimit(user);
            }
            ongoingTrivias.get(user).finish();
            ongoingTrivias.remove(user);
        }
    }

    private static boolean atLimit(IUser user) {
        if (playerCrayonLimit.containsKey(user.getLongID())) {
            return playerCrayonLimit.get(user.getLongID()) >= 125;
        } else {
            playerCrayonLimit.put(user.getLongID(), 5);
            return false;
        }
    }

    private static void updateLimit(IUser user) {
        int earned = playerCrayonLimit.get(user.getLongID()) + 5;
        playerCrayonLimit.put(user.getLongID(), earned);
    }
}