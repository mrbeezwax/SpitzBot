package io.github.mrbeezwax.jermobot.Commands;

import io.github.mrbeezwax.jermobot.Commands.Activities.ResetCrayonLimitCommand;
import io.github.mrbeezwax.jermobot.Commands.Activities.SlotMachineCommand;
import io.github.mrbeezwax.jermobot.Commands.Activities.SurpriseCommand;
import io.github.mrbeezwax.jermobot.Commands.Activities.TriviaCommand;
import io.github.mrbeezwax.jermobot.Commands.Currency.*;
import io.github.mrbeezwax.jermobot.Commands.SquadTracker.*;
import sx.blah.discord.api.events.EventSubscriber;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;
import sx.blah.discord.handle.obj.*;

import java.util.*;

public class CommandHandler {
    private static Map<String, Command> commandMap = new HashMap<>();
    private static Map<String, String> commandDescriptions = new HashMap<>();
    private static Map<String, String> commandDescriptionsUser = new HashMap<>();
    private static final String PREFIX = ">";

    static {
        commandMap.put("info", new InfoCommand());
        commandMap.put("coinflip", new CoinFlipCommand());
        commandMap.put("shutdown", new ShutdownCommand());
        commandMap.put("help", new HelpCommand());
        commandMap.put("checkin", new CheckInCommand());
        commandMap.put("balance", new BalanceCommand());
        commandMap.put("leaderboard", new LeaderboardCommand());
        commandMap.put("withdraw", new WithdrawCommand());
        commandMap.put("add", new AddCommand());
        commandMap.put("trivia", new TriviaCommand());
        commandMap.put("set", new SetCommand());
        commandMap.put("surprise", new SurpriseCommand());
        commandMap.put("bet", new SlotMachineCommand());
        commandMap.put("allbalances", new AllBalancesCommand());
        commandMap.put("resetlimit", new ResetCrayonLimitCommand());
        commandMap.put("buy", new BuyCommand());
        commandMap.put("addmember", new AddMemberCommand());
        commandMap.put("kick", new KickCommand());
        commandMap.put("create", new CreateCommand());
        commandMap.put("join", new JoinCommand());
        commandMap.put("leave", new LeaveCommand());
        commandMap.put("disband", new DisbandCommand());
        commandMap.put("role", new ChangeRoleCommand());
        commandMap.put("title", new ChangeTitleCommand());

        for (Map.Entry<String, Command> entry : commandMap.entrySet()) {
            commandDescriptions.put(entry.getKey(), entry.getValue().getDescription());
            if (!entry.getValue().forAdmin()) commandDescriptionsUser.put(entry.getKey(), entry.getValue().getDescription());
        }

    }

    @EventSubscriber
    public void onMessage(MessageReceivedEvent event) {
        boolean devMode = false;
        IMessage message = event.getMessage();
        ArrayList<IChannel> unlockedChannels = new ArrayList<>();
        IGuild guild = event.getGuild();
        if (guild.getLongID() == 454228555958714388L) devMode = true;
        // Add specific channels
        if (devMode) {
            System.out.println("DEV MODE");
            unlockedChannels.addAll(guild.getCategoryByID(471521451837751306L).getChannels());
        } else {
            unlockedChannels.add(guild.getChannelByID(451579671461625856L));        // Mod Chat
            unlockedChannels.add(guild.getChannelByID(473880568317214730L));        // crayon checkin
            unlockedChannels.add(guild.getChannelByID(541724855427661825L));        // crayon store
            unlockedChannels.add(guild.getChannelByID(581975760202629120L));        // squad tracker display
            unlockedChannels.add(guild.getChannelByID(581975953136418816L));        // squad tracker
            unlockedChannels.addAll(guild.getCategoryByID(473990790906118174L).getChannels());  // activity category
            unlockedChannels.addAll(guild.getCategoryByID(451833377612365824L).getChannels());  // bot category
        }

        String[] split = message.getContent().split(" ");
        if (!(split.length >= 1 && split[0].startsWith(PREFIX))) return;

        // If message is in private channel OR not legal channel, then return
        if (message.getChannel() instanceof IPrivateChannel ||
            !unlockedChannels.contains(message.getChannel())) return;

        String command = split[0].substring(1);
        List<String> argsList = new ArrayList<>(Arrays.asList(split));
        argsList.remove(0);

        if (commandMap.containsKey(command))
            commandMap.get(command).runCommand(event, argsList);
    }

    static Map getCommandDescriptions() {
        return commandDescriptions;
    }

    static Map getCommandDescriptionsUser() { return commandDescriptionsUser; }
}
