package ai;

import game.ai.utils.IOUtil;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.OneArgFunction;
import org.luaj.vm2.lib.TwoArgFunction;
import org.luaj.vm2.lib.jse.CoerceJavaToLua;

import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class CodeAI extends TwoArgFunction {

    @Override
    public LuaValue call(LuaValue modName, LuaValue env) {
        LuaValue library = tableOf();
        library.set( "exit", new exit() );
        library.set( "list_files", new list_files() );
        library.set( "read_file", new read_file() );

        env.set( "CodeAI", library );
        env.get("package").get("loaded").set("CodeAI", library);

        return library;
    }


    /* -------------------------------------------------------------------------------------------------------------- */

    static final class exit extends OneArgFunction {
        @Override
        public LuaValue call(LuaValue luaValue) {
            int value = luaValue.optint(0);
            System.exit(value);
            return LuaValue.NIL;
        }
    }

    static final class list_files extends TwoArgFunction {
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

    static class read_file extends TwoArgFunction {

        @Override
        public LuaValue call(LuaValue path, LuaValue mode) {
            String file_path = null;
            if(path.isstring()){
                file_path = path.checkjstring();
            }else if(path.isuserdata()){
                Object object = path.checkuserdata();
                if(object instanceof File){
                    file_path = ((File)object).getAbsolutePath();
                }else{
                    return LuaValue.NIL;
                }
            }else{
                return LuaValue.NIL;
            }
            String read_as_mode = mode.checkjstring();
            if("string".equalsIgnoreCase(read_as_mode)){
                try {
                    String result = IOUtil.readAsString(new FileInputStream(file_path), 1024);
                    return LuaValue.valueOf(result);
                }catch (FileNotFoundException fnfe){
                    return LuaValue.NIL;
                }
            }else if("bytes".equalsIgnoreCase(read_as_mode)){
                try {
                    byte[] result = IOUtil.readAsByteArray(new FileInputStream(file_path), 1024);
                    LuaValue.valueOf(result);
                }catch (FileNotFoundException fnfe){
                    return LuaValue.NIL;
                }
            }
            return LuaValue.NIL;
        }
    }
}
