package io.github.mrbeezwax.jermobot.SquadTracker;

public class SquadTrackerException extends Exception {
    private String exception;

    public SquadTrackerException(String s) {
        super(s);
        exception = s;
    }

    @Override
    public String toString() {
        return exception;
    }
}
