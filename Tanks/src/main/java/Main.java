
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;
import java.util.function.IntConsumer;
import processing.core.PApplet;
import processing.net.Client;

// Client
public class Main extends PApplet {

	public static final ByteBuffer buffer_4bytes = ByteBuffer.allocate(4),
		buffer_5bytes = ByteBuffer.allocate(5),
		buffer_8bytes = ByteBuffer.allocate(8),
		buffer_12bytes = ByteBuffer.allocate(12);
	public static final int W = 600, H = 500;
	public static final Map<Integer, Tank> tanks = new HashMap<>();

	public static Client this_client;
	public static float scale_x, scale_y;
	public static boolean initialized = false;
	public static int move_state = 0;

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
			switch (move_state) {
				case 0b0001:
					this_client.write(buffer_5bytes.put(0, (byte)1).putInt(1, -1).array());
					break;
				case 0b0010:
					this_client.write(buffer_5bytes.put(0, (byte)1).putInt(1, 1).array());
					break;
				case 0b0100:
					this_client.write(buffer_5bytes.put(0, (byte)2).putInt(1, -1).array());
					break;
				case 0b1000:
					this_client.write(buffer_5bytes.put(0, (byte)2).putInt(1, 1).array());
					break;
			}

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
			case 4:
				client.readBytes(buffer_8bytes.array());
				tanks.get(buffer_8bytes.getInt(0)).updateX(buffer_8bytes.getInt(4));
				break;
			case 5:
				client.readBytes(buffer_8bytes.array());
				tanks.get(buffer_8bytes.getInt(0)).updateY(buffer_8bytes.getInt(4));
				break;
		}
	}

	@Override
	public void keyPressed() {
		updateMoveState((final int bit) -> move_state |= (1 << bit));
	}

	@Override
	public void keyReleased() {
		updateMoveState((final int bit) -> move_state &= ~(1 << bit));
	}

	public void updateMoveState(final IntConsumer action) {
		switch (keyCode) {
			case 'a':
			case 'A':
			case LEFT:
				action.accept(0);
				break;
			case 'd':
			case 'D':
			case RIGHT:
				action.accept(1);
				break;
			case 'w':
			case 'W':
			case UP:
				action.accept(2);
				break;
			case 's':
			case 'S':
			case DOWN:
				action.accept(3);
				break;
		}
	}
}
