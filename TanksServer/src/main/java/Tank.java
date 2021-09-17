
import java.util.concurrent.ThreadLocalRandom;
import utils.IntUnit;

public class Tank {
	private static int counter = 0;

	public final int index;
	public final IntUnit x, y;

	public Tank() {
		index = counter++;
		x = new IntUnit(ThreadLocalRandom.current().nextInt(0, Main.x_tiles));
		y = new IntUnit(ThreadLocalRandom.current().nextInt(0, Main.y_tiles));
	}

	public int x() {
		return x.item0;
	}

	public int y() {
		return y.item0;
	}
}
