package io.github.mrbeezwax.jermobot.Activities;

import io.github.mrbeezwax.jermobot.Commands.Activities.SurpriseCommand;
import sx.blah.discord.handle.obj.IUser;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class Surprise {
    private String keyword;
    private int amount;
    private ArrayList<IUser> participants = new ArrayList<>();
    private Timer timer = new Timer();

    public Surprise(String keyword, int amount) {
        this.keyword = keyword;
        this.amount = amount;
    }

    public void startTimer(int minutes) {
        System.out.println("Timer Started");
        timer.scheduleAtFixedRate(new TimerTask() {
            int seconds = minutes * 60;
            @Override
            public void run() {
                seconds--;
                if (seconds < 0) {
                    timer.cancel();
                    System.out.println("Timer Ended");
                    SurpriseCommand.isStarted = false;
                }
            }
        }, 0, 1000);
    }

    public int getAmount() {
        return amount;
    }

    public String getKeyWord() {
        return keyword;
    }

    public ArrayList<IUser> getParticipants() {
        return participants;
    }
}
