package game.ai;

import game.ai.utils.BehaviorTreeReader;

import java.io.InputStream;


public class GameAIApplication {
    public static void main(String[] args) throws Exception{
        if(args.length<2){
            System.out.println("java game.ai.GameAIApplication <path> <script>");
            return;
        }
        MyClassLoader loader = new MyClassLoader(args[0], Thread.currentThread().getContextClassLoader());
        Thread.currentThread().setContextClassLoader(loader);
        InputStream inputStream = loader.getResourceAsStream(args[1]);
        BehaviorTree tree = BehaviorTreeReader.read(inputStream);
        inputStream.close();
        DefaultGameAIContext context = new DefaultGameAIContext(new Blackboard());
        while(true){
            tree.update(context);
        }
    }
}
