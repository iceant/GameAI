print("die_Update")
local blackboard = context:getBlackboard()
local times = blackboard:get("Times") or 0
times = times + 1
blackboard:set("Times", times)

if(times==10) then
    return "TERMINATED"
else
    return "running"
end