package Tank_Game.Patterns.Interpreter;

import Tank_Game.Patterns.Singletone.Game_Context;

public class ExitExpression extends Expression {
    @Override
    public int execute() {
        Game_Context.getInstance().setProp("kill", 1);
        return 0;
    }
}
