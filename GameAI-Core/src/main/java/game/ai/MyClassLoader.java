package game.ai;

import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Paths;
import java.util.Objects;

public class MyClassLoader extends URLClassLoader {

    ClassLoader parent = null;
    String path = null;

    public MyClassLoader(String path, ClassLoader parent) throws Exception {
        super(new URL[]{Paths.get(path).toUri().toURL()}, parent);
        this.parent = parent;
        this.path = path;
    }

    @Override
    public URL getResource(String name) {
        Objects.requireNonNull(name);
        URL url;
        url = findResource(name);
//        System.out.printf("findResource(%s) = %s\n", name, url);
        if(url==null){
            url = findResource(Paths.get(path, name).toString());
        }
        if(url!=null) return url;
        if (parent != null) {
            url = parent.getResource(name);
        }
        return url;
    }
}
