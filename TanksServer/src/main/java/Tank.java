
import utils.IntUnit;
import utils.Utils;

public class Tank {
	private static int counter = 0;

	public final int index;
	public final IntUnit x, y;

	public Tank() {
		index = counter++;
		x = new IntUnit(Utils.random().nextInt(Main.x_tiles));
		y = new IntUnit(Utils.random().nextInt(Main.y_tiles));
	}

	public int x() {
		return x.item0;
	}

	public int y() {
		return y.item0;
	}
}
