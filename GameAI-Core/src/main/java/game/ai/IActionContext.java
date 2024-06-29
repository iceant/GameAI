package game.ai;

public interface IActionContext {

    void setAction(Action action);

    Action getAction();

    IBlackboard getBlackboard();
}
