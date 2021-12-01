package Tank_Game.Patterns.Interpreter;

public class IntExpression extends Expression{

    private int data;

    public IntExpression(int data) {
        this.data = data;
    }

    public int getData() {
        return data;
    }

    @Override
    public int execute() {
        return 0;
    }
}
