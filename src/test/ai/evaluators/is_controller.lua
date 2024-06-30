local blackboard = context:getBlackboard()
local file = blackboard:get("file")
if(file) then
    local fileName = file:getName()
    if(fileName:find(".lua")~=nil) then
        return 1
    else
        return 0
    end
end
return 0

