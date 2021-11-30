package Tank_Game.Patterns.Interpreter;

import Tank_Game.Patterns.Singletone.Game_Context;

public class SetExpression extends Expression {
    public SetExpression() {
        super();
    }

    @Override
    public int execute() {
        if(this.expList.size() >= 2){
            if (this.expList.get(0) instanceof StringExpression &&
                    this.expList.get(1) instanceof IntExpression ){
                String key = ((StringExpression) this.expList.get(0)).getData();
                int val = ((IntExpression) this.expList.get(1)).getData();
                Game_Context.getInstance().setProp(key, val);
                return 0;
            }
        }
        return -1;
    }
}
