package io.github.mrbeezwax.jermobot.Currency;

import io.github.mrbeezwax.jermobot.Main;
import io.github.mrbeezwax.jermobot.Data.Reference;
import sx.blah.discord.handle.obj.IGuild;
import sx.blah.discord.handle.obj.IUser;

import java.util.*;

public class CrayonBank {
    private static HashMap<Long, Integer> playerCrayons = Main.playerCrayons;

    public static void withdraw(int crayons, IUser recipient) {
        if (!checkPlayer(recipient)) initializePlayer(recipient);
        int balance = playerCrayons.get(recipient.getLongID());
        if (balance < crayons) {
            balance = 0;
        } else {
            balance -= crayons;
        }
        playerCrayons.put(recipient.getLongID(), balance);
        Reference.saveFiles();
    }

    public static void set(int crayons, IUser recipient) {
        playerCrayons.put(recipient.getLongID(), crayons);
        Reference.saveFiles();

    }

    public static void add(int crayons, IUser recipient) {
        if (!checkPlayer(recipient)) initializePlayer(recipient);
        int balance = playerCrayons.get(recipient.getLongID());
        playerCrayons.put(recipient.getLongID(), balance + crayons);
        Reference.saveFiles();

    }

    public static int getBalance(IUser recipient) {
        if (!checkPlayer(recipient)) {
            initializePlayer(recipient);
            return 0;
        }
        return playerCrayons.get(recipient.getLongID());
    }

    public static String getAllBalances(IGuild guild) {
        String balances = "";
        for (Long id : playerCrayons.keySet()) {
            if (id == 0) continue;
            IUser iuser = guild.getUserByID(id);
            if (iuser == null) continue;
            balances += iuser.getDisplayName(guild) + " " + playerCrayons.get(id) + "\n";
        }
        if (balances.isEmpty()) return "No one has crayons yet";
        return balances;
    }

    public static String printLeaderboard(IGuild guild) {
        String leaderboard = "```\nCrayons Leaderboard\n" + String.format("%-20.30s  %-20.30s%n", "Name", "Crayons");
        HashMap<Long, Integer> topPlayers = getTopPlayers();
        int counter = 1;
        for (Long id : topPlayers.keySet()) {
            if (id == 0) continue;
            if (counter == 11) break;
            int crayons = topPlayers.get(id);
            IUser iuser = guild.getUserByID(id);
            if (iuser == null) continue;
            String user = iuser.getDisplayName(guild);
            if (user.equalsIgnoreCase("null")) continue;
            leaderboard += String.format("%-20.30s  %-20.30s%n", counter + ") " + user, crayons);
            counter++;
        }
        return leaderboard + "```";
    }

    private static HashMap<Long, Integer> getTopPlayers() {
        List<Map.Entry<Long, Integer>> list = new ArrayList<>(playerCrayons.entrySet());
        list.sort(Map.Entry.comparingByValue());
        Collections.reverse(list);
        HashMap<Long, Integer> result = new LinkedHashMap<>();
        for (Map.Entry<Long, Integer> entry : list) {
            result.put(entry.getKey(), entry.getValue());
        }
        return result;
    }

    private static boolean checkPlayer(IUser user) {
        return playerCrayons.containsKey(user.getLongID());
    }

    private static void initializePlayer(IUser user) {
        playerCrayons.put(user.getLongID(), 0);
    }
}
