package io.github.mrbeezwax.jermobot.SquadTracker;

import io.github.mrbeezwax.jermobot.Main;
import sx.blah.discord.handle.obj.IUser;

public class SquadMember {
    private int id;
    private String role;
    private IUser user;

    public SquadMember(int id, IUser name, String role) {
        this.id = id;
        this.role = role;
        this.user = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRole() {
        return role;
    }

    public IUser getUser() {
        return user;
    }
}
