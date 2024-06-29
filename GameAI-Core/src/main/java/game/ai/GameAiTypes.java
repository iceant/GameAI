package game.ai;

public enum GameAiTypes {
    Action(1, "Action"),
    Evaluator(2, "Evaluator"),
    DecisionTreeConditionNode(3, "DecisionTreeConditionNode"),
    DecisionTreeActionNode(4, "DecisionTreeActionNode"),
    ;


    int code;
    String name;

    GameAiTypes(int code, String name) {
        this.code = code;
        this.name = name;
    }
}
