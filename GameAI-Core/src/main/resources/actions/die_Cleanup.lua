local GameAI = require("game.ai.lua.GameAI")
print("die_Cleanup")
print("Context", context)
print("Name", context:getBlackboard():get("Name", context))
return GameAI.exit(0)
