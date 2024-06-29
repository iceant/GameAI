package game.ai;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.jse.CoerceJavaToLua;

@Data
@NoArgsConstructor
public class LuaKnowledgeSourceEvaluator implements IKnowledgeSourceEvaluator{
    LuaValue luaFunction;

    public LuaKnowledgeSourceEvaluator(LuaValue luaFunction) {
        this.luaFunction = luaFunction;
    }

    @Override
    public KnowledgeSourceEvaluateResult evaluate(IKnowledgeSourceEvaluateContext context) {
        LuaValue result = luaFunction.call(CoerceJavaToLua.coerce(context));
        KnowledgeSourceEvaluateResult evaluateResult = new KnowledgeSourceEvaluateResult();
        if(result.istable()){
            if(result.get("confidence")!=LuaValue.NIL){
                evaluateResult.setConfidence(result.get("confidence").checkdouble());
                evaluateResult.setEvaluation(result.get("evaluation"));
            }else{
                evaluateResult.setConfidence(result.get(1).checkdouble());
                LuaValue value = result.get(2);
                if(value.isstring()){
                    evaluateResult.setEvaluation(value.checkjstring());
                }else if(value.isnumber()){
                    if(value.isinttype()){
                        evaluateResult.setEvaluation(value.checkint());
                    }else{
                        evaluateResult.setEvaluation(value.checkdouble());
                    }
                }else if(value.isboolean()){
                    evaluateResult.setEvaluation(value.checkboolean());
                }else{
                    evaluateResult.setEvaluation(value);
                }
            }

        }

        return evaluateResult;
    }
}
