package io.github.mrbeezwax.jermobot.Commands.Activities;

import io.github.mrbeezwax.jermobot.Activities.Trivia;
import io.github.mrbeezwax.jermobot.Commands.Command;
import io.github.mrbeezwax.jermobot.Events.TriviaEvent;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IGuild;
import sx.blah.discord.handle.obj.IUser;

import java.util.List;

public class TriviaCommand implements Command {
    private static final String DESCRIPTION = "Starts a trivia question";

    @Override
    public void runCommand(MessageReceivedEvent event, List<String> args) {
        IUser user = event.getAuthor();
        IGuild guild = event.getGuild();
        IChannel channel = event.getChannel();
        IChannel triviaChannelRevamped = guild.getChannelByID(517048928055853057L);
        //IChannel triviaChannelRevamped = guild.getChannelByID(517054190359543809L);

        if (triviaChannelRevamped == null || channel != triviaChannelRevamped) return;
        if (TriviaEvent.ongoingTrivias.containsKey(user)) {
            channel.sendMessage("You already initiated a Trivia question. Answer it or wait for time to run out");
            return;
        }
        Trivia trivia = new Trivia(channel, user);
        channel.sendMessage(trivia.getQuestion());
        trivia.startTimer();
        TriviaEvent.ongoingTrivias.put(user, trivia);

//        if (Trivia.file.exists()) System.out.println("Warframe.txt found. Loading questions");
//        else System.out.println("Warframe.txt not found.");

//        if (!TriviaEvent.started) {
//            if (LocalTime.now(ZoneId.of("America/Los_Angeles")).getHour() >= 21 || LocalTime.now(ZoneId.of("America/Los_Angeles")).getHour() <= 8) {
//                channel.sendMessage("Trivia is on a rest period (12AM-8AM PST)");
//                return;
//            }
//            TriviaEvent.startTimer();
//        }
//        else channel.sendMessage("Trivia has already started");
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
