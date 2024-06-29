package game.ai;

public interface IEvaluatorContext {

    void setEvaluator(Evaluator evaluator);

    Evaluator getEvaluator();

    IBlackboard getBlackboard();
}
