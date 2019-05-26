package io.github.mrbeezwax.jermobot.Events;

import sx.blah.discord.api.events.EventSubscriber;
import sx.blah.discord.handle.impl.events.guild.member.UserJoinEvent;

public class PlayerJoinEvent {
    @EventSubscriber
    public void onPlayerJoin(UserJoinEvent event) {
        try {
            event.getUser().addRole(event.getGuild().getRoleByID(454416389755764746L));
        } catch (NullPointerException e) {
            System.out.println("Dev Mode On. Role Adding Ignored");
        }
    }
}
