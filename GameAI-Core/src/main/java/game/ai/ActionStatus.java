package game.ai;

public enum ActionStatus {
    UNKNOWN(0, "UNKNOWN"),
    UNINITIALIZED(1, "UNINITIALIZED"),
    RUNNING(2, "RUNNING"),
    TERMINATED(3, "TERMINATED"),
    ;
    int code;
    String name;

    ActionStatus(int code, String name) {
        this.code = code;
        this.name = name;
    }
}
