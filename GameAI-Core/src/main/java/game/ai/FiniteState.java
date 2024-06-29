package game.ai;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class FiniteState {
    String name;
    Action action;
    List<FiniteStateTransition> transitions = new ArrayList<>();

    public FiniteState(String name, Action action) {
        this.name = name;
        this.action = action;
    }

    public FiniteState addTransition(String toStateName, Evaluator evaluator){
        transitions.add(new FiniteStateTransition(toStateName, evaluator));
        return this;
    }

    public FiniteState removeTransition(String toStateName){
        for(FiniteStateTransition transition : transitions){
            if(transition.getToStateName().equals(toStateName)){
                transitions.remove(transition);
                break;
            }
        }
        return this;
    }

    public FiniteStateTransition findTransition(String toStateName){
        for(FiniteStateTransition transition : transitions){
            if(transition.getToStateName().equals(toStateName)){
                return transition;
            }
        }
        return null;
    }

    public Boolean containsTransition(String toStateName){
        for(FiniteStateTransition transition : transitions){
            if(transition.getToStateName().equals(toStateName)){
               return Boolean.TRUE;
            }
        }
        return Boolean.FALSE;
    }
}
