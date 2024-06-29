package game.ai;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class DecisionTreeNode {

    List<DecisionTreeNode> children = null;
    Evaluator evaluator = null;
    Action action = null;
    GameAiTypes type = GameAiTypes.DecisionTreeConditionNode;


    public DecisionTreeNode(Action action){
        this.action = action;
        this.type = GameAiTypes.DecisionTreeActionNode;
    }

    public DecisionTreeNode(Evaluator evaluator){
        this.evaluator = evaluator;
        this.children = new ArrayList<>();
    }

    public DecisionTreeNode addChild(DecisionTreeNode child){
        children.add(child);
        return this;
    }

    public DecisionTreeNode addChildAt(DecisionTreeNode child, int index){
        children.add(index, child);
        return this;
    }

    public DecisionTreeNode addActionChild(Action action){
        return addChild(new DecisionTreeNode(action));
    }

    public DecisionTreeNode addActionChildAt(Action action, int index){
        return addChildAt(new DecisionTreeNode(action), index);
    }

    public DecisionTreeNode addEvaluatorChild(Evaluator evaluator){
        return addChild(new DecisionTreeNode(evaluator));
    }

    public DecisionTreeNode addEvaluatorChildAt(Evaluator evaluator, int index){
        return addChildAt(new DecisionTreeNode(evaluator), index);
    }

    public Action evaluate(IDecisionTreeContext context){
        int childIndex = evaluator.evaluate(context);
        DecisionTreeNode child = children.get(childIndex);
        if(child.getType()==GameAiTypes.DecisionTreeConditionNode){
            return child.evaluate(context);
        }else{
            return child.getAction();
        }
    }


}
