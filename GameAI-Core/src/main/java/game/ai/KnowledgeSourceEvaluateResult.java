package game.ai;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class KnowledgeSourceEvaluateResult {
    Double confidence = 0.0d;
    Object evaluation;
}
