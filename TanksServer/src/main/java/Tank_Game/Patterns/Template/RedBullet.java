package Tank_Game.Patterns.Template;

import Tank_Game.Tank;
import utils.Utils;

public class RedBullet extends Bullet{

    public RedBullet(Tank tank, int side) {
        super(tank, side);
    }

    @Override
    protected int doDamage() {
        return 15;
    }

    @Override
    protected int doDamageArmor() {
        return 10;
    }
}
