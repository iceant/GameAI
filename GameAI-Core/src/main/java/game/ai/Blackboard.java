package game.ai;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.*;

@Data
@NoArgsConstructor
public class Blackboard implements IBlackboard{
    Map<Object, Object> attributes = new HashMap<>();
    Map<String, List<KnowledgeSource>> sources = new HashMap<>();
    Object userdata = null;

    public Blackboard(Object userdata) {
        this.userdata = userdata;
    }

    public Blackboard addSource(String attributeName, KnowledgeSource source){
        List<KnowledgeSource> attributeSources = sources.getOrDefault(attributeName, new ArrayList<>());
        attributeSources.add(source);
        return this;
    }

    public Blackboard removeSource(String attributeName, KnowledgeSource source){
        List<KnowledgeSource> attributeSources = sources.getOrDefault(attributeName, Collections.EMPTY_LIST);
        if(!attributeSources.isEmpty()){
            attributeSources.remove(source);
        }
        return this;
    }

    public Object evaluate(IKnowledgeSourceEvaluateContext context, List<KnowledgeSource> sources){
        Double bestConfidence = 0D;
        Object bestResult = null;
        for(KnowledgeSource knowledgeSource : sources){
            KnowledgeSourceEvaluateResult eval = knowledgeSource.evaluate(context);
            if(eval.confidence > bestConfidence){
                bestConfidence = eval.confidence;
                bestResult = eval.evaluation;
            }
        }
        return bestResult;
    }

    public Object get(String attributeName, IKnowledgeSourceEvaluateContext context){
        List<KnowledgeSource> attributeKnowledgeSource = this.sources.getOrDefault(attributeName, Collections.EMPTY_LIST);
        if(!attributeKnowledgeSource.isEmpty()){
            return evaluate(context, attributeKnowledgeSource);
        }
        return attributes.get(attributeName);
    }

    public void set(String attributeName, Object value){
        this.attributes.put(attributeName, value);
    }
}
