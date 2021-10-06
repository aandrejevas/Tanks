
import java.lang.invoke.MethodHandles;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.IntConsumer;
import processing.core.PApplet;
import processing.core.PImage;
import processing.net.Client;
import utils.ObjectPool;
import utils.TOutputStream;
import utils.Utils;

// Client
public class Main extends PApplet {

	public static final int W = 600, H = 600;
	public static final Map<Integer, Tank> tanks = new HashMap<>();
	public static final long timeout = 100_000_000;

	public static PApplet self;
	public static Client this_client;
	public static TOutputStream this_os;
	public static PImage red_tank;
	public static float scale_x, scale_y;
	public static boolean initialized = false;
	public static int move_state = 0;
	public static long start_time;

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
		this_client.output = (this_os = new TOutputStream(this_client));
		this_os.write(Utils.S_INIT_CLIENT);
	}

	@Override
	public void draw() {
		while (this_client.available() != 0) {
			switch (this_client.read()) {
				case Utils.INITIALIZE:
					start_time = System.nanoTime() - timeout;
					initialized = true;
					break;
				case Utils.INITIALIZE_GRID:
					Utils.readII(this_client);
					scale_x = (float)W / Utils.i1;
					scale_y = (float)H / Utils.i2;
					ObjectPool.register(Tank.class, Tank::new);
					break;
				// <><><><><><><><><><><><><><><> ADD <><><><><><><><><><><><><><><>
				case Utils.ADD_LEFT_TANK:
					handleAdd(Tank::pointLeft);
					break;
				case Utils.ADD_RIGHT_TANK:
					handleAdd(Tank::pointRight);
					break;
				case Utils.ADD_UP_TANK:
					handleAdd(Tank::pointUp);
					break;
				case Utils.ADD_DOWN_TANK:
					handleAdd(Tank::pointDown);
					break;
				case Utils.REMOVE_TANK:
					ObjectPool.returnObject(Tank.class, tanks.remove(Utils.readInt(this_client)));
					break;
				// <><><><><><><><><><><><><><><> MOVE <><><><><><><><><><><><><><><>
				case Utils.MOVE_LEFT:
					handleMove(Tank::moveLeft);
					break;
				case Utils.MOVE_RIGHT:
					handleMove(Tank::moveRight);
					break;
				case Utils.MOVE_UP:
					handleMove(Tank::moveUp);
					break;
				case Utils.MOVE_DOWN:
					handleMove(Tank::moveDown);
					break;
				// <><><><><><><><><><><><><><><> POINT <><><><><><><><><><><><><><><>
				case Utils.POINT_LEFT:
					handleMove(Tank::pointLeft);
					break;
				case Utils.POINT_RIGHT:
					handleMove(Tank::pointRight);
					break;
				case Utils.POINT_UP:
					handleMove(Tank::pointUp);
					break;
				case Utils.POINT_DOWN:
					handleMove(Tank::pointDown);
					break;
				// <><><><><><><><><><><><><><><> TURN <><><><><><><><><><><><><><><>
				case Utils.TURN_LEFT:
					handleMove(Tank::turnLeft);
					break;
				case Utils.TURN_RIGHT:
					handleMove(Tank::turnRight);
					break;
				case Utils.TURN_UP:
					handleMove(Tank::turnUp);
					break;
				case Utils.TURN_DOWN:
					handleMove(Tank::turnDown);
					break;
				default: throw new AssertionError();
			}
		}

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
		}

		background(0xFFFFFFFF);
		tanks.values().forEach((final Tank tank) -> {
			tank.shape.draw(g);
		});
	}

	public static void sendMove(final byte message) {
		this_os.write(message);
		start_time = System.nanoTime();
	}

	public static void handleAdd(final Consumer<Tank> func) {
		Utils.readIII(this_client);
		final Tank tank = ObjectPool.borrowObject(Tank.class);
		func.accept(tank);
		tank.move(Utils.i2, Utils.i3);
		tanks.put(Utils.i1, tank);
	}

	public static void handleMove(final Consumer<Tank> func) {
		func.accept(tanks.get(Utils.readInt(this_client)));
	}

	@Override
	public void mousePressed() {
		if (!initialized) {
			this_os.write(Utils.S_SPAWN_TANK, (int)(mouseX / scale_x), (int)(mouseY / scale_y));
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
