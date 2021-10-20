package Tank_Game.Patterns.Command;

import Tank_Game.Patterns.Decorator.Decorator;
import Tank_Game.Patterns.Decorator.NormalShoot;
import Tank_Game.Tank;

public class NormalShootCommand extends Command
{
	public NormalShootCommand(Tank tank) {
		super();
		this.tank = tank;
		//this.target = new NormalShoot(tank);
	}

	public NormalShootCommand(Decorator target) throws CloneNotSupportedException {
		//	this.target = (Decorator) target.clone();
		this.target = target;
	}

	@Override
	public Decorator execute() {
		if(this.target == null){
			this.target = new NormalShoot(this.tank);
			return this.target;
		}
		try {
			this.laterDecorator = (Decorator) this.target.clone();
		} catch (CloneNotSupportedException e) {
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

	public Tank thisTank(){
		return this.tank;
	}

}