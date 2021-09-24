
import java.lang.invoke.MethodHandles;
import java.util.IdentityHashMap;
import java.util.Map;
import processing.core.PApplet;
import processing.net.Client;
import processing.net.Server;
import utils.ToByteFunction;
import utils.Utils;

// Server
public class Main extends PApplet {

	public static final Map<Client, Tank> clients = new IdentityHashMap<>();
	public static final int x_tiles = 20, y_tiles = 20;

	public static Server this_server;
	public static Client available_client;

	public static void main(final String[] args) {
		PApplet.main(MethodHandles.lookup().lookupClass(), args);
	}

	@Override
	public void settings() {
	}

	@Override
	public void setup() {
		surface.setVisible(false);

		this_server = new Server(this, 12345);
	}

	@Override
	public void draw() {
		while ((available_client = this_server.available()) != null) {
			switch (available_client.read()) {
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

	public static void handleMove(final ToByteFunction<Tank> func) {
		final Tank tank = clients.get(available_client);
		Utils.write(this_server::write, func.applyAsByte(tank), tank.index);
	}

	// https://processing.org/reference/libraries/net/serverEvent_.html
	public static void serverEvent(final Server server, final Client client) {
		Utils.write(client::write, Utils.INITIALIZE_GRID, x_tiles, y_tiles);
		clients.values().forEach((final Tank tank) -> {
			Utils.write(client::write, tank.getAddMessage(), tank.index, tank.x, tank.y);
		});

		final Tank new_tank = new Tank();
		Utils.write(server::write, Utils.ADD_UP_TANK, new_tank.index, new_tank.x, new_tank.y);
		client.write(Utils.INITIALIZE);
		clients.put(client, new_tank);
	}

	// https://processing.org/reference/libraries/net/disconnectEvent_.html
	public static void disconnectEvent(final Client client) {
		Utils.write(this_server::write, Utils.REMOVE_TANK, clients.get(client).index);
		clients.remove(client);
	}
}
