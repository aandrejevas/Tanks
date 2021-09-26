
import java.lang.invoke.MethodHandles;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.IntConsumer;
import java.util.function.UnaryOperator;
import processing.core.PApplet;
import processing.core.PImage;
import processing.net.Client;
import utils.Utils;

// Client
public class Main extends PApplet {

	public static final int W = 600, H = 600;
	public static final Map<Integer, Tank> tanks = new HashMap<>();
	public static final long timeout = 100_000_000;

	public static PApplet self;
	public static Client this_client;
	public static PImage red_tank;
	public static float scale_x, scale_y;
	public static boolean initialized = false;
	public static int move_state = 0;
	public static long start_time = System.nanoTime();

	public static void main(final String[] args) {
		PApplet.main(MethodHandles.lookup().lookupClass(), args);
	}

	@Override
	public void settings() {
		size(W, H, P2D);
	}

	@Override
	public void setup() {
		hint(DISABLE_ASYNC_SAVEFRAME);
		//hint(DISABLE_OPENGL_ERRORS);
		surface.setResizable(false);

		self = this;
		red_tank = loadImage("tank_test.png");

		this_client = new Client(this, "127.0.0.1", 12345);
	}

	@Override
	public void draw() {
		if (initialized) {
			if (System.nanoTime() - start_time > timeout) {
				switch (move_state) {
					case 0b0001:
						sendMove(Utils.S_MOVE_LEFT);
						break;
					case 0b0010:
						sendMove(Utils.S_MOVE_RIGHT);
						break;
					case 0b0100:
						sendMove(Utils.S_MOVE_UP);
						break;
					case 0b1000:
						sendMove(Utils.S_MOVE_DOWN);
						break;
				}
			}

			background(0xFFFFFFFF);
			tanks.values().forEach((final Tank tank) -> {
				tank.shape.draw(g);
			});
		} else if (this_client.available() != 0) {
			clientEvent(this_client);
		}
	}

	public static void sendMove(final byte message) {
		this_client.write(message);
		start_time = System.nanoTime();
	}

	// https://processing.org/reference/libraries/net/clientEvent_.html
	public static void clientEvent(final Client client) {
		switch (client.read()) {
			case Utils.INITIALIZE:
				initialized = true;
				return;
			case Utils.INITIALIZE_GRID:
				Utils.readII(client);
				scale_x = (float)W / Utils.i1;
				scale_y = (float)H / Utils.i2;
				return;
			case Utils.ADD_LEFT_TANK:
				handleAdd(client, Tank::initLeft);
				return;
			case Utils.ADD_RIGHT_TANK:
				handleAdd(client, Tank::initRight);
				return;
			case Utils.ADD_UP_TANK:
				handleAdd(client, Tank::initUp);
				return;
			case Utils.ADD_DOWN_TANK:
				handleAdd(client, Tank::initDown);
				return;
			case Utils.REMOVE_TANK:
				tanks.remove(Utils.readInt(client));
				return;
			case Utils.MOVE_LEFT:
				handleMove(client, Tank::moveLeft);
				return;
			case Utils.MOVE_RIGHT:
				handleMove(client, Tank::moveRight);
				return;
			case Utils.MOVE_UP:
				handleMove(client, Tank::moveUp);
				return;
			case Utils.MOVE_DOWN:
				handleMove(client, Tank::moveDown);
				return;
			case Utils.POINT_LEFT:
				handleMove(client, Tank::pointLeft);
				return;
			case Utils.POINT_RIGHT:
				handleMove(client, Tank::pointRight);
				return;
			case Utils.POINT_UP:
				handleMove(client, Tank::pointUp);
				return;
			case Utils.POINT_DOWN:
				handleMove(client, Tank::pointDown);
				return;
			case Utils.TURN_LEFT:
				handleMove(client, Tank::turnLeft);
				return;
			case Utils.TURN_RIGHT:
				handleMove(client, Tank::turnRight);
				return;
			case Utils.TURN_UP:
				handleMove(client, Tank::turnUp);
				return;
			case Utils.TURN_DOWN:
				handleMove(client, Tank::turnDown);
				return;
			default: throw new AssertionError();
		}
	}

	public static void handleAdd(final Client client, final UnaryOperator<Tank> func) {
		Utils.readIII(client);
		tanks.put(Utils.i1, func.apply(new Tank(Utils.i2, Utils.i3)));
	}

	public static void handleMove(final Client client, final Consumer<Tank> func) {
		func.accept(tanks.get(Utils.readInt(client)));
	}

	@Override
	public void keyPressed() {
		updateMoveState((final int bit) -> move_state |= bit);
	}

	@Override
	public void keyReleased() {
		updateMoveState((final int bit) -> move_state &= ~bit);
	}

	public static void updateMoveState(final IntConsumer action) {
		switch (self.keyCode) {
			case 'a':
			case 'A':
			case LEFT:
				action.accept(0b0001);
				return;
			case 'd':
			case 'D':
			case RIGHT:
				action.accept(0b0010);
				return;
			case 'w':
			case 'W':
			case UP:
				action.accept(0b0100);
				return;
			case 's':
			case 'S':
			case DOWN:
				action.accept(0b1000);
				return;
		}
	}
}
