package Tank_Game;

import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.IdentityHashMap;
import java.util.Map;
import java.util.Random;
import java.util.function.Consumer;

import Tank_Game.Patterns.AbstractFactory.*;
import Tank_Game.Patterns.Factory.Creator;
import Tank_Game.Patterns.Factory.PlayerCreator;
import Tank_Game.Patterns.Singletone.Game_Context;
import Tank_Game.Patterns.Strategy.MoveDown;
import Tank_Game.Patterns.Strategy.MoveLeft;
import Tank_Game.Patterns.Strategy.MoveRight;
import Tank_Game.Patterns.Strategy.MoveUp;
import processing.core.PApplet;
import processing.net.Client;
import processing.net.Server;
import utils.ArenaMap;
import utils.MapBuilder;
import utils.Utils;

// Server
public class Main extends PApplet {

	public static int seed = 3, seedAux = 3;
	public static final int edge = 30;
	public static final Map<Client, Tank> clients = new IdentityHashMap<>();
	public static final ArrayList<Tank> enemies = new ArrayList();
	public static ArenaMap map = new ArenaMap(seed, edge, true);

	public static Game_Context game_context;
	public static Creator ctr = new PlayerCreator();

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

		game_context = Game_Context.getInstance();
		//building map
//		map = new ArenaMap(seed, edge, true);
		map = (new MapBuilder(map)).makeLava().makeWater().makeBorders().makeMaze().getBuildable();

//		for (int i = 0; i < map.edge; i++) {
//			for (int j = 0; j < map.edge; j++) {
//				switch (map.map[i][j].value) {
//					case Utils.MAP_WALL:
//						print('▒');
//						break;
//					case Utils.MAP_EMPTY:
//						print('░');
//						break;
//					case Utils.MAP_BORDER:
//						print('▓');
//						break;
//					case Utils.MAP_LAVA:
//						print('^');
//						break;
//					case Utils.MAP_WATER:
//						print('0');
//						break;
//				}
//			}
//			println();
//		}
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

		if (enemies.size() != 0 && frameCount % 30 == 0){
			enemies.forEach((final Tank tank) -> {
				int rand = new Random().nextInt(4);
				if (rand == 0)
					tank.setAlgorithm(new MoveUp());
				else if (rand == 1)
					tank.setAlgorithm(new MoveDown());
				else if (rand == 2)
					tank.setAlgorithm(new MoveLeft());
				else
					tank.setAlgorithm(new MoveRight());
				tank.move();
			});
		}


		while ((available_client = this_server.available()) != null) {
			switch (available_client.read()) {
				case Utils.S_INIT_CLIENT:
					Utils.send(available_client::write, Utils.INITIALIZE_GRID, edge, seed);
					clients.values().forEach((final Tank tank) -> {
						Utils.send(available_client::write, tank.direction[0], tank.index, tank.cord[0], tank.cord[1], tank.ally_or_enemy);
					});
					if (enemies.size() != 0) {
						enemies.forEach((final Tank tank) -> {
							Utils.send(available_client::write, tank.direction[0], tank.index, tank.cord[0], tank.cord[1], tank.ally_or_enemy);
						});
					}


					final Tank new_player = ctr.factoryMethod(game_context.Player_Count(), true);

					Utils.send(this_server::write, Utils.ADD_UP_TANK, game_context.getPlayer_count(), new_player.cord[0], new_player.cord[1], new_player.ally_or_enemy);
					Utils.send(available_client::write, Utils.INITIALIZE);
					clients.put(available_client, new_player);
					break;
				// <><><><><><><><><><><><><><><> MOVE <><><><><><><><><><><><><><><>
				case Utils.S_MOVE_LEFT:
					//handleMove(Tank::moveLeft);
					handleMove(Tank-> Tank.setAlgorithm(new MoveLeft()).move());
					break;
				case Utils.S_MOVE_RIGHT:
					//handleMove(Tank::moveRight);
					handleMove(Tank-> Tank.setAlgorithm(new MoveRight()).move());
					break;
				case Utils.S_MOVE_UP:
					//handleMove(Tank::moveUp);
					handleMove(Tank-> Tank.setAlgorithm(new MoveUp()).move());
					break;
				case Utils.S_MOVE_DOWN:
					//handleMove(Tank::moveDown);
					handleMove(Tank-> Tank.setAlgorithm(new MoveDown()).move());
					break;
				default: throw new AssertionError();
			}
			if (clients.size() > enemies.size()){
				final Tank new_player = ctr.factoryMethod(game_context.Player_Count(), false);
				Utils.send(this_server::write, Utils.ADD_UP_TANK, game_context.getPlayer_count(), new_player.cord[0], new_player.cord[1], new_player.ally_or_enemy);
				enemies.add(new_player);
			}
		}
		//generate drops randomly
		generateDrops();

	}

	private static void generateDrops() {
		if (GetRandAux() % 10000 < 20) {
			int size = GetRandAux() % 300;
			AbstractFactory af;
			if (size < 100) {
				af = new SmallFactory();
			} else if (size < 200) {
				af = new MediumFactory();
			} else {
				af = new LargeFactory();
			}
			int dropType = GetRandAux() % 300;
			Drop drop;
			if (dropType < 100) {
				drop = af.createAmmo();
			} else if (dropType < 200) {
				drop = af.createArmor();
			} else {
				drop = af.createHealth();
			}

			int[] cord = new int[2];

			do {
				cord[0] = Utils.random().nextInt(Main.edge);
				cord[1] = Utils.random().nextInt(Main.edge);
			} while (Main.map.map[cord[1]][cord[0]].value != Utils.MAP_EMPTY);
			Main.map.map[cord[1]][cord[0]].value = drop.getName();

			Utils.send(this_server::write, Utils.ADD_DROP, (int)drop.getName(), drop.getValue(), cord[1], cord[0]);
		}
	}

	public static void handleMove(final Consumer<Tank> func) {
		func.accept(clients.get(available_client));
	}

	public static int GetRand() {
		seed = ((seed * 1103515245) + 12345) & 0x7fffffff;
		return seed;
	}

	public static int GetRandAux() {
		seedAux = ((seedAux * 1103515245) + 12345) & 0x7fffffff;
		return seedAux;
	}
}
