package Tank_Game.Patterns.Command;

import Tank_Game.Patterns.Decorator.BlueShoot;
import Tank_Game.Patterns.Decorator.Decorator;
import Tank_Game.Tank;

public class BlueShootCommand extends Command {

	public BlueShootCommand(final Tank tank) {
		this.tank = tank;
		//this.target = new NormalShoot(tank);
	}

	public BlueShootCommand(final Decorator target) {
		//super(target);
		//this.target = (Decorator) target.clone();
		this.target = target;
	}

	@Override
	public Decorator execute() {
		if (this.target == null) {
			this.target = new BlueShoot(this.tank);
			return this.target;
		}
		try {
			this.laterDecorator = (Decorator)this.target.clone();
		} catch (final CloneNotSupportedException e) {
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
}
