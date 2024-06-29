package game.ai.lua;

import org.luaj.vm2.Globals;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.jse.JsePlatform;
import org.luaj.vm2.server.Launcher;

import java.io.InputStream;
import java.io.Reader;

public class LuaLauncher implements Launcher {
    Globals luaVM;

    public LuaLauncher(){
        luaVM = JsePlatform.standardGlobals();
    }

    @Override
    public Object[] launch(String script, Object[] arg) {
        LuaValue chunk = luaVM.load(script, "main");
        return new Object[] { chunk.call(LuaValue.valueOf(arg[0].toString())) };
    }

    @Override
    public Object[] launch(InputStream script, Object[] arg) {
        LuaValue chunk = luaVM.load(script, "main", "bt", luaVM);
        return new Object[] { chunk.call(LuaValue.valueOf(arg[0].toString())) };
    }

    @Override
    public Object[] launch(Reader script, Object[] arg) {
        LuaValue chunk = luaVM.load(script, "main");
        return new Object[] { chunk.call(LuaValue.valueOf(arg[0].toString())) };
    }
}
