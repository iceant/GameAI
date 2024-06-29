package game.ai;

public interface IBehaviorTreeContext extends IEvaluatorContext, IActionContext{
    void setBehaviorTree(BehaviorTree behaviorTree);

    BehaviorTree getBehaviorTree();
}
