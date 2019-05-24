package io.github.mrbeezwax.jermobot.SquadTracker;

import io.github.mrbeezwax.jermobot.Main;
import sx.blah.discord.handle.obj.IUser;

import java.util.Objects;

public class SquadMember {
    private String role;
    private IUser user;

    public SquadMember(IUser name, String role) {
        this.role = role;
        this.user = name;
    }

    public String getRole() {
        return role;
    }

    public IUser getUser() {
        return user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SquadMember that = (SquadMember) o;
        return Objects.equals(user, that.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(user);
    }

    @Override
    public String toString() {
        return user.getDisplayName(Main.getGuild());
    }
}
