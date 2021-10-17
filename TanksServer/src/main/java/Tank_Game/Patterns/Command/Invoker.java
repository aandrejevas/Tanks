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

	public Decorator runCommand(Command command) {
		Decorator dc = command.execute();
		commands.add(command);
		return dc;
	}

	public Decorator undoCommand(){
		if (commands.size() == 0){
			return null;
		}
		Command cmd =  commands.remove(commands.size()-1);
		return cmd.undo();
	}

	public Command popCommand() {
		if (commands.size() == 0){
			return null;
		}
		return commands.get(commands.size()-1);
	}
}
