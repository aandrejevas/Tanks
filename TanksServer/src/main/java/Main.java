
import java.lang.invoke.MethodHandles;
import java.util.IdentityHashMap;
import java.util.Map;
import java.util.function.Consumer;
import processing.core.PApplet;
import processing.net.Client;
import processing.net.Server;
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
		this_server = new Server(this, 12345);
	}

	@Override
	public void draw() {
		while ((available_client = this_server.available()) != null) {
			switch (available_client.read()) {
				case Utils.S_MOVE_LEFT:
					handleMove(Utils.MOVE_LEFT, (final Tank tank) -> --tank.x);
					break;
				case Utils.S_MOVE_RIGHT:
					handleMove(Utils.MOVE_RIGHT, (final Tank tank) -> ++tank.x);
					break;
				case Utils.S_MOVE_UP:
					handleMove(Utils.MOVE_UP, (final Tank tank) -> --tank.y);
					break;
				case Utils.S_MOVE_DOWN:
					handleMove(Utils.MOVE_DOWN, (final Tank tank) -> ++tank.y);
					break;
			}
		}
	}

	public static void handleMove(final byte message, final Consumer<Tank> func) {
		final Tank tank = clients.get(available_client);
		func.accept(tank);
		this_server.write(Utils.bytes(message, tank.index));
	}

	// https://processing.org/reference/libraries/net/serverEvent_.html
	public void serverEvent(final Server server, final Client client) {
		client.write(Utils.bytes(Utils.INITIALIZE_GRID, x_tiles, y_tiles));
		clients.forEach((final Client c, final Tank tank) -> {
			client.write(Utils.bytes(Utils.ADD_TANK, tank.index, tank.x, tank.y));
		});

		final Tank new_tank = new Tank();
		server.write(Utils.bytes(Utils.ADD_TANK, new_tank.index, new_tank.x, new_tank.y));
		client.write(Utils.INITIALIZE);
		clients.put(client, new_tank);
	}

	// https://processing.org/reference/libraries/net/disconnectEvent_.html
	public void disconnectEvent(final Client client) {
		this_server.write(Utils.bytes(Utils.REMOVE_TANK, clients.get(client).index));
		clients.remove(client);
	}
}
