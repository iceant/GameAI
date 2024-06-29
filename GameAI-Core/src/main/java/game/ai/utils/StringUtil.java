package game.ai.utils;

public class StringUtil {
    public static int tokenCount(String string, char ch){
        int start = 0;
        int result =  0;
        while((start=string.indexOf(ch, start))!=-1){
            result+=1;
            start=start+1;
        }
        return result;
    }
}
