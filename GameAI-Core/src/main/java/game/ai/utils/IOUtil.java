package game.ai.utils;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

public class IOUtil {

    public static byte[] readAsByteArray(InputStream inputStream, int bufferSize){
        if(inputStream==null) return null;
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            byte[] buffer = new byte[bufferSize];
            int size = 0;
            while ((size = inputStream.read(buffer)) != -1) {
                baos.write(buffer, 0, size);
            }
            baos.close();
            return baos.toByteArray();
        }catch (Exception err){
            throw new RuntimeException(err);
        }
    }

    public static String readAsString(InputStream inputStream, int bufferSize){
        if(inputStream==null) return null;
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            byte[] buffer = new byte[bufferSize];
            int size = 0;
            while ((size = inputStream.read(buffer)) != -1) {
                baos.write(buffer, 0, size);
            }
            baos.close();
            return baos.toString();
        }catch (Exception err){
            throw new RuntimeException(err);
        }
    }
}
