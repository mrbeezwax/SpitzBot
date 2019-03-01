package io.github.mrbeezwax.jermobot.Events;

import sx.blah.discord.api.events.EventSubscriber;
import sx.blah.discord.handle.impl.events.guild.channel.message.reaction.ReactionEvent;
import sx.blah.discord.handle.impl.events.guild.channel.message.reaction.ReactionRemoveEvent;

public class RecruitingRoleEvent {
    @EventSubscriber
    public void onReact(ReactionEvent event) {
        if (event.getMessageID() == 548414218517348365L) {
            event.getUser().addRole(event.getGuild().getRoleByID(548408422446399488L));
            System.out.println("User " + event.getUser().getName() + " reacted");
        }
    }

    @EventSubscriber
    public void onRemoveReact(ReactionRemoveEvent event) {
        if (event.getMessageID() == 548414218517348365L) {
            event.getUser().removeRole(event.getGuild().getRoleByID(548408422446399488L));
            System.out.println("User " + event.getUser().getName() + " unreacted");
        }
    }
}
