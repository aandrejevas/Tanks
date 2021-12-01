package Tank_Game.Patterns.Interpreter;

import java.util.ArrayList;

public abstract class Expression {

    public ArrayList<Expression> expList = new ArrayList<Expression>();

    public abstract int execute();

    public static boolean isFunction(String token){
        if (token.equals("set"))
            return true;
        else if (token.equals("exit"))
            return true;
        else
            return false;
    }

    public static boolean isFE(String token){
        return token.equals("fe");
    }

    public static boolean isInt(String token){
        try {
            Integer.parseInt(token);
            return true;
        } catch(NumberFormatException | NullPointerException e) {
            return false;
        }
    }
}
