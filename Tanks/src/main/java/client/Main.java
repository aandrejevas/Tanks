package client;

import chain.English;
import chain.French;
import chain.Language;
import chain.Lithuanian;
import chain.NullLanguage;
import chain.Russian;
import java.lang.invoke.MethodHandles;
import java.nio.charset.StandardCharsets;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.function.IntConsumer;
import memento.Message;
import processing.core.PApplet;
import processing.core.PImage;
import processing.net.Client;
import utils.ArenaBlock;
import utils.ArenaMap;
import utils.ErrorLogger;
import utils.NullLogger;
import utils.OutputLogger;
import utils.TOutputStream;
import utils.TWritable;
import utils.Utils;

// Client
public class Main extends PApplet {

	public static final int D = 800;
	public static final Map<Integer, Tank> tanks = new HashMap<>();
	public static final Map<Integer, Bullet> bullets = new HashMap<>();
	public static final long move_timeout = 100_000_000, shoot_timeout = 500_000_000;
	public static final String[] languages = new String[] { "LT", "EN", "RU", "FR" };
	public static final Message message = new Message();
	public static final List<Message.Memento> mementoes = new ArrayList<>();
	public static final Queue<String> messages = new ArrayDeque<>();

	public static PApplet self;
	public static Client this_client;
	public static TWritable this_os;
	public static PImage normal_shot_icon, blue_shot_icon, red_shot_icon;
	public static FlyweightFactory images;
	public static float scale;
	public static boolean initialized = false, show_help = false, show_second_language = false,
		write_out = false, write_err = false, show_chat = false;
	public static int move_state = 0, language = 0, language2 = 0, memento_index = 0,
		normal_shots = 20, blue_shots = 0, red_shots = 0, health_state = 100, armor_state = 100,
		edge;
	public static long move_start = System.nanoTime(), shoot_start = System.nanoTime();
	public static Tank this_tank;
	public static byte shot_type = Utils.S_SHOOT_NORMAL, last_drop = Utils.DROP_SAMMO;
	public static ArenaMap map;
	public static Language chain;

	public static void main(final String[] args) {
		PApplet.main(MethodHandles.lookup().lookupClass(), args);
	}

	public int clientTankIndex;

	@Override
	public void settings() {
		size(D + 100, D, P2D);
	}

	@Override
	public void setup() {
		hint(DISABLE_ASYNC_SAVEFRAME);
		//hint(DISABLE_OPENGL_ERRORS);
		surface.setResizable(false);
		//printArray(PFont.list());
		textFont(createFont("Arial", 100, true));

		self = this;
		images = new FlyweightFactory();
		normal_shot_icon = images.getImage(Utils.SELECTED_SHOT_NORMAL);
		blue_shot_icon = images.getImage(Utils.BIG_SHOT_BLUE);
		red_shot_icon = images.getImage(Utils.BIG_SHOT_RED);

		chain = new Lithuanian(new English(new Russian(new French(new NullLanguage()))));
		mementoes.add(message.saveState());

		this_client = new Client(this, "127.0.0.1", 12345);
		this_os = (TWritable)(this_client.output = new TOutputStream(this_client));
		this_os.write(Utils.S_INIT_CLIENT);
	}

	@Override
	public void draw() {
		while (this_client.available() != 0) {
			Utils.rbuf.rewind().limit(this_client.readBytes(Utils.rbuf.array()));
			do {
				switch (Utils.rbuf.get()) {
					case Utils.INITIALIZE_GRID:
						edge = Utils.rbuf.getInt();
						scale = (float)D / edge;
						Utils.random.setSeed(Utils.rbuf.getInt());

						textSize(scale * 0.8f);
						drawPanel();
						handleGenMap();
						break;
					// <><><><><><><><><><><><><><><> ADD/REMOVE TANK <><><><><><><><><><><><><><><>
					case Utils.ADD_NEW_TANK:
						if (initialized) {
							tanks.put(Utils.rbuf.getInt(), new Tank(Utils.rbuf.getInt(), Utils.rbuf.getInt(), Utils.MAP_T34));
						} else {
							initialized = true;
							clientTankIndex = Utils.rbuf.getInt();
							tanks.put(clientTankIndex, this_tank = new Tank(Utils.rbuf.getInt(), Utils.rbuf.getInt(), Utils.MAP_T34H));
						}
						break;
					case Utils.ADD_LEFT_TANK:
						Facade.handleAdd(Tank::pointLeft);
						break;
					case Utils.ADD_RIGHT_TANK:
						Facade.handleAdd(Tank::pointRight);
						break;
					case Utils.ADD_UP_TANK:
						Facade.handleAdd(null);
						break;
					case Utils.ADD_DOWN_TANK:
						Facade.handleAdd(Tank::pointDown);
						break;
					case Utils.REMOVE_TANK:
						tanks.remove(Utils.rbuf.getInt());
						break;
					// <><><><><><><><><><><><><><><> ADD/REMOVE DROP <><><><><><><><><><><><><><><>
					case Utils.ADD_DROP: {
						final int y = Utils.rbuf.getInt(), x = Utils.rbuf.getInt();
						map.map[y][x].drop = new TextureDrop((byte)Utils.rbuf.getInt(), Utils.rbuf.getInt(), x, y);
						break;
					}
					case Utils.REMOVE_DROP:
						final ArenaBlock block = map.map[Utils.rbuf.getInt()][Utils.rbuf.getInt()];
						if (Utils.rbuf.getInt() == clientTankIndex) {
							switch (block.drop.getName()) {
								case Utils.DROP_SAMMO:
									normal_shots += block.drop.getValue();
									last_drop = Utils.DROP_SAMMO;
									break;
								case Utils.DROP_MAMMO:
									blue_shots += block.drop.getValue();
									last_drop = Utils.DROP_MAMMO;
									break;
								case Utils.DROP_LAMMO:
									red_shots += block.drop.getValue();
									last_drop = Utils.DROP_LAMMO;
									break;
							}
							drawPanel();
						}
						block.drop = null;
						break;
					// <><><><><><><><><><><><><><><> MOVE <><><><><><><><><><><><><><><>
					case Utils.MOVE_LEFT:
						Facade.handleMove(Tank::moveLeft);
						break;
					case Utils.MOVE_RIGHT:
						Facade.handleMove(Tank::moveRight);
						break;
					case Utils.MOVE_UP:
						Facade.handleMove(Tank::moveUp);
						break;
					case Utils.MOVE_DOWN:
						Facade.handleMove(Tank::moveDown);
						break;
					// <><><><><><><><><><><><><><><> POINT <><><><><><><><><><><><><><><>
					case Utils.POINT_LEFT:
						Facade.handleMove(Tank::pointLeft);
						break;
					case Utils.POINT_RIGHT:
						Facade.handleMove(Tank::pointRight);
						break;
					case Utils.POINT_UP:
						Facade.handleMove(Tank::pointUp);
						break;
					case Utils.POINT_DOWN:
						Facade.handleMove(Tank::pointDown);
						break;
					// <><><><><><><><><><><><><><><> TURN <><><><><><><><><><><><><><><>
					case Utils.TURN_LEFT:
						Facade.handleMove(Tank::turnLeft);
						break;
					case Utils.TURN_RIGHT:
						Facade.handleMove(Tank::turnRight);
						break;
					case Utils.TURN_UP:
						Facade.handleMove(Tank::turnUp);
						break;
					case Utils.TURN_DOWN:
						Facade.handleMove(Tank::turnDown);
						break;
					// <><><><><><><><><><><><><><><> BULLET MOVE <><><><><><><><><><><><><><><>
					case Utils.BULLET_LEFT:
						Facade.handleBulletMove(Bullet::moveLeft);
						break;
					case Utils.BULLET_RIGHT:
						Facade.handleBulletMove(Bullet::moveRight);
						break;
					case Utils.BULLET_UP:
						Facade.handleBulletMove(Bullet::moveUp);
						break;
					case Utils.BULLET_DOWN:
						Facade.handleBulletMove(Bullet::moveDown);
						break;
					// <><><><><><><><><><><><><><><> ADD/REMOVE BULLET <><><><><><><><><><><><><><><>
					case Utils.ADD_BULLET:
						Facade.handleBulletAdd();
						break;
					case Utils.REMOVE_BULLET:
						bullets.remove(Utils.rbuf.getInt());
						break;
					case Utils.SET_HEALTH:
						//int temp = Utils.rbuf.getInt();
						Utils.rbuf.getInt();
						health_state = Utils.rbuf.getInt();
						drawPanel();
						break;
					case Utils.SET_ARMOR:
						//int tem = Utils.rbuf.getInt();
						Utils.rbuf.getInt();
						armor_state = Utils.rbuf.getInt();
						drawPanel();
						break;
					// <><><><><><><><><><><><><><><> MESSAGE <><><><><><><><><><><><><><><>
					case Utils.MESSAGE: {
						final int length = Utils.rbuf.getInt();
						if (messages.size() == 10)
							messages.poll();
						messages.offer(new String(Utils.rbuf.array(), Utils.rbuf.position(), length, StandardCharsets.US_ASCII));
						Utils.rbuf.position(Utils.rbuf.position() + length);
						break;
					}
					default: throw new AssertionError();
				}
			} while (Utils.rbuf.hasRemaining());
		}

		if (initialized) {
			if (System.nanoTime() - move_start > move_timeout) {
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

			for (int i = 0; i != edge; ++i) {
				for (int j = 0; j != edge; ++j) {
					((TextureBlock)(map.background[i][j])).shape.draw(g);
					((TextureBlock)(map.map[i][j])).shape.draw(g);
					if (map.map[i][j].drop != null) {
						((TextureDrop)(map.map[i][j].drop)).shape.draw(g);
					}
				}
			}

			bullets.values().forEach((final Bullet bullet) -> bullet.shape.draw(g));
			tanks.values().forEach((final Tank tank) -> tank.shape.draw(g));

			if (show_help) {
				if (show_second_language) {
					chain.showHelp((1 << language) | (1 << language2));
				} else {
					chain.showHelp(1 << language);
				}
			}

			if (show_chat) {
				translate(0, height - scale);
				text(message.get().array(), 0, message.get().position(), 50, 0);
				messages.forEach((final String message) -> {
					translate(0, -scale);
					text(message, 50, 0);
				});
			}
		}
	}

	public static void handleGenMap() {
		map = new ArenaMap(edge, false);

		for (int i = 0; i != edge; ++i) {
			for (int j = 0; j != edge; ++j) {
				map.setBlock(new TextureBlock(i, j));
				map.setDefBlock(new TextureBlock(i, j));
			}
		}

//		map = (new MapBuilder(map)).makeBackground().makeLava().makeWater().makeBorders().makeMaze().getBuildable();
		map = (new MapFrontBuilder(map)).build().getBuildable();

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
		for (int i = 0; i != edge; ++i) {
			for (int j = 0; j != edge; ++j) {
				((TextureBlock)(map.map[i][j])).shape.setTexture(images.getImage(map.map[i][j].value));
				((TextureBlock)(map.background[i][j])).shape.setTexture(images.getImage(map.background[i][j].value));
			}
		}
	}

	public static void sendMove(final byte message) {
		this_os.write(message);
		move_start = System.nanoTime();
	}

	@Override
	public void keyPressed() {
		if (show_chat) {
			switch (keyCode) {
				case ENTER:
					this_os.write(Utils.S_MESSAGE, message.get().flip());
					message.get().clear();
					memento_index = 0;
					return;
				case ESC:
					key = 0;
					show_chat = false;
					return;
				case SHIFT:
					if (memento_index != mementoes.size() - 1)
						message.restoreState(mementoes.get(++memento_index));
					return;
				case BACKSPACE:
					if (memento_index != 0)
						message.restoreState(mementoes.get(--memento_index));
					return;
				default:
					if (message.get().hasRemaining()) {
						message.get().put(key);
						while (memento_index != mementoes.size() - 1) {
							mementoes.remove(mementoes.size() - 1);
						}
						mementoes.add(message.saveState());
						++memento_index;
					}
					return;
			}
		}
		switch (keyCode) {
			case ' ':
				switch (shot_type) {
					case Utils.S_SHOOT_NORMAL:
						if (normal_shots != 0) {
							shoot(() -> --normal_shots);
						}
						break;
					case Utils.S_SHOOT_BLUE:
						if (blue_shots != 0 && last_drop == Utils.DROP_MAMMO) {
							shoot(() -> --blue_shots);
						}
						break;
					case Utils.S_SHOOT_RED:
						if (red_shots != 0 && last_drop == Utils.DROP_LAMMO) {
							shoot(() -> --red_shots);
						}
						break;
				}
				return;
			case '1':
				if (shot_type != Utils.S_SHOOT_NORMAL) {
					resetShotIcon();
					shot_type = Utils.S_SHOOT_NORMAL;
					normal_shot_icon = images.getImage(Utils.SELECTED_SHOT_NORMAL);
					drawPanel();
				}
				return;
			case '2':
				if (shot_type != Utils.S_SHOOT_BLUE) {
					resetShotIcon();
					shot_type = Utils.S_SHOOT_BLUE;
					blue_shot_icon = images.getImage(Utils.SELECTED_SHOT_BLUE);
					drawPanel();
				}
				return;
			case '3':
				if (shot_type != Utils.S_SHOOT_RED) {
					resetShotIcon();
					shot_type = Utils.S_SHOOT_RED;
					red_shot_icon = images.getImage(Utils.SELECTED_SHOT_RED);
					drawPanel();
				}
				return;
			case 'O':
			case 'o':
				if (write_out) {
					write_out = false;
					this_tank.setLogger(NullLogger.instance);
				} else {
					write_out = true;
					this_tank.setLogger(OutputLogger.instance);
				}
				return;
			case 'P':
			case 'p':
				if (write_err) {
					write_err = false;
					bullets.values().forEach((final Bullet bullet) -> bullet.setLogger(NullLogger.instance));
				} else {
					write_err = true;
					bullets.values().forEach((final Bullet bullet) -> bullet.setLogger(ErrorLogger.instance));
				}
				return;
			case TAB:
				if (!show_help) {
					if (!show_second_language) language = (language + 1) % languages.length;
					else {
						language2 = (language2 + 1) % languages.length;
						if (language2 == language) language2 = (language2 + 1) % languages.length;
					}
					drawPanel();
				}
				return;
			case 'h':
			case 'H':
				show_help = !show_help;
				return;
			case 'l':
			case 'L':
				if (!show_help) {
					if (show_second_language = !show_second_language)
						if (language2 == language) language2 = (language2 + 1) % languages.length;
					drawPanel();
				}
				return;
			case 'c':
			case 'C':
				show_chat = true;
				return;
		}
		updateMoveState((final int bit) -> move_state |= bit);
	}

	public static void resetShotIcon() {
		switch (shot_type) {
			case Utils.S_SHOOT_NORMAL:
				normal_shot_icon = images.getImage(Utils.BIG_SHOT_NORMAL);
				return;
			case Utils.S_SHOOT_BLUE:
				blue_shot_icon = images.getImage(Utils.BIG_SHOT_BLUE);
				return;
			case Utils.S_SHOOT_RED:
				red_shot_icon = images.getImage(Utils.BIG_SHOT_RED);
				return;
		}
	}

	public void shoot(final Runnable action) {
		if (System.nanoTime() - shoot_start > shoot_timeout) {
			this_os.write(shot_type);
			action.run();
			drawPanel();
			shoot_start = System.nanoTime();
		}
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

	public void drawPanel() {
		fill(0xFF000000);
		noStroke();
		rect(800, 0, 100, 800);
		image(normal_shot_icon, 810, 10, scale, scale);
		image(blue_shot_icon, 810, 10 + scale, scale, scale);
		image(red_shot_icon, 810, 10 + 2 * scale, scale, scale);
		image(images.getImage(Utils.HEALTH_ICON), 810, 10 + 3 * scale, scale, scale);
		image(images.getImage(Utils.SHIELD_ICON), 810, 10 + 4 * scale, scale, scale);
		fill(0xFFFFFFFF);
		text(normal_shots, 850, scale);
		text(blue_shots, 850, scale * 2);
		text(red_shots, 850, scale * 3);
		text(health_state, 850, scale * 4);
		text(armor_state, 850, scale * 5);

		text(languages[language], 850, height - scale * 2);
		if (show_second_language) {
			text(languages[language2], 850, height - scale);
		}
	}
}
