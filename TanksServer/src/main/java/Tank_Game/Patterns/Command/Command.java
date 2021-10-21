package Tank_Game.Patterns.Command;

import Tank_Game.Patterns.Decorator.Decorator;
import Tank_Game.Tank;

public abstract class Command implements Cloneable {
	protected Decorator target;
	protected Decorator laterDecorator;

	protected Tank tank;

	public Command() {
	}

	public abstract Decorator execute();

	public abstract Decorator undo();
}
