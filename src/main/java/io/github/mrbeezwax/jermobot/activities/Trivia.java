package io.github.mrbeezwax.jermobot.Activities;

import io.github.mrbeezwax.jermobot.Events.TriviaEvent;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IUser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class Trivia {
    private String question;
    private String answer;
    private Timer timer;
    private IUser user;
    private IChannel channel;
    private static File file = new File("resources/warframe.txt");

    public Trivia(IChannel channel, IUser user) {
        String[] triviaQuestion = getContents();
        question = triviaQuestion[0];
        answer = triviaQuestion[1];
        timer = new Timer();
        this.channel = channel;
        this.user = user;
    }

    private String[] getContents() {
        Random rand = new Random();
        int type = rand.nextInt(2) + 1;
        //int type = 2;
        //while (!file.exists() && type == 2) rand.nextInt(5);
        if (!file.exists()) type = 1;
        if (type == 1) {
            return mathQuestion();
        } else if (type == 2) {
            return warframeQuestion();
        } else return null;
    }

    private String[] mathQuestion() {
        Random rand = new Random();
        String operation;
        int operationAnswer;
        int operationType = rand.nextInt(4);
        int var1 = rand.nextInt(101);
        int var2 = rand.nextInt(101);
        if (operationType == 1) {
            operation = var1 + " + " + var2;
            operationAnswer = var1 + var2;
        } else if (operationType == 2) {
            operation = var1 + " - " + var2;
            operationAnswer = var1 - var2;
        } else {
            operation = var1 + " * " + var2;
            operationAnswer = var1 * var2;
        }
        System.out.println(operation + "`" + operationAnswer);
        return new String[]{operation, operationAnswer + ""};
    }

    private String[] warframeQuestion() {
        Random rand = new Random();
        int numberOfLines = rand.nextInt(144) + 1;
        String[] contents;
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line = "";
            for (int i = 0; i < numberOfLines; i++) {
                line = br.readLine();
            }
            System.out.println(line);
            contents = line.split("`");
            return contents;
        } catch (IOException p) {
            System.out.println("IOException while reading warframe.txt");
            return null;
        }
    }

    public String getQuestion() {
        return question;
    }

    public String getAnswer() {
        return answer;
    }

    public void startTimer() {
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                channel.sendMessage("The answer is: " + answer);
                TriviaEvent.ongoingTrivias.remove(user);
            }
        }, 15000);
    }

    public IUser getUser() {
        return user;
    }

    public void finish() {
        timer.cancel();
        timer.purge();
    }
}