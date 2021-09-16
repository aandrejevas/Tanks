
import java.nio.ByteBuffer;
import java.util.IdentityHashMap;
import java.util.Map;
import processing.core.PApplet;
import processing.net.Client;
import processing.net.Server;

// Server
public class Main extends PApplet {

	public static final ByteBuffer buffer_1bytes = ByteBuffer.allocate(1),
		buffer_5bytes = ByteBuffer.allocate(5),
		buffer_9bytes = ByteBuffer.allocate(9),
		buffer_13bytes = ByteBuffer.allocate(13);
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
		}
	}

	public void serverEvent(final Server server, final Client client) {
		client.write(buffer_9bytes.put(0, (byte)1).putInt(1, x_tiles).putInt(5, y_tiles).array());
		clients.forEach((final Client c, final Tank tank) -> {
			client.write(buffer_13bytes.put(0, (byte)2).putInt(1, tank.index).putInt(5, tank.x).putInt(9, tank.y).array());
		});

		final Tank new_tank = new Tank();
		server.write(buffer_13bytes.put(0, (byte)2).putInt(1, new_tank.index).putInt(5, new_tank.x).putInt(9, new_tank.y).array());
		client.write(buffer_1bytes.put(0, (byte)0).array());
		clients.put(client, new_tank);
	}

	public void disconnectEvent(final Client client) {
		this_server.write(buffer_5bytes.put(0, (byte)3).putInt(1, clients.get(client).index).array());
		clients.remove(client);
	}
}
