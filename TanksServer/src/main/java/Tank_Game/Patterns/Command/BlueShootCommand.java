/**
 * @(#) BlueShootCommand.java
 */

package Tank_Game.Patterns.Command;

import Tank_Game.Patterns.Decorator.BlueShoot;
import Tank_Game.Patterns.Decorator.Decorator;
import Tank_Game.Patterns.Decorator.NormalShoot;
import Tank_Game.Tank;

public class BlueShootCommand extends Command implements Cloneable
{

	public BlueShootCommand(Tank tank) {
		this.tank = tank;
		this.target = new NormalShoot(tank);
	}

	public BlueShootCommand(Decorator target) throws CloneNotSupportedException {
		super(target);
	}

	@Override
	public Decorator execute() {

		try {
			this.laterDecorator = (Decorator) this.target.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}

		this.target = new BlueShoot(this.target);
		return this.target;
	}

	@Override
	public Decorator undo() {
		if (this.laterDecorator == null)
			return null;
		this.target = this.laterDecorator;
		return this.laterDecorator;
	}

	@Override
	public Tank undoTank() {
		return super.tank;
	}

}
