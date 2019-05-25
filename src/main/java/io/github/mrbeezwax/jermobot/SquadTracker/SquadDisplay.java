package io.github.mrbeezwax.jermobot.SquadTracker;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import io.github.mrbeezwax.jermobot.Main;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IMessage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
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
                if (isExpired()) Main.squadList.clear();
                update(Main.squadList);
            }
        }, 0, 5000);       // 5 second period
    }

    private void update(List<Squad> squadList) {
        final String HEADER = "```==================================\n\tEidolon Hunt Squad Tracker\n==================================\n";
        final String FOOTER = "==================================\n" +
                "Commands:\nTo join a squad, type >join {squad id} {role}\n" +
                "To edit your role, type >role {role}\n" +
                "To edit the title, type >title {title}\n" +
                "To leave a squad, type >leave\n" +
                "To create a squad, type >create  {your role} {title}\n" +
                "To disband your squad, type >disband\n" +
                "To add someone to your squad, type >addmember {@user} {role}\n" +
                "To kick someone from your squad, type >kick {member id}\n```";

        StringBuilder sb = new StringBuilder(HEADER);
        sb.append("[ID] Squad Title\nCurrent Squads:\n");
        for (int i = 0 ; i < squadList.size(); i++) {
            sb.append(String.format("[%d] %s\n", i + 1, squadList.get(i)));
            List<SquadMember> squad = squadList.get(i).getPlayerList();
            for (int j = 0; j < squad.size(); j++) {
                sb.append(String.format("\t(%d) %s as %s\n", j + 1, squad.get(j), squad.get(j).getRole()));
            }
        }
        sb.append(FOOTER);
        message.edit(sb.toString());
    }

    private String getTime() {
        try {
            System.setProperty("http.agent", "Chrome");
            URL url = new URL("https://api.warframestat.us/pc/cetusCycle/");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.setConnectTimeout(5000);
            con.setReadTimeout(5000);

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream())
            );
            String inputLine;
            StringBuilder content = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            in.close();
            con.disconnect();
            return content.toString();
        } catch (IOException e) {
            System.out.println(e.toString());
            return "ERROR";
        }
    }

    private boolean isExpired() {
        String response = getTime();
        JsonObject jsonObject = new JsonParser().parse(response).getAsJsonObject();
        String state = jsonObject.getAsJsonObject().get("state").getAsString();
        String timeLeft = jsonObject.getAsJsonObject().get("timeLeft").getAsString().substring(0, 2);
        if (timeLeft.charAt(1) == 'm') {
            int digit = Integer.parseInt(timeLeft.substring(0, 1));
            if (state.equalsIgnoreCase("night") && digit < 10) {
                System.out.println("Squad Tracker Reset");
                return true;
            }
        }
        return false;
    }
}