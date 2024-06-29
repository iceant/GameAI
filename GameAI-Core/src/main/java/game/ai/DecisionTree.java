package game.ai;

import lombok.Data;

@Data
public class DecisionTree {
    DecisionTreeNode branch = null;
    Action currentAction = null;

    public void update(IDecisionTreeContext context){
        if(branch==null) return;

        if(currentAction==null){
            currentAction = branch.evaluate(context);
            currentAction.initialize(context);
        }

        ActionStatus status = currentAction.update(context);
        if(status==ActionStatus.TERMINATED){
            currentAction.cleanup(context);
            this.currentAction = null;
        }
    }
}
