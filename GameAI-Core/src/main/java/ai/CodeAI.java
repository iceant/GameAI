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

    static class list_files extends TwoArgFunction {
        static void listFilesInFolder(String folder, List<File> result, LuaValue filterFunction){
            File file = Paths.get(folder).toFile();
            File[] subFiles = file.listFiles(new FileFilter() {
                @Override
                public boolean accept(File pathname) {
                    if(filterFunction!=LuaValue.NIL){
                        LuaValue result = filterFunction.call(CoerceJavaToLua.coerce(pathname));
                        return result.optboolean(false);
                    }
                    return true;
                }
            });
            for(File subFile : subFiles){
                result.add(subFile);
                if(subFile.isDirectory()){
                    listFilesInFolder(subFile.getAbsolutePath(), result, filterFunction);
                }
            }
        }

        @Override
        public LuaValue call(LuaValue path, LuaValue filter) {
            String folder = path.checkjstring();
            List<File> fileList = new ArrayList<>();
            listFilesInFolder(folder, fileList, filter);
            return CoerceJavaToLua.coerce(fileList);
        }
    }
}
