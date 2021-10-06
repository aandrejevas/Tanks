
import java.lang.invoke.MethodHandles;
import java.util.IdentityHashMap;
import java.util.Map;
import java.util.function.Consumer;
import processing.core.PApplet;
import processing.net.Client;
import utils.ObjectPool;
import utils.TOutputStream;
import utils.TServer;
import utils.Utils;

// Server
public class Main extends PApplet {

	public static final Map<Client, Tank> clients = new IdentityHashMap<>();
	public static final int x_tiles = 20, y_tiles = 20, x_tiles_S1 = x_tiles - 1, y_tiles_S1 = y_tiles - 1;
	public static final boolean[][] occupied = new boolean[y_tiles][x_tiles];

	public static TServer this_server;
	public static Client available_client;
	public static TOutputStream client_os;

	public static void main(final String[] args) {
		PApplet.main(MethodHandles.lookup().lookupClass(), args);
	}

	@Override
	public void settings() {
	}

	@Override
	public void setup() {
		surface.setVisible(false);

		ObjectPool.register(Tank.class, Tank::new);

		this_server = new TServer(this, 12345);
	}

	@Override
	public void draw() {
		if (this_server.clientCount != clients.size()) {
			clients.entrySet().removeIf((final Map.Entry<Client, Tank> entry) -> {
				if (!entry.getKey().active()) {
					final Tank tank = entry.getValue();
					tank.unoccupy();
					ObjectPool.returnObject(Tank.class, tank);
					this_server.write(Utils.REMOVE_TANK, tank.index);
					return true;
				} else return false;
			});
		}

		while ((available_client = this_server.available()) != null) {
			client_os = (TOutputStream)available_client.output;
			switch (available_client.read()) {
				case Utils.S_INIT_CLIENT:
					client_os.write(Utils.INITIALIZE_GRID, x_tiles, y_tiles);
					clients.values().forEach((final Tank tank) -> {
						client_os.write(tank.direction, tank.index, tank.x, tank.y);
					});
					break;
				case Utils.S_SPAWN_TANK:
					Utils.readII(available_client);
					if (!occupied[Utils.i2][Utils.i1]) {
						final Tank new_tank = ObjectPool.borrowObject(Tank.class);
						new_tank.occupy(Utils.i1, Utils.i2);
						this_server.write(Utils.ADD_UP_TANK, new_tank.index, new_tank.x, new_tank.y);
						client_os.write(Utils.INITIALIZE);
						clients.put(available_client, new_tank);
					}
					break;
				// <><><><><><><><><><><><><><><> MOVE <><><><><><><><><><><><><><><>
				case Utils.S_MOVE_LEFT:
					handleMove(Tank::moveLeft);
					break;
				case Utils.S_MOVE_RIGHT:
					handleMove(Tank::moveRight);
					break;
				case Utils.S_MOVE_UP:
					handleMove(Tank::moveUp);
					break;
				case Utils.S_MOVE_DOWN:
					handleMove(Tank::moveDown);
					break;
				default: throw new AssertionError();
			}
		}
	}

	public static void handleMove(final Consumer<Tank> func) {
		func.accept(clients.get(available_client));
	}
}
