local blackboard = context:getBlackboard()
local files = blackboard:get("files")
local file_idx = blackboard:get("file_idx") or 0

if(file_idx < files:size()) then
    local file = files:get(file_idx)
    print("file: "..file_idx.."/"..(files:size()-1), file)
    blackboard:set("file", file)
    file_idx = file_idx + 1
    blackboard:set("file_idx", file_idx)
else
    blackboard:set("file", nil)
end

return "terminated"
