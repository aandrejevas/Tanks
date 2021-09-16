
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;
import processing.core.PApplet;
import processing.net.Client;

// Client
public class Main extends PApplet {

	public static final ByteBuffer buffer_4bytes = ByteBuffer.allocate(4),
		buffer_8bytes = ByteBuffer.allocate(8),
		buffer_12bytes = ByteBuffer.allocate(12);
	public static final int W = 600, H = 500;
	public static final Map<Integer, Tank> tanks = new HashMap<>();

	public static Client this_client;
	public static float scale_x, scale_y;
	public static boolean initialized = false;

	public static void main(final String[] args) {
		PApplet.main(Main.class, args);
	}

	@Override
	public void settings() {
		size(W, H);
	}

	@Override
	public void setup() {
		this_client = new Client(this, "127.0.0.1", 12345);
	}

	@Override
	public void draw() {
		if (initialized) {
			background(0xFFFFFFFF);
			tanks.forEach((final Integer i, final Tank tank) -> {
				shape(tank.shape);
			});
		} else if (this_client.available() != 0) {
			clientEvent(this_client);
		}
	}

	public void clientEvent(final Client client) {
		switch (client.read()) {
			case 0:
				initialized = true;
				break;
			case 1:
				client.readBytes(buffer_8bytes.array());
				scale_x = (float)W / buffer_8bytes.getInt(0);
				scale_y = (float)H / buffer_8bytes.getInt(4);
				break;
			case 2:
				client.readBytes(buffer_12bytes.array());
				tanks.put(buffer_12bytes.getInt(0), new Tank(this, buffer_12bytes.getInt(4), buffer_12bytes.getInt(8)));
				break;
			case 3:
				client.readBytes(buffer_4bytes.array());
				tanks.remove(buffer_4bytes.getInt(0));
				break;
		}
	}
}
