package game.ai;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@Accessors(chain = true)
public class BehaviorTreeNode {
    String name;
    BehaviorTreeNodeType type = BehaviorTreeNodeType.Action;
    Action action;
    Evaluator evaluator;
    List<BehaviorTreeNode> children=new ArrayList<>();

    @JsonIgnore
    @ToString.Exclude
    BehaviorTreeNode parent = null;

    public BehaviorTreeNode(String name, BehaviorTreeNodeType type){
        this.name = name;
        this.type = type;
    }

    public static BehaviorTreeNode newActionNode(String name, Action action){
        return new BehaviorTreeNode(name, BehaviorTreeNodeType.Action).setAction(action);
    }

    public static BehaviorTreeNode newConditionNode(String name, Evaluator evaluator){
        return new BehaviorTreeNode(name, BehaviorTreeNodeType.Condition).setEvaluator(evaluator);
    }

    public static BehaviorTreeNode newSequenceNode(String name){
        return new BehaviorTreeNode(name, BehaviorTreeNodeType.Sequence).setChildren(new ArrayList<>());
    }

    public static BehaviorTreeNode newSelectorNode(String name){
        return new BehaviorTreeNode(name, BehaviorTreeNodeType.Selector).setChildren(new ArrayList<>());
    }

    public BehaviorTreeNode setChildren(List<BehaviorTreeNode> children){
        for(BehaviorTreeNode child : children){
            child.setParent(this);
        }
        this.children.addAll(children);
        return this;
    }

    public BehaviorTreeNode addChild(BehaviorTreeNode child){
        if(this.children!=null){
            this.children.add(child);
        }
        child.setParent(this);
        return this;
    }

    public BehaviorTreeNode addChildAt(BehaviorTreeNode child, int index){
        if(this.children!=null){
            this.children.add(index, child);
        }
        child.setParent(this);
        return this;
    }

    public int findChildIndex(BehaviorTreeNode child){
        for(int i=0; i<children.size(); i++){
            if(children.get(i).equals(child)){
                return i;
            }
        }
        return -1;
    }

    @JsonIgnore
    public int getNumberOfChildren(){
        return children.size();
    }

    public BehaviorTreeNode getChildAt(int index){
        return children.get(index);
    }
}

