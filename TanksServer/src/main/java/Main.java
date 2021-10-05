
import java.lang.invoke.MethodHandles;
import java.util.IdentityHashMap;
import java.util.Map;
import java.util.function.Consumer;
import processing.core.PApplet;
import processing.net.Client;
import processing.net.Server;
import utils.ArenaMap;
import utils.MapBuilder;
import utils.Utils;

// Server
public class Main extends PApplet {

	public static int seed = 3;
	public static final int edge = 30;
	public static final Map<Client, Tank> clients = new IdentityHashMap<>();
	public static ArenaMap map = new ArenaMap(seed, edge, true);


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

		//building map

//		map = new ArenaMap(seed, edge, true);
		map = (new MapBuilder(map)).makeLava().makeWater().makeBorders().makeMaze().getBuildable();
	}

	@Override
	public void draw() {
		if (this_server.clientCount != clients.size()) {
			clients.entrySet().removeIf((final Map.Entry<Client, Tank> entry) -> {
				if (!entry.getKey().active()) {
					Utils.send(this_server::write, Utils.REMOVE_TANK, entry.getValue().index);
					return true;
				} else return false;
			});
		}

		while ((available_client = this_server.available()) != null) {
			switch (available_client.read()) {
				case Utils.S_INIT_CLIENT:
					Utils.send(available_client::write, Utils.INITIALIZE_GRID, edge, seed);
					clients.values().forEach((final Tank tank) -> {
						Utils.send(available_client::write, tank.direction, tank.index, tank.x, tank.y);
					});

					final Tank new_tank = new Tank();
					Utils.send(this_server::write, Utils.ADD_UP_TANK, new_tank.index, new_tank.x, new_tank.y);
					Utils.send(available_client::write, Utils.INITIALIZE);
					clients.put(available_client, new_tank);
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

	public static int GetRand() {
		seed = ((seed * 1103515245) + 12345) & 0x7fffffff;
		return seed;
	}
}
