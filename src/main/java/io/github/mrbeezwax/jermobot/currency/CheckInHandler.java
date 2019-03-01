package io.github.mrbeezwax.jermobot.Currency;

import io.github.mrbeezwax.jermobot.Main;
import sx.blah.discord.handle.obj.IUser;
import java.util.Date;
import java.util.Map;

public class CheckInHandler {

    private static Map<Long, Date> playerCooldown = Main.playerCooldown;

    public static void main(String[] args) {
        Date updatedDate = new Date();
        System.out.println(updatedDate);
    }

    public static boolean checkIn(IUser user, boolean isSub, boolean isDono) {
        if (!isOnCooldown(user)) {
            if (isDono) CrayonBank.add(30, user);
            else if (isSub) CrayonBank.add(20, user);
            else CrayonBank.add(10, user);
            Date current = new Date();
            playerCooldown.put(user.getLongID(), current);
            return true;
        }
        return false;
    }

    private static boolean isOnCooldown(IUser user) {
        if (!checkPlayer(user)) {
            initializePlayer(user);
            return false;
        }
        Date userDate = playerCooldown.get(user.getLongID());
        Date currentDate = new Date();
        return currentDate.getTime() - userDate.getTime() < 1440 * 60 * 1000;
    }

    public static String getCooldownTimeLeft(IUser user) {
        if (!checkPlayer(user)) {
            initializePlayer(user);
            return "";
        }
        Date userDate = playerCooldown.get(user.getLongID());
        Date currentDate = new Date();
        Long timeLeftInMS = 1440 * 60 * 1000 - (currentDate.getTime() - userDate.getTime());
        String hour = "" + (timeLeftInMS / (1000*60*60)) % 24;
        String minutes = "" + (timeLeftInMS / (1000*60)) % 60;
        String seconds = "" + (timeLeftInMS / 1000) % 60;
        return "Time Left: **" + hour + "** hours, **" + minutes + "** minutes, and **" + seconds + "** seconds";
    }

    private static boolean checkPlayer(IUser user) {
        return playerCooldown.containsKey(user.getLongID());
    }

    private static void initializePlayer(IUser user) {
        playerCooldown.put(user.getLongID(), null);
    }
}
