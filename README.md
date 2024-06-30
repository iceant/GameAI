# `luajava` usage


### Java Object
```java
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

```

### Lua Usage
```lua
local JavaObject = luajava.bindClass('test.JavaObject')
print(JavaObject.staticField)
JavaObject:sayHello("Hello from Lua")
local JavaObjectInstance = luajava.new(JavaObject, "New JavaObject Name")
print("JavaObjectInstance", JavaObjectInstance)
print("JavaObjectInstance:getName()", JavaObjectInstance:getName())
JavaObjectInstance:setName("Set JavaObject Name")
print(JavaObjectInstance:getName())
```

### 输出
```console
'JavaObject.STATIC_FIELD'
Hello from Lua
'JavaObjectInstance'	JavaObject{name='New JavaObject Name'}
'JavaObjectInstance:getName()'	'New JavaObject Name'
'Set JavaObject Name'
```


