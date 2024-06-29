package ai;

import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.OneArgFunction;
import org.luaj.vm2.lib.TwoArgFunction;
import org.luaj.vm2.lib.jse.CoerceJavaToLua;

import java.io.File;
import java.io.FileFilter;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class CodeAI extends TwoArgFunction {

    @Override
    public LuaValue call(LuaValue modName, LuaValue env) {
        LuaValue library = tableOf();
        library.set( "exit", new exit() );
        library.set( "list_files", new list_files() );
        env.set( "CodeAI", library );
        return library;
    }



    /* -------------------------------------------------------------------------------------------------------------- */

    static class exit extends OneArgFunction {
        @Override
        public LuaValue call(LuaValue luaValue) {
            int value = luaValue.optint(0);
            System.exit(value);
            return LuaValue.NIL;
        }
    }

    static class list_files extends OneArgFunction {
        static void listFilesInFolder(String folder, List<File> result){
            File file = Paths.get(folder).toFile();
            File[] subFiles = file.listFiles();
            for(File subFile : subFiles){
                result.add(subFile);
                if(subFile.isDirectory()){
                    listFilesInFolder(subFile.getAbsolutePath(), result);
                }
            }
        }
        @Override
        public LuaValue call(LuaValue luaValue) {
            String folder = luaValue.checkjstring();
            List<File> fileList = new ArrayList<>();
            listFilesInFolder(folder, fileList);
            return CoerceJavaToLua.coerce(fileList);
        }
    }
}
