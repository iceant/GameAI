package game.ai;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class KnowledgeSource {
    Double confidence = 0.0D;
    Object evaluation = null;
    IKnowledgeSourceEvaluator evaluator;
    Long lastUpdateTime = 0L;
    Long updateFrequencyInMs = 0L;

    public KnowledgeSource(IKnowledgeSourceEvaluator evaluator, Long updateFrequencyInMs){
        this.evaluator = evaluator;
        this.updateFrequencyInMs = updateFrequencyInMs;
    }

    public <T> KnowledgeSourceEvaluateResult evaluate(IKnowledgeSourceEvaluateContext context){
        long time = System.currentTimeMillis();
        long nextUpdateTime = time + updateFrequencyInMs;
        if(nextUpdateTime > lastUpdateTime){
            this.lastUpdateTime = time;

            KnowledgeSourceEvaluateResult result = evaluator.evaluate(context);
            this.confidence = result.getConfidence();
            this.evaluation = result.getEvaluation();
        }
        return new KnowledgeSourceEvaluateResult().setConfidence(this.confidence).setEvaluation(this.evaluation);
    }

}
