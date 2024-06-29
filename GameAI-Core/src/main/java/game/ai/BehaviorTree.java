package game.ai;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class BehaviorTree {
    BehaviorTreeNode root;
    BehaviorTreeNode currentNode;
    Object userdata;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class BehaviorTreeEvaluateResult{
        Boolean result;
        BehaviorTreeNode node;

        public BehaviorTreeEvaluateResult(Boolean result) {
            this.result = result;
            this.node = null;
        }
    }

    public static BehaviorTreeEvaluateResult evaluateSequence(IBehaviorTreeContext context, BehaviorTree tree, BehaviorTreeNode node, int index)
    {
        for(int i=index; i<node.getNumberOfChildren(); i++){
            BehaviorTreeNode child = node.getChildAt(i);
            if(child.getType()==BehaviorTreeNodeType.Action){
                return new BehaviorTreeEvaluateResult(true, child);
            }else if(child.getType()==BehaviorTreeNodeType.Condition){
                int result = child.evaluator.evaluate(context);
                if(result==0){
                    return new BehaviorTreeEvaluateResult(false);
                }
            }else if(child.getType()==BehaviorTreeNodeType.Selector){
                BehaviorTreeEvaluateResult result = evaluateSelector(context, tree, child);
                if(result.result==Boolean.FALSE){
                    return result;
                }else if(result.result==Boolean.TRUE && result.node!=null){
                    return result;
                }
            }else if(child.getType()==BehaviorTreeNodeType.Sequence){
                BehaviorTreeEvaluateResult result = evaluateSequence(context, tree, child, 0);
                if(!result.result){
                    return result;
                }else if(result.node != null){
                    return result;
                }
            }
        }
        return new BehaviorTreeEvaluateResult(true);
    }

    public static BehaviorTreeEvaluateResult  evaluateSelector(IBehaviorTreeContext context, BehaviorTree tree, BehaviorTreeNode node){
        for(int i=0; i<node.getNumberOfChildren(); i++){
            BehaviorTreeNode child = node.getChildAt(i);
            if(child.getType()==BehaviorTreeNodeType.Action){
                return new BehaviorTreeEvaluateResult(true, child);
            }else if(child.getType()==BehaviorTreeNodeType.Condition){
                return new BehaviorTreeEvaluateResult(false);
            }else if(child.getType()==BehaviorTreeNodeType.Selector){
                BehaviorTreeEvaluateResult result = evaluateSelector(context, tree, child);
                if(result.result){
                    return result;
                }
            }else if(child.getType()==BehaviorTreeNodeType.Sequence){
               BehaviorTreeEvaluateResult result = evaluateSequence(context, tree, child, 0);
               if(result.result){
                   return result;
               }
            }
        }
        return new BehaviorTreeEvaluateResult(false);
    }

    public static BehaviorTreeNode evaluateNode(IBehaviorTreeContext context, BehaviorTree tree, BehaviorTreeNode node){
        if(node.getType()==BehaviorTreeNodeType.Action){
            return node;
        }else if(node.getType()==BehaviorTreeNodeType.Condition){
            /* ERROR */
        }else if(node.getType()==BehaviorTreeNodeType.Selector){
            BehaviorTreeEvaluateResult result = evaluateSelector(context, tree, node);
            if(result.result){
                return result.node;
            }
        }else if(node.getType()==BehaviorTreeNodeType.Sequence){
            BehaviorTreeEvaluateResult result = evaluateSequence(context, tree, node, 0);
            if(result.result){
                return result.node;
            }
        }
        return null;
    }

    public static BehaviorTreeEvaluateResult continueEvaluation(IBehaviorTreeContext context, BehaviorTree tree, BehaviorTreeNode node){
        BehaviorTreeNode parentNode = node.getParent();
        BehaviorTreeNode childNode = node;

        while(parentNode!=null){
            if(parentNode.getType()==BehaviorTreeNodeType.Sequence){
                int childIndex = parentNode.findChildIndex(childNode);
                if(childIndex< parentNode.getNumberOfChildren()){
                    return evaluateSequence(context, tree, parentNode, childIndex+1);
                }
            }

            childNode = parentNode;
            parentNode = childNode.getParent();
        }
        return new BehaviorTreeEvaluateResult(false);
    }

    public void update(IBehaviorTreeContext context){
        context.setBehaviorTree(this);
        if(this.currentNode==null){
            this.currentNode = evaluateNode(context, this, this.root);
        }

        if(this.currentNode!=null){
            ActionStatus status = this.currentNode.action.getStatus();
            if(status==ActionStatus.UNINITIALIZED){
                this.currentNode.action.initialize(context);
            }else if(status==ActionStatus.TERMINATED){
                this.currentNode.action.cleanup(context);
                this.currentNode = continueEvaluation(context, this, this.currentNode).node;
            }else if(status==ActionStatus.RUNNING){
                this.currentNode.action.update(context);
            }
        }
    }
}
