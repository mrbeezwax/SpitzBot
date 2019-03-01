package io.github.mrbeezwax.jermobot.Activities;
import io.github.mrbeezwax.jermobot.Currency.CrayonBank;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IUser;
import java.util.Random;

// Source code by haseeb-heaven
public class SlotMachine {
    private IChannel channel;
    private IUser user;
    private int bet;

    public SlotMachine(int bet, IChannel channel, IUser user) {
        this.bet = bet;
        this.channel = channel;
        this.user = user;
    }

    public void startSlotMachine() {
        Random rand = new Random();
        int winnings = 0;
        int[] spinIndex = {-1, -2, -3};
        String[] emojis = {"\uD83E\uDD55", "\uD83C\uDF52", "\uD83C\uDF4D",
                "\uD83C\uDF47", "\uD83C\uDF36", "\uD83C\uDF6A", "\uD83C\uDF69", "\uD83C\uDF4C"};

        double chance = bet / 300.0 * 100; // Higher Bet = Higher Chance; Max = 33; Min = 0.33
        int newRand = generateRandom(chance);
        System.out.println("User bets " + bet + " with a chance of " + chance + " and rolls " + newRand);

        String spinMessage = "";
        int emojiIndex = rand.nextInt(8);
        if (newRand >= 90) {
            // Match Three
            for (int i = 0; i < spinIndex.length; i++) {
                spinIndex[i] = emojiIndex;
            }
            winnings = bet * 3;
            System.out.println("Three Matches");
        } else if (newRand >= 75) {
            // Match Two
            winnings = bet * 2;
            int firstIndex = rand.nextInt(3);
            int secondIndex = rand.nextInt(3);
            int thirdIndex = 0;
            while (secondIndex == firstIndex)
                secondIndex = rand.nextInt(3);
            while (thirdIndex == firstIndex || thirdIndex == secondIndex)
                thirdIndex++;
            spinIndex[firstIndex] = emojiIndex;
            spinIndex[secondIndex] = emojiIndex;
            int newEmojiIndex = rand.nextInt(8);
            while (emojiIndex == newEmojiIndex)
                newEmojiIndex = rand.nextInt(8);
            spinIndex[thirdIndex] = newEmojiIndex;
            System.out.println("Two Matches");
        } else {
            for (int i = 0; i < spinIndex.length; i++)
                spinIndex[i] = rand.nextInt(8);
            while (spinIndex[0] == spinIndex[1] || spinIndex[0] == spinIndex[2]) {
                for (int i = 0; i < spinIndex.length; i++)
                    spinIndex[i] = rand.nextInt(8);
            }
            System.out.println("No Matches");
        }
        System.out.println("Output " + spinIndex[0] + " " + spinIndex[1] + " " + spinIndex[2]);
        for (int i = 0; i < 3; i++)
            spinMessage += emojis[spinIndex[i]];
        if (winnings == 0) {
            channel.sendMessage(spinMessage + "\n" + "Sorry " + user.mention() + ", you did not win anything");
        } else {
            channel.sendMessage(spinMessage + "\n" + user.mention() + ", you won " + winnings + " crayons!");
            CrayonBank.add(winnings, user);
        }
    }

    private int generateRandom(double minLimit) {
        Random random = new Random();
        return random.nextInt(100 - (int)minLimit) + (int) minLimit;
    }
}
