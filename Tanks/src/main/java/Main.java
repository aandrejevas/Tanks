
import java.lang.invoke.MethodHandles;
import java.util.HashMap;
import java.util.Map;
import java.util.function.IntConsumer;
import processing.core.PApplet;
import processing.core.PImage;
import processing.net.Client;
import utils.Utils;

// Client
public class Main extends PApplet {

	public static final int W = 600, H = 600;
	public static final Map<Integer, Tank> tanks = new HashMap<>();

	public static Client this_client;
	public static PImage red_tank;
	public static float scale_x, scale_y;
	public static boolean initialized = false;
	public static int move_state = 0;

	public static void main(final String[] args) {
		PApplet.main(MethodHandles.lookup().lookupClass(), args);
	}

	@Override
	public void settings() {
		size(W, H, P2D);
	}

	@Override
	public void setup() {
		red_tank = loadImage("tank_test.png");

		this_client = new Client(this, "127.0.0.1", 12345);
	}

	@Override
	public void draw() {
		if (initialized) {
			switch (move_state) {
				case 0b0001:
					this_client.write(Utils.S_MOVE_LEFT);
					break;
				case 0b0010:
					this_client.write(Utils.S_MOVE_RIGHT);
					break;
				case 0b0100:
					this_client.write(Utils.S_MOVE_UP);
					break;
				case 0b1000:
					this_client.write(Utils.S_MOVE_DOWN);
					break;
			}

			background(0xFFFFFFFF);
			tanks.forEach((final Integer i, final Tank tank) -> {
				tank.shape.draw(g);
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
				Utils.readII(client);
				scale_x = (float)W / Utils.i1;
				scale_y = (float)H / Utils.i2;
				break;
			case Utils.ADD_TANK:
				Utils.readIII(client);
				tanks.put(Utils.i1, new Tank(this, Utils.i2, Utils.i3));
				break;
			case Utils.REMOVE_TANK:
				tanks.remove(Utils.readInt(client));
				break;
			case Utils.MOVE_LEFT:
				tanks.get(Utils.readInt(client)).moveLeft();
				break;
			case Utils.MOVE_RIGHT:
				tanks.get(Utils.readInt(client)).moveRight();
				break;
			case Utils.MOVE_UP:
				tanks.get(Utils.readInt(client)).moveUp();
				break;
			case Utils.MOVE_DOWN:
				tanks.get(Utils.readInt(client)).moveDown();
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
