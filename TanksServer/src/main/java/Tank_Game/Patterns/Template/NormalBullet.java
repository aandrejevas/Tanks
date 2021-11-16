package Tank_Game.Patterns.Template;

import Tank_Game.Tank;
import utils.Utils;

public class NormalBullet extends Bullet {

    public NormalBullet(Tank tank, int side) {
        super(tank, side);
    }

    @Override
    protected byte doDamage() {
        return Utils.SHOT_NORMAL;
    }

}
