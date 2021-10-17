/**
 * @(#) ICommand.java
 */

package Tank_Game.Patterns.Command;

import Tank_Game.Patterns.Decorator.Decorator;
import Tank_Game.Patterns.Decorator.NormalShoot;
import Tank_Game.Tank;

public abstract class Command implements Cloneable
{
	protected Decorator target;
	protected Decorator laterDecorator;

	protected Tank tank;

	public Command() {

	}


	public abstract Decorator execute( );
	
	public abstract Decorator undo( );

	public abstract Tank undoTank();

	public Command(Decorator target) throws CloneNotSupportedException {
		this.target = new NormalShoot(target); //this.laterDecorator.clone();
	}

}
