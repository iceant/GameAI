package game.ai;

import game.ai.utils.LuaUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.luaj.vm2.Globals;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.jse.CoerceJavaToLua;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class LuaFileEvaluator implements IEvaluatorFunction{
    String file;

    @Override
    public Integer apply(IEvaluatorContext context) {
        Globals vm = LuaUtil.getLuaVM();
        vm.set("context", CoerceJavaToLua.coerce(context));
        try {
            LuaValue result = vm.loadfile("evaluators/" + file).call();
        System.out.printf("[LuaFileEvaluator] %s -> %d\n", "evaluators/"+file, result.toint());
            return result.toint();
        }catch (Exception err){
            err.printStackTrace();
        }
        return 0;
    }
}
