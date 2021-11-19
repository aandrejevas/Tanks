package Tank_Game.Patterns.Template;

import Tank_Game.Tank;
import utils.Utils;

public class NormalBullet extends Bullet {

    public NormalBullet(Tank tank, int side) {
        super(tank, side);
    }

    @Override
    protected int doDamage() {
        return 5;
    }

    @Override
    protected int doDamageArmor() {
        return 5;
    }
}
