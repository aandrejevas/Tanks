package Tank_Game.Patterns.Interpreter;

public class StringExpression extends Expression{

    private String data;

    public StringExpression(String data) {
        this.data = data;
    }

    public String getData() {
        return data;
    }

    @Override
    public int execute() {
        return 0;
    }
}
