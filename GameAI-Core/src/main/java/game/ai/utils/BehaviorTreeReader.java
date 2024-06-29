package game.ai.utils;

import game.ai.*;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.*;
import java.util.StringTokenizer;

public class BehaviorTreeReader {

    @Data
    @Accessors(chain = true)
    static class NodeInfo{
        BehaviorTreeNodeType type;
        String name;
        int level;

        public String getFormatedName(){
            return name.replaceAll(" ", "_").replaceAll("/", "_").replaceAll("\\\\", "_");
        }
    }

    public static NodeInfo parseNodeInfo(String line){
        NodeInfo nodeInfo = new NodeInfo();
        StringTokenizer stringTokenizer = new StringTokenizer(line, ":");
        int index = line.indexOf(":");
        String type = "";
        String name = "";
        if(index==-1){
            type = line;
        }else{
            type = line.substring(0, index).trim();
            name = line.substring(index+1).trim();
        }

        int level = StringUtil.tokenCount(line.replaceAll(" {4}", "\t"), '\t');
        if("selector".equalsIgnoreCase(type)){
            return nodeInfo.setType(BehaviorTreeNodeType.Selector).setName(name).setLevel(level);
        }else if("sequence".equalsIgnoreCase(type)){
            return nodeInfo.setType(BehaviorTreeNodeType.Sequence).setName(name).setLevel(level);
        }else if("condition".equalsIgnoreCase(type)){
            return nodeInfo.setType(BehaviorTreeNodeType.Condition).setName(name).setLevel(level);
        }else if("action".equalsIgnoreCase(type)){
            return nodeInfo.setType(BehaviorTreeNodeType.Action).setName(name).setLevel(level);
        }
        return nodeInfo.setType(BehaviorTreeNodeType.Unknown).setLevel(level);
    }

    public static BehaviorTree read(String text){
        StringReader reader = new StringReader(text);
        BufferedReader bufferedReader = new BufferedReader(reader);
        String line = null;
        BehaviorTree tree = new BehaviorTree();
        BehaviorTreeNode currentNode = null;
        BehaviorTreeNode latestNode = null;

        NodeInfo currentNodeInfo = null;
        NodeInfo latestNodeInfo = null;
        try {
            while ((line = bufferedReader.readLine()) != null) {
                currentNodeInfo = parseNodeInfo(line);
                if(currentNodeInfo.type==BehaviorTreeNodeType.Selector){
                    currentNode = BehaviorTreeNode.newSelectorNode(currentNodeInfo.getName());
                }else if(currentNodeInfo.type==BehaviorTreeNodeType.Sequence){
                    currentNode = BehaviorTreeNode.newSequenceNode(currentNodeInfo.getName());
                }else if(currentNodeInfo.type==BehaviorTreeNodeType.Condition){
                    currentNode = BehaviorTreeNode.newConditionNode(currentNodeInfo.getName(),
                            new Evaluator(currentNodeInfo.getName(), new LuaFileEvaluator(currentNodeInfo.getFormatedName()+".lua"), null));
                }else if(currentNodeInfo.type==BehaviorTreeNodeType.Action){
                    currentNode = BehaviorTreeNode.newActionNode(currentNodeInfo.getName(), new Action(currentNode.getName()
                            , new LuaFileActionFunction(currentNodeInfo.getFormatedName()+"_Initialize.lua")
                            , new LuaFileActionFunction( currentNodeInfo.getFormatedName()+"_Update.lua")
                            , new LuaFileActionFunction(currentNodeInfo.getFormatedName()+"_CleanUp.lua")
                    ));
                }
                if(currentNodeInfo.getLevel()==0){
                    /* root node */
                    tree.setRoot(currentNode);
                }else if(latestNodeInfo!=null){
                    if(currentNodeInfo.level > latestNodeInfo.level){
                        /*子节点*/
                        latestNode.addChild(currentNode);
                    }else
                    if(currentNodeInfo.level==latestNodeInfo.level){
                        /*兄弟节点*/
                        latestNode.getParent().addChild(currentNode);
                    }else {
                        /* 父节点的兄弟节点 */
                        int diff_levels = latestNodeInfo.getLevel() - currentNodeInfo.getLevel();
                        BehaviorTreeNode parent = latestNode.getParent();
                        for(int i=0; i<diff_levels; i++){
                            parent = parent.getParent();
                        }
                        parent.addChild(currentNode);
                    }
                }

                latestNode = currentNode;
                latestNodeInfo = currentNodeInfo;

            }
        }catch (Exception err){
            throw new RuntimeException(err);
        }
        return tree;
    }

    public static BehaviorTree read(InputStream inputStream){
        return read(IOUtil.readAsString(inputStream, 1024));
    }

    public static void main(String[] args) {
        InputStream inputStream =Thread.currentThread().getContextClassLoader().getResourceAsStream("soldier.bt");
        BehaviorTree behaviorTree = read(inputStream);
        System.out.println(JsonUtil.toJson(behaviorTree));
        DefaultGameAIContext context = new DefaultGameAIContext(new Blackboard());
        while(true){
            behaviorTree.update(context);
        }
    }
}
