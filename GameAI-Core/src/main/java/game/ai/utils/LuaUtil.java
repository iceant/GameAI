package game.ai.utils;

import org.luaj.vm2.Globals;
import org.luaj.vm2.lib.ResourceFinder;
import org.luaj.vm2.lib.jse.JsePlatform;

import java.io.InputStream;

public class LuaUtil {
    static Globals luaVM = JsePlatform.standardGlobals();
    public static Globals getLuaVM(){
        luaVM.finder = new ResourceFinder() {
            @Override
            public InputStream findResource(String s) {
                return Thread.currentThread().getContextClassLoader().getResourceAsStream(s);
            }
        };
        return luaVM;
    }
}
