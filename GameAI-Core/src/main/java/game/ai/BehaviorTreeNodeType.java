package game.ai;

public enum BehaviorTreeNodeType {
    Unknown(0, "Unknown"),
    Action(1, "Action"),
    Condition(2, "Condition"),
    Selector(3, "Selector"),
    Sequence(4, "Sequence"),
    ;
    int code;
    String name;

    BehaviorTreeNodeType(int code, String name) {
        this.code = code;
        this.name = name;
    }
}
