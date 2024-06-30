local JavaObject = luajava.bindClass('test.JavaObject')
print(JavaObject.staticField)
JavaObject:sayHello("Hello from Lua")
local JavaObjectInstance = luajava.new(JavaObject, "New JavaObject Name")
print("JavaObjectInstance", JavaObjectInstance)
print("JavaObjectInstance:getName()", JavaObjectInstance:getName())
JavaObjectInstance:setName("Set JavaObject Name")
print(JavaObjectInstance:getName())

