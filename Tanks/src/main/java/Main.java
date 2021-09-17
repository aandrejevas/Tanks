
import java.lang.invoke.MethodHandles;
import java.util.HashMap;
import java.util.Map;
import java.util.function.IntConsumer;
import processing.core.PApplet;
import processing.net.Client;
import utils.Utils;

// Client
public class Main extends PApplet {

	public static final int W = 600, H = 500;
	public static final Map<Integer, Tank> tanks = new HashMap<>();

	public static Client this_client;
	public static float scale_x, scale_y;
	public static boolean initialized = false;
	public static int move_state = 0;

	public static void main(final String[] args) {
		PApplet.main(MethodHandles.lookup().lookupClass(), args);
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
					this_client.write(Utils.bytes(Utils.MOVE_X, -1));
					break;
				case 0b0010:
					this_client.write(Utils.bytes(Utils.MOVE_X, 1));
					break;
				case 0b0100:
					this_client.write(Utils.bytes(Utils.MOVE_Y, -1));
					break;
				case 0b1000:
					this_client.write(Utils.bytes(Utils.MOVE_Y, 1));
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

	// https://processing.org/reference/libraries/net/clientEvent_.html
	public void clientEvent(final Client client) {
		switch (client.read()) {
			case Utils.INITIALIZE:
				initialized = true;
				break;
			case Utils.INITIALIZE_GRID:
				Utils.read(client, (final int x_tiles, final int y_tiles) -> {
					scale_x = (float)W / x_tiles;
					scale_y = (float)H / y_tiles;
				});
				break;
			case Utils.ADD_TANK:
				Utils.read(client, (final int index, final int x, final int y) -> tanks.put(index, new Tank(this, x, y)));
				break;
			case Utils.REMOVE_TANK:
				Utils.read(client, (final int index) -> tanks.remove(index));
				break;
			case Utils.UPDATE_X:
				Utils.read(client, (final int index, final int x) -> tanks.get(index).updateX(x));
				break;
			case Utils.UPDATE_Y:
				Utils.read(client, (final int index, final int y) -> tanks.get(index).updateY(y));
				break;
		}
	}

	@Override
	public void keyPressed() {
		updateMoveState((final int bit) -> move_state |= bit);
	}

	@Override
	public void keyReleased() {
		updateMoveState((final int bit) -> move_state &= ~bit);
	}

	public void updateMoveState(final IntConsumer action) {
		switch (keyCode) {
			case 'a':
			case 'A':
			case LEFT:
				action.accept(0b0001);
				break;
			case 'd':
			case 'D':
			case RIGHT:
				action.accept(0b0010);
				break;
			case 'w':
			case 'W':
			case UP:
				action.accept(0b0100);
				break;
			case 's':
			case 'S':
			case DOWN:
				action.accept(0b1000);
				break;
		}
	}
}
