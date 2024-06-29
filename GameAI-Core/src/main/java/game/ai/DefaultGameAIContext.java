package game.ai;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Getter
@Setter
@NoArgsConstructor
public class DefaultGameAIContext implements IActionContext
        , IEvaluatorContext
        , IFiniteStateMachineContext
        , IDecisionTreeContext
        , IBehaviorTreeContext
        , IKnowledgeSourceEvaluateContext
{
    Action action;
    IBlackboard blackboard;
    Evaluator evaluator;
    FiniteStateMachine finiteStateMachine;
    BehaviorTree behaviorTree;

    public DefaultGameAIContext(IBlackboard blackboard) {
        this.blackboard = blackboard;
    }
}
