print("die_Initialize")
print("Context", context)
print("Blackboard", context:getBlackboard())
--context:getBlackboard():set("Name", "Chen Peng")
local count = context:getBlackboard():get("Count") or 0
count = count + 1
context:getBlackboard():set("Count", count)
context:getBlackboard():addLuaSource("Name", function(ctx) return {1, "I'm "..count} end, 10000)

