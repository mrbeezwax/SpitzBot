package io.github.mrbeezwax.jermobot.SquadTracker;

import java.util.*;

public class Squad {
    private final int CAPACITY = 4;
    private ArrayList<SquadMember> playerList;
    private SquadMember leader;
    private String title;

    public Squad(SquadMember leader, String title) {
        playerList = new ArrayList<>(CAPACITY);
        playerList.add(leader);
        this.leader = leader;
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public SquadMember getLeader() {
        return leader;
    }

    public void addMember(SquadMember member) throws SquadTrackerException {
        if (playerList.size() == CAPACITY) throw new SquadTrackerException("The squad is full");
        if (playerList.contains(member)) throw new SquadTrackerException("They are already in this squad");
        playerList.add(member);
    }

    public void kickMember(int id) throws SquadTrackerException {
        if (id > playerList.size() || id < 2) throw new SquadTrackerException("Enter a valid id");
        playerList.remove(id - 1);
    }
}
