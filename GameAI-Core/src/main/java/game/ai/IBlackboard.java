package game.ai;

public interface IBlackboard {
    Object get(String attributeName, IKnowledgeSourceEvaluateContext context);

    void set(String attributeName, Object value);
}
