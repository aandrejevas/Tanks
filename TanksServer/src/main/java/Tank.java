
import java.util.concurrent.ThreadLocalRandom;

public class Tank {
	private static int counter = 0;

	public final int index;
	public int x, y;

	public Tank() {
		index = counter++;
		x = ThreadLocalRandom.current().nextInt(0, Main.x_tiles);
		y = ThreadLocalRandom.current().nextInt(0, Main.y_tiles);
	}
}
