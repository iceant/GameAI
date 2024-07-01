local blackboard = context:getBlackboard()
local CodeAI = require("ai.CodeAI")
local prettyPrint = require("libs.pretty-print")

local folder = [[M:\Workspace\BJSN]]

local files = blackboard:get("files") or nil
if(files == nil) then
    print("list files in "..folder)
    files = CodeAI.list_files(folder, function(file) 
            return true
        end);
    blackboard:set("files", files)
end

return "terminated"
