package game.ai;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class FiniteStateMachine {
    FiniteState currentState;
    List<FiniteState> states = new ArrayList<>();
    Object userdata;

    public Boolean containsState(String stateName){
        if(stateName==null || stateName.isEmpty()){
            return Boolean.FALSE;
        }
        for(FiniteState state : states){
            if(state.getName().equals(stateName)){
                return Boolean.TRUE;
            }
        }
        return Boolean.FALSE;
    }

    public FiniteState findState(String stateName){
        for(FiniteState state : states){
            if(state.getName().equals(stateName)){
                return state;
            }
        }
        return null;
    }

    public String getCurrentStateName(){
        if(currentState!=null) {
            return currentState.getName();
        }
        return null;
    }

    public ActionStatus getCurrentStateStatus(){
        if(currentState!=null){
            return currentState.action.status;
        }
        return ActionStatus.UNKNOWN;
    }

    public FiniteStateMachine setState(IFiniteStateMachineContext context, String stateName){
        if(containsState(stateName)){
            if(this.currentState!=null){
                this.currentState.action.cleanup(context);
            }
            this.currentState = findState(stateName);
            this.currentState.action.initialize(context);
        }
        return this;
    }

    public FiniteStateMachine addState(String stateName, Action action){
        states.add(new FiniteState(stateName, action));
        return this;
    }

    public FiniteStateMachine addTransition(String fromStateName, String toStateName, Evaluator evaluator){
        FiniteState fromState = findState(fromStateName);
        if(fromState!=null){
            fromState.addTransition(toStateName, evaluator);
        }
        return this;
    }

    public Boolean containsTransition(String fromStateName, String toStateName){
        FiniteState fromState = findState(fromStateName);
        if(fromState==null) return Boolean.FALSE;
        return fromState.containsTransition(toStateName);
    }

    private String evaluateTransitions(IFiniteStateMachineContext context, List<FiniteStateTransition> transitions){
        if(transitions==null || transitions.isEmpty()) {
            return null;
        }

        for(FiniteStateTransition transition: transitions){
            if(transition.evaluator.evaluate(context)==1){
                return transition.getToStateName();
            }
        }
        return null;
    }

    public void update(IFiniteStateMachineContext context){
        if(this.currentState!=null){
            ActionStatus status = getCurrentStateStatus();
            if(status==ActionStatus.RUNNING){
                if(this.currentState.action.updateFunction!=null){
                    this.currentState.action.update(context);
                }
            }else if(status==ActionStatus.TERMINATED){
                String toStateName = evaluateTransitions(context, currentState.getTransitions());
                if (containsState(toStateName)) {
                    this.currentState.action.cleanup(context);
                    this.currentState = findState(toStateName);
                    this.currentState.action.initialize(context);
                }
            }
        }
    }
}
