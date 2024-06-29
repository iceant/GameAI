package game.ai;

public interface IFiniteStateMachineContext extends IActionContext, IEvaluatorContext{
    void setFiniteStateMachine(FiniteStateMachine finiteStateMachine);

    FiniteStateMachine getFiniteStateMachine();
}
