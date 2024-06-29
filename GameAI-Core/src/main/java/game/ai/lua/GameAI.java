package game.ai.lua;

import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.OneArgFunction;
import org.luaj.vm2.lib.TwoArgFunction;
import org.luaj.vm2.lib.ZeroArgFunction;

import java.util.Random;

public class GameAI extends TwoArgFunction {
    @Override
    public LuaValue call(LuaValue modName, LuaValue env) {
        LuaValue library = tableOf();
        library.set( "rand", new rand() );
        library.set( "exit", new exit() );
        env.set( "GameAI", library );
        return library;
    }

    static class rand extends OneArgFunction {
        static Random random = new Random(System.currentTimeMillis());

        @Override
        public LuaValue call(LuaValue luaValue) {
            if(luaValue.isnumber()){
                int randomValue = random.nextInt(luaValue.toint());
                return LuaValue.valueOf(randomValue);
            }else{
                int randomValue = random.nextInt();
                return LuaValue.valueOf(randomValue);
            }
        }
    }

    static class exit extends OneArgFunction{

        @Override
        public LuaValue call(LuaValue luaValue) {
            int value = luaValue.optint(0);
            System.exit(value);
            return LuaValue.NIL;
        }

    }
}
