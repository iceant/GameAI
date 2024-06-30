package test;

public class JavaObject {

    private String name;

    public JavaObject(String name) {
        this.name = name;
    }

    public static String staticField = "JavaObject.STATIC_FIELD";

    public static void sayHello(String message){
        System.out.println(message);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "JavaObject{" +
                "name='" + name + '\'' +
                '}';
    }
}
