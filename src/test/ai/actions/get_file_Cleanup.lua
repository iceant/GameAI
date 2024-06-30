local CodeAI = require("CodeAI")
local blackboard = context:getBlackboard()
if(blackboard:get("file")==nil) then
    --local sys = luajava.bindClass('java.lang.System')
    --sys:exit(0)
    CodeAI.exit(0)
end