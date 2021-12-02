package Tank_Game.Patterns.Command;

import Tank_Game.Patterns.Decorator.Decorator;
import Tank_Game.Patterns.Decorator.NormalShoot;
import Tank_Game.Tank;

public class NormalShootCommand extends Command {
	public NormalShootCommand(final Tank tank) {
		this.tank = tank;
		//this.target = new NormalShoot(tank);
	}

	public NormalShootCommand(final Decorator target) {
		//	this.target = (Decorator) target.clone();
		this.target = target;
	}

	@Override
	public Decorator execute() {
		if (this.target == null) {
			this.target = new NormalShoot(this.tank);
			try {
				this.laterDecorator = (Decorator)this.target.clone();
			} catch (final CloneNotSupportedException e) {
				e.printStackTrace();
			}
			/*System.out.println("Name " + this.target.getIndex() + " type " + (this.target.getType() == 0 ? "ally" : "Enemy"));
			System.out.println("Decorator hashcode " + System.identityHashCode(this.target));
			System.out.println("Cloned decorator hashcode " + System.identityHashCode(this.laterDecorator) + "\n");

			System.out.println("Tank hashcode " + System.identityHashCode(this.target.getTank()));
			System.out.println("Cloned Tank hashcode " + System.identityHashCode(this.laterDecorator.getTank()) + "\n");*/
			return this.target;
		}
		try {
			this.laterDecorator = (Decorator)this.target.clone();
		} catch (final CloneNotSupportedException e) {
			e.printStackTrace();
		}

		this.target = new NormalShoot(target);
		return this.target;
	}

	@Override
	public Decorator undo() {
		this.target = this.laterDecorator;
		return this.laterDecorator;
	}

	public Tank thisTank() {
		return this.tank;
	}

}
