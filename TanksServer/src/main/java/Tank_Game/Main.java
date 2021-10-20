package Tank_Game;

import Tank_Game.Patterns.AbstractFactory.AbstractFactory;
import Tank_Game.Patterns.AbstractFactory.LargeFactory;
import Tank_Game.Patterns.AbstractFactory.MediumFactory;
import Tank_Game.Patterns.AbstractFactory.SmallFactory;
import Tank_Game.Patterns.Command.Command;
import Tank_Game.Patterns.Command.Invoker;
import Tank_Game.Patterns.Command.NormalShootCommand;
import Tank_Game.Patterns.Decorator.Decorator;
import Tank_Game.Patterns.Factory.AI_Player;
import Tank_Game.Patterns.Factory.Creator;
import Tank_Game.Patterns.Factory.PlayerCreator;
import Tank_Game.Patterns.Singletone.Game_Context;
import Tank_Game.Patterns.Strategy.MoveDown;
import Tank_Game.Patterns.Strategy.MoveLeft;
import Tank_Game.Patterns.Strategy.MoveRight;
import Tank_Game.Patterns.Strategy.MoveUp;
import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import processing.core.PApplet;
import processing.net.Client;
import utils.ArenaBlock;
import utils.ArenaMap;
import utils.Drop;
import utils.MapBuilder;
import utils.TOutputStream;
import utils.TServer;
import utils.Utils;

// Server
public class Main extends PApplet {

	public static final int edge = 30, seed = Utils.random().nextInt();
	public static final Map<Client, Invoker> clients = new IdentityHashMap<>();
	public static final List<Bullet> bullets = new LinkedList<>();
	public static final List<Invoker> enemies = new ArrayList<>();
	public static ArenaMap map = new ArenaMap(edge, true);

	public static Game_Context game_context;
	public static final Creator ctr = new PlayerCreator();

	public static TServer this_server;
	public static Client available_client;
	public static TOutputStream client_os;

	//public static boolean test = false;
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
			clients.entrySet().removeIf((final Map.Entry<Client, Invoker> entry) -> {
				if (!entry.getKey().active()) {
					final Decorator tank = entry.getValue().currentDecorator();
					final ArenaBlock block = map.map[tank.getY()][tank.getX()];
					block.value = block.defValue;
					block.obstacle = false;
					this_server.write(Utils.REMOVE_TANK, tank.getIndex());
					return true;
				} else return false;
			});
		}

		if (!enemies.isEmpty() && frameCount % 30 == 0) {
			enemies.forEach((final Invoker tank) -> {
				((AI_Player)tank.undoTank()).AIThink();
			});
		}

		final Iterator<Bullet> iter = bullets.iterator();
		while (iter.hasNext()) {
			final Bullet bullet = iter.next();
			if (bullet.move()) {
				this_server.write(Utils.REMOVE_BULLET, bullet.index);
				iter.remove();
			}
		}

		while ((available_client = this_server.available()) != null) {
			client_os = (TOutputStream)available_client.output;
			switch (available_client.read()) {
				case Utils.S_INIT_CLIENT:
					client_os.write(Utils.INITIALIZE_GRID, edge, seed);
					clients.values().forEach((final Invoker tank) -> {
						client_os.write(tank.currentDecorator().getDirection(), tank.currentDecorator().getIndex(), tank.currentDecorator().getX(), tank.currentDecorator().getY(), tank.currentDecorator().getType());
					});

					for (int i = 0; i < map.edge; i++) {
						for (int j = 0; j < map.edge; j++) {
							if (map.map[i][j].drop != null) {
								this_server.write(Utils.ADD_DROP, map.map[i][j].drop.getName(), map.map[i][j].drop.getValue(), i, j);
							}
						}
					}

					enemies.forEach((final Invoker tank) -> {
						client_os.write(tank.currentDecorator().getDirection(), tank.currentDecorator().getIndex(), tank.currentDecorator().getX(), tank.currentDecorator().getY(), tank.currentDecorator().getType());
					});

					final Tank new_player = ctr.factoryMethod(game_context.Player_Count(), true);

					this_server.write(Utils.ADD_UP_TANK, game_context.getPlayer_count(), new_player.getX(), new_player.getY(), new_player.getType());
					client_os.write(Utils.INITIALIZE);
					final Invoker invoker = new Invoker();
					final Command cmd = new NormalShootCommand(new_player);
					invoker.runCommand(cmd);
					clients.put(available_client, invoker);
					break;
				// <><><><><><><><><><><><><><><> MOVE <><><><><><><><><><><><><><><>
				case Utils.S_MOVE_LEFT:
					//handleMove(Tank::moveLeft);
					handleMove(Tank -> Tank.setAlgorithm(MoveLeft.instance).move());
					break;
				case Utils.S_MOVE_RIGHT:
					//handleMove(Tank::moveRight);
					handleMove(Tank -> Tank.setAlgorithm(MoveRight.instance).move());
					break;
				case Utils.S_MOVE_UP:
					//handleMove(Tank::moveUp);
					handleMove(Tank -> Tank.setAlgorithm(MoveUp.instance).move());
					break;
				case Utils.S_MOVE_DOWN:
					//handleMove(Tank::moveDown);
					handleMove(Tank -> Tank.setAlgorithm(MoveDown.instance).move());
					break;
				case Utils.S_SHOOT:
					final Tank tank = clients.get(available_client).currentDecorator();
					switch (tank.getDirection()) {
						case Tank.LEFT:
							if (!map.map[tank.getX() - 1][tank.getY()].obstacle) bullets.add(new Bullet.Left(tank));
							break;
						case Tank.RIGHT:
							if (!map.map[tank.getX() + 1][tank.getY()].obstacle) bullets.add(new Bullet.Right(tank));
							break;
						case Tank.UP:
							if (!map.map[tank.getX()][tank.getY() - 1].obstacle) bullets.add(new Bullet.Up(tank));
							break;
						case Tank.DOWN:
							if (!map.map[tank.getX()][tank.getY() + 1].obstacle) bullets.add(new Bullet.Down(tank));
							break;
					}
					break;
				default: throw new AssertionError();
			}
			if (clients.size() > enemies.size()) {
				final Tank new_player = ctr.factoryMethod(game_context.Player_Count(), false);
				this_server.write(Utils.ADD_UP_TANK, game_context.getPlayer_count(), new_player.getX(), new_player.getY(), new_player.getType());
				final Invoker invoker = new Invoker();
				final Command cmd = new NormalShootCommand(new_player);
				invoker.runCommand(cmd);
				enemies.add(invoker);
			}
		}
		//generate drops randomly
		generateDrops();

		//Prototype, command and decorator pattern
		/*if (test && !enemies.isEmpty()) {
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
		}*/
	}

	private static void generateDrops() {
		if (Utils.random().nextInt(1000000) < map.edge * map.edge) {
			final int size = Utils.random().nextInt(300);
			final AbstractFactory af = (size < 100 ? new SmallFactory() : (size < 200 ? new MediumFactory() : new LargeFactory()));

			final int dropType = Utils.random().nextInt(300);
			final Drop drop = (dropType < 100 ? af.createAmmo() : (dropType < 200 ? af.createArmor() : af.createHealth()));

			final int[] dropCord = new int[2];

			do {
				dropCord[0] = Utils.random().nextInt(Main.edge);
				dropCord[1] = Utils.random().nextInt(Main.edge);
			} while (Main.map.map[dropCord[1]][dropCord[0]].value != Utils.MAP_EMPTY);
			Main.map.map[dropCord[1]][dropCord[0]].drop = drop;

			this_server.write(Utils.ADD_DROP, (int)drop.getName(), drop.getValue(), dropCord[1], dropCord[0]);
		}
	}

	public static void handleMove(final Consumer<Decorator> func) {
		func.accept(clients.get(available_client).currentDecorator());
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
