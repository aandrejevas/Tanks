
import utils.Utils;

public class Tank {
	private static int counter = 0;

	public final int index;
	public int x, y;

	public Tank() {
		index = counter++;
		x = Utils.random().nextInt(Main.x_tiles);
		y = Utils.random().nextInt(Main.y_tiles);
	}
}
