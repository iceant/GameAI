package game.ai;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FiniteStateTransition {
    String toStateName;
    Evaluator evaluator;
}
