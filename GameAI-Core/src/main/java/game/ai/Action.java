package game.ai;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Action {
    IActionFunction initializeFunction=null;
    IActionFunction updateFunction=null;
    IActionFunction cleanupFunction=null;

    String name;
    Object userdata=null;

    ActionStatus status = ActionStatus.UNINITIALIZED;
    GameAiTypes types = GameAiTypes.Action;

    public Action(String name, IActionFunction initializeFunction, IActionFunction updateFunction, IActionFunction cleanupFunction){
        this.name = name;
        this.initializeFunction = initializeFunction;
        this.updateFunction = updateFunction;
        this.cleanupFunction = cleanupFunction;
    }

    public void initialize(IActionContext context){
        context.setAction(this);
        if(status == ActionStatus.UNINITIALIZED){
            if(this.initializeFunction!=null){
                this.initializeFunction.apply(context);
            }
        }
        this.status = ActionStatus.RUNNING;
    }

    public ActionStatus update(IActionContext context){
        context.setAction(this);
        if(status==ActionStatus.TERMINATED){
            return ActionStatus.TERMINATED;
        }else if(status==ActionStatus.RUNNING){
            if(this.updateFunction!=null){
                this.status = this.updateFunction.apply(context);
            }else{
                this.status = ActionStatus.TERMINATED;
            }
        }
        return this.status;
    }

    public void cleanup(IActionContext context){
        context.setAction(this);
        if(status == ActionStatus.TERMINATED){
            if(this.cleanupFunction!=null){
                this.cleanupFunction.apply(context);
            }
        }
        this.status = ActionStatus.UNINITIALIZED;
    }
}
