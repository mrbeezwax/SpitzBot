package io.github.mrbeezwax.jermobot.SquadTracker;

import sx.blah.discord.handle.obj.IUser;

public class SquadMember {
    private int id;
    private Role role;
    private IUser user;

    public SquadMember(int id, IUser name, Role role) {
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

    public Role getRole() {
        return role;
    }

    public IUser getUser() {
        return user;
    }
}
