/**
 * @(#) RedShootCommand.java
 */

package Tank_Game.Patterns.Command;

import Tank_Game.Patterns.Decorator.Decorator;
import Tank_Game.Patterns.Decorator.NormalShoot;
import Tank_Game.Patterns.Decorator.RedShoot;
import Tank_Game.Tank;

public class RedShootCommand extends Command
{
	public RedShootCommand(Tank tank) {
		super();
		this.tank = tank;
		this.target = new NormalShoot(tank);
	}

	public RedShootCommand(Decorator target) throws CloneNotSupportedException {
		super(target);
	}

	@Override
	public Decorator execute() {

		try {
			this.laterDecorator = (Decorator) this.target.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}

		this.target = new RedShoot(target);
		return this.target;
	}

	@Override
	public Decorator undo() {
		this.target = this.laterDecorator;
		return this.laterDecorator;
	}

	@Override
	public Tank undoTank() {
		return super.tank;
	}

}
