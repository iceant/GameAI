package test;

import game.ai.*;

import java.util.Random;

public class BlackboardTest {

    static Random random = new Random(System.currentTimeMillis());

    static int rangeRand(int min, int max){
        return min + random.nextInt(max)%(max-min+1);
    }

    static double rangeRand(double min, double max){
        return min + random.nextDouble();
    }

    public static void main(String[] args) {
        Blackboard blackboard = new Blackboard();
        blackboard.addSource("value", new KnowledgeSource(new IKnowledgeSourceEvaluator() {
            @Override
            public KnowledgeSourceEvaluateResult evaluate(IKnowledgeSourceEvaluateContext context) {
                KnowledgeSourceEvaluateResult result = new KnowledgeSourceEvaluateResult();
                result.setConfidence(rangeRand(1.0D, 10.0D));
                result.setEvaluation(rangeRand(1, 100));
                System.out.println("Knowledge-1: "+result);
                return result;
            }
        }, 1000L));
        blackboard.addSource("value", new KnowledgeSource(new IKnowledgeSourceEvaluator() {
            @Override
            public KnowledgeSourceEvaluateResult evaluate(IKnowledgeSourceEvaluateContext context) {
                KnowledgeSourceEvaluateResult result = new KnowledgeSourceEvaluateResult();
                result.setConfidence(rangeRand(1.0D, 10.0D));
                result.setEvaluation(rangeRand(1, 100));
                System.out.println("Knowledge-2: "+result);
                return result;
            }
        }, 2000L));

        for(int i=0; i<10; i++){
            Object value = blackboard.get("value", null);
            System.out.println("["+i+"] "+value);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
