
import java.util.IdentityHashMap;
import java.util.Map;
import java.util.function.Function;
import processing.core.PApplet;
import processing.net.Client;
import processing.net.Server;
import utils.IntUnit;
import utils.Utils;

// Server
public class Main extends PApplet {

	public static final Map<Client, Tank> clients = new IdentityHashMap<>();
	public static final int x_tiles = 30, y_tiles = 25;

	public static Server this_server;
	public static Client available_client;

	public static void main(final String[] args) {
		PApplet.main(Main.class, args);
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
				case 1:
					handleMove(4, (final Tank tank) -> tank.x);
					break;
				case 2:
					handleMove(5, (final Tank tank) -> tank.y);
					break;
			}
		}
	}

	public static void handleMove(final int message, final Function<Tank, IntUnit> func) {
		Utils.read(available_client, (final int d) -> {
			final Tank tank = clients.get(available_client);
			this_server.write(Utils.bytes(message, tank.index, func.apply(tank).add(d)));
		});
	}

	public void serverEvent(final Server server, final Client client) {
		client.write(Utils.bytes(1, x_tiles, y_tiles));
		clients.forEach((final Client c, final Tank tank) -> {
			client.write(Utils.bytes(2, tank.index, tank.x(), tank.y()));
		});

		final Tank new_tank = new Tank();
		server.write(Utils.bytes(2, new_tank.index, new_tank.x(), new_tank.y()));
		client.write(Utils.bytes(0));
		clients.put(client, new_tank);
	}

	public void disconnectEvent(final Client client) {
		this_server.write(Utils.bytes(3, clients.get(client).index));
		clients.remove(client);
	}
}
