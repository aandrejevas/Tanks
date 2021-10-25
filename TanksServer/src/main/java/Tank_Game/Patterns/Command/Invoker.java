package Tank_Game.Patterns.Command;

import Tank_Game.Patterns.Decorator.Decorator;
import Tank_Game.Tank;
import java.util.ArrayList;
import java.util.List;

public class Invoker {
	private final List<Command> commands = new ArrayList<>();

	private Decorator crDecorator;

	public Decorator runCommand(final Command command) {
		crDecorator = command.execute();
		commands.add(command);
		return crDecorator;
	}

	public Decorator undoCommand() {
		if (commands.isEmpty())
			return null;
		Command cmd = commands.remove(commands.size() - 1);
		/*if (commands.size() == 1){
			commands.clear();
		}*/
		crDecorator = cmd.undo();
		return crDecorator;
	}

	public void clearCommands() {
		commands.clear();
	}

	public Tank undoTank() {
		if (commands.isEmpty())
			return null;

		final Command cmd = commands.get(0);
		return cmd.tank;
	}

	public Command popCommand() {
		if (commands.isEmpty()) {
			return null;
		}
		return commands.remove(commands.size() - 1);
	}

	public Command currentCommand() {
		if (commands.isEmpty()) {
			return null;
		}
		return commands.get(commands.size() - 1);
	}

	public Decorator currentDecorator() {
		return crDecorator;
	}
}
