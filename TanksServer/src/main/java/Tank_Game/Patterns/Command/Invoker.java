/**
 * @(#) Invoker.java
 */

package Tank_Game.Patterns.Command;

import Tank_Game.Patterns.Decorator.Decorator;
import Tank_Game.Tank;

import java.util.ArrayList;

public class Invoker
{
	private ArrayList<Command> commands = new ArrayList<Command>();

	private Decorator crDecorator;

	public Decorator runCommand(Command command) {
		crDecorator = command.execute();
		commands.add(command);
		return crDecorator;
	}

	public Decorator undoCommand(){
		if (commands.isEmpty())
			return null;
		Command cmd =  commands.remove(commands.size()-1);
		/*if (commands.size() == 1){
			commands.clear();
		}*/
		return cmd.undo();
	}

	public void clearCommands(){
		commands.clear();
	}


	public Tank undoTank(){
		if (commands.isEmpty())
			return null;

		Command cmd =  commands.get(0);
		return cmd.tank;
	}

	public Command popCommand() {
		if (commands.size() == 0){
			return null;
		}
		return commands.remove(commands.size()-1);
	}

	public Command currentCommand() {
		if (commands.size() == 0){
			return null;
		}
		return commands.get(commands.size()-1);
	}

	public Decorator currentDecorator(){
		return crDecorator;
	}
}
