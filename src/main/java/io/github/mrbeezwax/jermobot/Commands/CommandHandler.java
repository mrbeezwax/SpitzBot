package io.github.mrbeezwax.jermobot.Commands;

import io.github.mrbeezwax.jermobot.Commands.Activities.ResetCrayonLimitCommand;
import io.github.mrbeezwax.jermobot.Commands.Activities.SlotMachineCommand;
import io.github.mrbeezwax.jermobot.Commands.Activities.SurpriseCommand;
import io.github.mrbeezwax.jermobot.Commands.Activities.TriviaCommand;
import io.github.mrbeezwax.jermobot.Commands.Currency.*;
import io.github.mrbeezwax.jermobot.Commands.SquadTracker.*;
import sx.blah.discord.api.events.EventSubscriber;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;
import sx.blah.discord.handle.obj.ICategory;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.handle.obj.IPrivateChannel;

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
        commandMap.put("changerole", new ChangeRoleCommand());
        commandMap.put("changetitle", new ChangeTitleCommand());

        for (Map.Entry<String, Command> entry : commandMap.entrySet()) {
            commandDescriptions.put(entry.getKey(), entry.getValue().getDescription());
            if (!entry.getValue().forAdmin()) commandDescriptionsUser.put(entry.getKey(), entry.getValue().getDescription());
        }

    }

    @EventSubscriber
    public void onMessage(MessageReceivedEvent event) {
        IMessage message = event.getMessage();
        ICategory botCategory = event.getGuild().getCategoryByID(451833377612365824L);
        ICategory botDevCategory = event.getGuild().getCategoryByID(471521451837751306L);
        ICategory activityCategory = event.getGuild().getCategoryByID(473990790906118174L);
        IChannel moderatorChat = event.getGuild().getChannelByID(451579671461625856L);
        IChannel crayonCheckIn = event.getGuild().getChannelByID(473880568317214730L);
        IChannel crayonStore = event.getGuild().getChannelByID(541724855427661825L);

//        if (botCategory == null && botDevCategory == null
//                && moderatorChat == null) return;

        String[] split = message.getContent().split(" ");
        if (!(split.length >= 1 && split[0].startsWith(PREFIX))) return;

        if (message.getChannel() instanceof IPrivateChannel
                || ((message.getChannel().getCategory() != botCategory)
                && (message.getChannel().getCategory() != botDevCategory)
                && (message.getChannel() != moderatorChat)
                && (message.getChannel() != crayonStore)
                && (message.getChannel() != crayonCheckIn)
                && (message.getChannel().getCategory() != activityCategory))) return;

        String command = split[0].substring(1);
        List<String> argsList = new ArrayList<>(Arrays.asList(split));
        argsList.remove(0);

        if (commandMap.containsKey(command))
            commandMap.get(command).runCommand(event, argsList);
    }

    public static Map getCommandDescriptions() {
        return commandDescriptions;
    }

    public static Map getCommandDescriptionsUser() { return commandDescriptionsUser; }
}
