package io.github.mrbeezwax.jermobot.Events;

import sx.blah.discord.api.events.EventSubscriber;
import sx.blah.discord.handle.impl.events.guild.member.UserJoinEvent;

public class PlayerJoinEvent {
    @EventSubscriber
    public void onPlayerJoin(UserJoinEvent event) {
        event.getUser().addRole(event.getGuild().getRoleByID(454416389755764746L));
    }
}
