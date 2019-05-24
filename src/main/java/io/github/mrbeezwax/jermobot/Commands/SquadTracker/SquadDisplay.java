package io.github.mrbeezwax.jermobot.Commands.SquadTracker;

import io.github.mrbeezwax.jermobot.Main;
import io.github.mrbeezwax.jermobot.SquadTracker.Squad;
import io.github.mrbeezwax.jermobot.SquadTracker.SquadMember;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IMessage;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class SquadDisplay {
    private IMessage message;

    public SquadDisplay(IChannel channel) {
        if (channel.getFullMessageHistory().size() == 0) {
            System.out.println("CHANNEL EMPTY");
        } else {
            channel.bulkDelete();
        }
        message = channel.sendMessage("INITIALIZING SQUAD TRACKER...");
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                update(Main.squadList);
            }
        }, 0, 5000);       // 5 second period
    }

    private void update(List<Squad> squadList) {
        final String HEADER = "```==================================\n\tEidolon Hunt Squad Tracker\n==================================\n";
        final String FOOTER = "==================================\n" +
                "Commands:\nTo join a squad, type >join {squad id} {role}\n" +
                "To leave a squad, type >leave\n" +
                "To create a squad, type >create  {your role} {title}\n" +
                "To disband your squad, type >disband\n" +
                "To add someone to your squad, type >add {@user} {role}\n" +
                "To kick someone from your squad, type >kick {member id}\n```";

        String raw = HEADER;
        raw += "[ID] Squad Title\nCurrent Squads:\n";
        for (int i = 0 ; i < squadList.size(); i++) {
            raw += String.format("[%d] %s\n", i + 1, squadList.get(i));
            List<SquadMember> squad = squadList.get(i).getPlayerList();
            for (int j = 0; j < squad.size(); j++) {
                raw += String.format("\t(%d) %s as %s\n", j + 1, squad.get(j), squad.get(j).getRole());
            }
        }
        raw += FOOTER;
        message.edit(raw);
    }
}
