package Tank_Game;

import Tank_Game.Patterns.AbstractFactory.*;
import Tank_Game.Patterns.Command.*;
import Tank_Game.Patterns.Decorator.Decorator;
import Tank_Game.Patterns.Factory.*;
import Tank_Game.Patterns.Singletone.Game_Context;
import Tank_Game.Patterns.Strategy.MoveDown;
import Tank_Game.Patterns.Strategy.MoveLeft;
import Tank_Game.Patterns.Strategy.MoveRight;
import Tank_Game.Patterns.Strategy.MoveUp;
import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.IdentityHashMap;
import java.util.Map;
import java.util.function.Consumer;
import processing.core.PApplet;
import processing.net.Client;
import utils.ArenaMap;
import utils.Drop;
import utils.MapBuilder;
import utils.TOutputStream;
import utils.TServer;
import utils.Utils;

// Server
public class Main extends PApplet {

	public static final int edge = 30, seed = Utils.random().nextInt();
	public static final Map<Client, Tank> clients = new IdentityHashMap<>();
	public static final ArrayList<Tank> enemies = new ArrayList();
	public static ArenaMap map = new ArenaMap(edge, true);

	public static Game_Context game_context;
	public static Creator ctr = new PlayerCreator();

	public static TServer this_server;
	public static Client available_client;
	public static TOutputStream client_os;

	public static boolean test = true;

	public static void main(final String[] args) {
		PApplet.main(MethodHandles.lookup().lookupClass(), args);
	}

	@Override
	public void settings() {
	}

	@Override
	public void setup() {
		surface.setVisible(false);
		this_server = new TServer(this, 12345);

		game_context = Game_Context.getInstance();
		//building map
		Utils.random.setSeed(seed);
		map = (new MapBuilder(map)).build(false).getBuildable();

	}

	@Override
	public void draw() {
		if (this_server.clientCount != clients.size()) {
			clients.entrySet().removeIf((final Map.Entry<Client, Tank> entry) -> {
				if (!entry.getKey().active()) {
					this_server.write(Utils.REMOVE_TANK, entry.getValue().index);
					return true;
				} else return false;
			});
		}

		if (!enemies.isEmpty() && frameCount % 30 == 0) {
			enemies.forEach((final Tank tank) -> {
				((AI_Player)tank).AIThink();
			});
		}

		while ((available_client = this_server.available()) != null) {
			client_os = (TOutputStream)available_client.output;
			switch (available_client.read()) {
				case Utils.S_INIT_CLIENT:
					client_os.write(Utils.INITIALIZE_GRID, edge, seed);
					clients.values().forEach((final Tank tank) -> {
						client_os.write(tank.direction[0], tank.index, tank.cord[0], tank.cord[1], tank.type);
					});

					for (int i = 0; i < map.edge; i++) {
						for (int j = 0; j < map.edge; j++) {
							if (map.map[i][j].drop != null) {
								this_server.write(Utils.ADD_DROP, map.map[i][j].drop.getName(), map.map[i][j].drop.getValue(), i, j);
							}
						}
					}

					if (!enemies.isEmpty()) {
						enemies.forEach((final Tank tank) -> {
							client_os.write(tank.direction[0], tank.index, tank.cord[0], tank.cord[1], tank.type);
						});
					}

					final Tank new_player = ctr.factoryMethod(game_context.Player_Count(), true);

					this_server.write(Utils.ADD_UP_TANK, game_context.getPlayer_count(), new_player.cord[0], new_player.cord[1], new_player.type);
					client_os.write(Utils.INITIALIZE);
					clients.put(available_client, new_player);
					break;
				// <><><><><><><><><><><><><><><> MOVE <><><><><><><><><><><><><><><>
				case Utils.S_MOVE_LEFT:
					//handleMove(Tank::moveLeft);
					handleMove(Tank -> Tank.setAlgorithm(new MoveLeft()).move());
					break;
				case Utils.S_MOVE_RIGHT:
					//handleMove(Tank::moveRight);
					handleMove(Tank -> Tank.setAlgorithm(new MoveRight()).move());
					break;
				case Utils.S_MOVE_UP:
					//handleMove(Tank::moveUp);
					handleMove(Tank -> Tank.setAlgorithm(new MoveUp()).move());
					break;
				case Utils.S_MOVE_DOWN:
					//handleMove(Tank::moveDown);
					handleMove(Tank -> Tank.setAlgorithm(new MoveDown()).move());
					break;
				default: throw new AssertionError();
			}
			if (clients.size() > enemies.size()) {
				final Tank new_player = ctr.factoryMethod(game_context.Player_Count(), false);
				this_server.write(Utils.ADD_UP_TANK, game_context.getPlayer_count(), new_player.cord[0], new_player.cord[1], new_player.type);
				enemies.add(new_player);
			}
		}
		//generate drops randomly
		generateDrops();

		//Prototype, command and decorator pattern
		if (test && !enemies.isEmpty()) {
			Tank tank = enemies.get(enemies.size() - 1);

			Invoker invoker = new Invoker();
			try {
				Command cmd = new RedShootCommand(tank);
				Decorator dec = invoker.runCommand(cmd);
				System.out.println("First red decoration: " + dec.getShotType());

				Command cmd2 = new BlueShootCommand(dec);
				Decorator dec2 = invoker.runCommand(cmd2);
				System.out.println("Second blue decoration: " + dec2.getShotType());

				Command cmd3 = new RedShootCommand(dec2);
				Decorator dec3 = invoker.runCommand(cmd3);
				System.out.println("Third red decorator" + dec3.getShotType());

				Decorator decUndo = invoker.undoCommand();
				System.out.println("Undo last decorator: " + decUndo.getShotType());

				Decorator decUndo2 = invoker.undoCommand();
				System.out.println("Undo previous decorator: " + decUndo2.getShotType());

				Decorator decUndo3 = invoker.undoCommand();
				System.out.println("Undo first decorator: " + decUndo3.getShotType());

				System.out.println("Tank without decorations: " + cmd.undoTank().getShotType());

			} catch (CloneNotSupportedException e) {
				e.printStackTrace();
			}
			test = false;
		}
	}

	private static void generateDrops() {
		if (Utils.random().nextInt(1000000) < map.edge * map.edge) {
			final int size = Utils.random().nextInt(300);
			AbstractFactory af;
			if (size < 100) {
				af = new SmallFactory();
			} else if (size < 200) {
				af = new MediumFactory();
			} else {
				af = new LargeFactory();
			}
			final int dropType = Utils.random().nextInt(300);
			Drop drop;
			if (dropType < 100) {
				drop = af.createAmmo();
			} else if (dropType < 200) {
				drop = af.createArmor();
			} else {
				drop = af.createHealth();
			}

			final int[] dropCord = new int[2];

			do {
				dropCord[0] = Utils.random().nextInt(Main.edge);
				dropCord[1] = Utils.random().nextInt(Main.edge);
			} while (Main.map.map[dropCord[1]][dropCord[0]].value != Utils.MAP_EMPTY);
			Main.map.map[dropCord[1]][dropCord[0]].drop = drop;

			this_server.write(Utils.ADD_DROP, (int)drop.getName(), drop.getValue(), dropCord[1], dropCord[0]);
		}
	}

	public static void handleMove(final Consumer<Tank> func) {
		func.accept(clients.get(available_client));
	}

	public static void printMap() {
		for (int i = 0; i < Main.map.edge; i++) {
			for (int j = 0; j < Main.map.edge; j++) {
				if (Main.map.map[i][j].debugValue != 0) {
					switch (Main.map.map[i][j].debugValue) {
						case 1:
							print('+');
							break;
						case 2:
							print('*');
							break;
					}
					Main.map.map[i][j].debugValue = 0;
				} else {
					switch (Main.map.map[i][j].value) {
						case Utils.MAP_WALL:
							print('▒');
							break;
						case Utils.MAP_EMPTY:
							print('░');
							break;
						case Utils.MAP_BORDER:
							print('▓');
							break;
						case Utils.MAP_LAVA:
							print('^');
							break;
						case Utils.MAP_WATER:
							print('0');
							break;
						case Utils.MAP_PLAYER:
							print('X');
							break;
						default:
							print(' ');
							break;
					}
				}
			}
			println();
		}
	}
}
