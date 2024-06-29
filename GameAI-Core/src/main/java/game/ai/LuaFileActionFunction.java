package game.ai;

import game.ai.utils.LuaUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.luaj.vm2.Globals;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.jse.CoerceJavaToLua;

@Data
@AllArgsConstructor
public class LuaFileActionFunction implements IActionFunction{

    String file;

    @Override
    public ActionStatus apply(IActionContext context) {
        Globals globals = LuaUtil.getLuaVM();
        globals.set("context", CoerceJavaToLua.coerce(context));
        System.out.printf("[LuaFileActionFunction] %s\n", "actions/"+file);
        try {
            LuaValue result = globals.loadfile("actions/" + file).call();
            if (result.isstring()) {
                String string = result.checkjstring();
                return ActionStatus.valueOf(string.toUpperCase());
            }
        }catch (Exception err){
            err.printStackTrace();
        }
        return ActionStatus.UNKNOWN;
    }
}
