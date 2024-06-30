package game.ai;

import game.ai.utils.BehaviorTreeReader;
import game.ai.utils.JsonUtil;
import lombok.AllArgsConstructor;

import java.io.InputStream;

@AllArgsConstructor
class ShutdownHook extends Thread{
    DefaultGameAIContext context;


    @Override
    public void run() {
        String json = JsonUtil.toJson(context.getBlackboard());
        System.out.println(json);
    }
}

public class GameAIApplication {
    public static void main(String[] args) throws Exception{
        DefaultGameAIContext context = new DefaultGameAIContext(new Blackboard());

        Runtime.getRuntime().addShutdownHook(new ShutdownHook(context));
        if(args.length<2){
            System.out.println("java game.ai.GameAIApplication <path> <script>");
            return;
        }
        MyClassLoader loader = new MyClassLoader(args[0], Thread.currentThread().getContextClassLoader());
        Thread.currentThread().setContextClassLoader(loader);
        InputStream inputStream = loader.getResourceAsStream(args[1]);
        BehaviorTree tree = BehaviorTreeReader.read(inputStream);
        inputStream.close();
        while(true){
            tree.update(context);
        }
    }
}
