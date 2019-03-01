package io.github.mrbeezwax.jermobot.Events;

import io.github.mrbeezwax.jermobot.Main;
import sx.blah.discord.api.events.EventSubscriber;
import sx.blah.discord.handle.impl.events.ReadyEvent;

public class BotReadyEvent {
    @EventSubscriber
    public void onReady(ReadyEvent event) {
        System.out.println("Spitz Bot " + Main.VERSION + " Activated.");
    }
}
