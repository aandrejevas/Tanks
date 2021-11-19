package Tank_Game.Patterns.Template;

import Tank_Game.Tank;
import utils.Utils;

public class BlueBullet extends Bullet{

    public BlueBullet(Tank tank, int side) {
        super(tank, side);
    }

    @Override
    protected int doDamage() {
        return 10;
    }

    @Override
    protected int doDamageArmor() {
        return 15;
    }
}




