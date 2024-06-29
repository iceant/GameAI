package game.ai;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Evaluator {
    String name;
    IEvaluatorFunction function;
    GameAiTypes type = GameAiTypes.Evaluator;
    Object userdata;

    public Evaluator(String name, IEvaluatorFunction function, Object userdata){
        this.name = name;
        this.function = function;
        this.userdata = userdata;
    }

    public int evaluate(IEvaluatorContext context){
        context.setEvaluator(this);
        return function.apply(context);
    }


}
