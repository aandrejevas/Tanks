
import java.lang.invoke.MethodHandles;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.IntConsumer;
import java.util.function.UnaryOperator;
import processing.core.PApplet;
import processing.core.PImage;
import processing.net.Client;
import utils.ArenaBlock;
import utils.ArenaMap;
import utils.MapBuilder;
import utils.TOutputStream;
import utils.Utils;

// Client
public class Main extends PApplet {

	public static final int D = 800;
	public static final Map<Integer, Tank> tanks = new HashMap<>();
	public static final Map<Integer, Bullet> bullets = new HashMap<>();
	public static final long move_timeout = 100_000_000, shoot_timeout = 500_000_000;

	public static PApplet self;
	public static Client this_client;
	public static TOutputStream this_os;
	public static PImage red_tank, t34_tank, t34_tank_highlited, tiger_tank, sherman_tank,
		bullet_blue, bullet_red, bullet_normal,
		background_box, water_box, lava_box, metal_box, wood_box,
		drop_sammo, drop_mammo, drop_lammo,
		drop_sarmor, drop_marmor, drop_larmor,
		drop_shealth, drop_mhealth, drop_lhealth;
	public static float scale;
	public static boolean initialized = false;
	public static int move_state = 0, normal_shots = 20, blue_shots = 0, red_shots = 0;
	public static long move_start = System.nanoTime(), shoot_start = System.nanoTime();

	private static byte shot_type = Utils.S_SHOOT_NORMAL;

	public static int edge;
	public static ArenaMap map = null;

	public static boolean myTank = true;

	public static void main(final String[] args) {
		PApplet.main(MethodHandles.lookup().lookupClass(), args);
	}

	@Override
	public void settings() {
		size(D + 100, D, P2D);
	}

	@Override
	public void setup() {
		hint(DISABLE_ASYNC_SAVEFRAME);
		//hint(DISABLE_OPENGL_ERRORS);
		surface.setResizable(false);

		self = this;
		red_tank = loadImage("tank_test.png");
		t34_tank = loadImage("t-34.png");
		tiger_tank = loadImage("tiger-1.png");
		sherman_tank = loadImage("sherman.png");
		bullet_blue = loadImage("Bullet_Blue_3.png");
		bullet_normal = loadImage("Bullet_Green_2.png");
		bullet_red = loadImage("Bullet_Red_2.png");
		background_box = loadImage("Backgound_box.png");
		metal_box = loadImage("metal_box.png");
		wood_box = loadImage("wood_box.png");
		lava_box = loadImage("lava_box.png");
		water_box = loadImage("pudle_box.png");
		drop_lammo = loadImage("Drops/Ammo_Drop/Large_Ammo.png");
		drop_mammo = loadImage("Drops/Ammo_Drop/Medium_Ammo.png");
		drop_sammo = loadImage("Drops/Ammo_Drop/Small_Ammo.png");
		drop_sarmor = loadImage("Drops/Armor_Drop/Small_Armor.png");
		drop_marmor = loadImage("Drops/Armor_Drop/Medium_Armor.png");
		drop_larmor = loadImage("Drops/Armor_Drop/Large_Armor.png");
		drop_lhealth = loadImage("Drops/Health_Drop/Large_Health.png");
		drop_mhealth = loadImage("Drops/Health_Drop/Medium_Health.png");
		drop_shealth = loadImage("Drops/Health_Drop/Small_Health.png");
		t34_tank_highlited = loadImage("tank_t34_highlighted.png");

		this_client = new Client(this, "127.0.0.1", 12345);
		this_client.output = (this_os = new TOutputStream(this_client));
		this_os.write(Utils.S_INIT_CLIENT);
	}

	public static PImage getImage(final byte key) {
		switch (key) {
			case Utils.MAP_EMPTY: return background_box;
			case Utils.MAP_BORDER: return metal_box;
			case Utils.MAP_WALL: return wood_box;
			case Utils.MAP_WATER: return water_box;
			case Utils.MAP_LAVA: return lava_box;
			case Utils.MAP_T34: return t34_tank;
			case Utils.MAP_SHERMAN: return sherman_tank;
			case Utils.MAP_TIGER: return tiger_tank;
			case Utils.DROP_LAMMO: return drop_lammo;
			case Utils.DROP_MAMMO: return drop_mammo;
			case Utils.DROP_SAMMO: return drop_sammo;
			case Utils.DROP_LARMOR: return drop_larmor;
			case Utils.DROP_MARMOR: return drop_marmor;
			case Utils.DROP_SARMOR: return drop_sarmor;
			case Utils.DROP_LHEALTH: return drop_lhealth;
			case Utils.DROP_MHEALTH: return drop_mhealth;
			case Utils.DROP_SHEALTH: return drop_shealth;
			case Utils.SHOT_NORMAL: return bullet_normal;
			case Utils.SHOT_RED: return bullet_red;
			case Utils.SHOT_BLUE: return bullet_blue;
			default: throw new NullPointerException();
		}
	}

	@Override
	public void draw() {
		while (this_client.available() != 0) {
			Utils.rbuf.reset().limit(this_client.readBytes(Utils.rbuf.array()));
			do {
				switch (Utils.rbuf.get()) {
					case Utils.INITIALIZE:
						initialized = true;
						break;
					case Utils.INITIALIZE_GRID:
						edge = Utils.rbuf.getInt();
						scale = (float)D / edge;
						Utils.random.setSeed(Utils.rbuf.getInt());

						textSize(scale * 0.8f);
						drawPanel();
						handleGenMap();
						break;
					// <><><><><><><><><><><><><><><> ADD/REMOVE TANK <><><><><><><><><><><><><><><>
					case Utils.ADD_LEFT_TANK:
						handleAdd(Tank::initLeft);
						break;
					case Utils.ADD_RIGHT_TANK:
						handleAdd(Tank::initRight);
						break;
					case Utils.ADD_UP_TANK:
						handleAdd(Tank::initUp);
						break;
					case Utils.ADD_DOWN_TANK:
						handleAdd(Tank::initDown);
						break;
					case Utils.REMOVE_TANK:
						tanks.remove(Utils.rbuf.getInt());
						break;
					// <><><><><><><><><><><><><><><> ADD/REMOVE DROP <><><><><><><><><><><><><><><>
					case Utils.ADD_DROP: {
						final byte i1 = (byte)Utils.rbuf.getInt();
						final int i2 = Utils.rbuf.getInt(), i3 = Utils.rbuf.getInt(), i4 = Utils.rbuf.getInt();
						map.map[i3][i4].drop = new TextureDrop(i1, i2, getImage(i1), i4, i3);
						break;
					}
					case Utils.REMOVE_DROP:
						final ArenaBlock block = map.map[Utils.rbuf.getInt()][Utils.rbuf.getInt()];
						switch (block.drop.getName()) {
							case Utils.DROP_SAMMO:
								normal_shots += block.drop.getValue();
								break;
							case Utils.DROP_MAMMO:
								blue_shots += block.drop.getValue();
								break;
							case Utils.DROP_LAMMO:
								red_shots += block.drop.getValue();
								break;
						}
						drawPanel();
						block.drop = null;
						break;
					// <><><><><><><><><><><><><><><> MOVE <><><><><><><><><><><><><><><>
					case Utils.MOVE_LEFT:
						handleMove(Tank::moveLeft);
						break;
					case Utils.MOVE_RIGHT:
						handleMove(Tank::moveRight);
						break;
					case Utils.MOVE_UP:
						handleMove(Tank::moveUp);
						break;
					case Utils.MOVE_DOWN:
						handleMove(Tank::moveDown);
						break;
					// <><><><><><><><><><><><><><><> POINT <><><><><><><><><><><><><><><>
					case Utils.POINT_LEFT:
						handleMove(Tank::pointLeft);
						break;
					case Utils.POINT_RIGHT:
						handleMove(Tank::pointRight);
						break;
					case Utils.POINT_UP:
						handleMove(Tank::pointUp);
						break;
					case Utils.POINT_DOWN:
						handleMove(Tank::pointDown);
						break;
					// <><><><><><><><><><><><><><><> TURN <><><><><><><><><><><><><><><>
					case Utils.TURN_LEFT:
						handleMove(Tank::turnLeft);
						break;
					case Utils.TURN_RIGHT:
						handleMove(Tank::turnRight);
						break;
					case Utils.TURN_UP:
						handleMove(Tank::turnUp);
						break;
					case Utils.TURN_DOWN:
						handleMove(Tank::turnDown);
						break;
					// <><><><><><><><><><><><><><><> BULLET MOVE <><><><><><><><><><><><><><><>
					case Utils.BULLET_LEFT:
						handleBulletMove(Bullet::moveLeft);
						break;
					case Utils.BULLET_RIGHT:
						handleBulletMove(Bullet::moveRight);
						break;
					case Utils.BULLET_UP:
						handleBulletMove(Bullet::moveUp);
						break;
					case Utils.BULLET_DOWN:
						handleBulletMove(Bullet::moveDown);
						break;
					// <><><><><><><><><><><><><><><> ADD/REMOVE BULLET <><><><><><><><><><><><><><><>
					case Utils.ADD_BULLET:
						bullets.put(Utils.rbuf.getInt(), new Bullet(Utils.rbuf.getInt(), Utils.rbuf.getInt(), (byte)Utils.rbuf.getInt()));
						break;
					case Utils.REMOVE_BULLET:
						bullets.remove(Utils.rbuf.getInt());
						break;
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

			for (int i = 0; i < edge; i++) {
				for (int j = 0; j < edge; j++) {
					((TextureBlock)(map.background[j][i])).shape.draw(g);
					((TextureBlock)(map.map[j][i])).shape.draw(g);
					if (map.map[j][i].drop != null) {
						((TextureDrop)(map.map[j][i].drop)).shape.draw(g);
					}
				}
			}

			bullets.values().forEach((final Bullet bullet) -> bullet.shape.draw(g));
			tanks.values().forEach((final Tank tank) -> tank.shape.draw(g));
		}
	}

	public static void handleGenMap() {
		map = new ArenaMap(edge, false);

		for (int i = 0; i < edge; i++) {
			for (int j = 0; j < edge; j++) {
				map.setBlock(new TextureBlock(i, j));
				map.setDefBlock(new TextureBlock(i, j));
			}
		}

//		map = (new MapBuilder(map)).makeBackground().makeLava().makeWater().makeBorders().makeMaze().getBuildable();
		map = (new MapBuilder(map)).build(true).getBuildable();

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
		for (int i = 0; i < edge; i++) {
			for (int j = 0; j < edge; j++) {
				((TextureBlock)(map.map[j][i])).setShape(getImage(map.map[j][i].value));
				((TextureBlock)(map.background[j][i])).setShape(getImage(map.background[j][i].value));
			}
		}
	}

	public static void sendMove(final byte message) {
		this_os.write(message);
		move_start = System.nanoTime();
	}

	public static void handleAdd(final UnaryOperator<Tank> func) {
		tanks.put(Utils.rbuf.getInt(), func.apply(new Tank(Utils.rbuf.getInt(), Utils.rbuf.getInt(), Utils.rbuf.getInt())));
	}

	public static void handleMove(final Consumer<Tank> func) {
		func.accept(tanks.get(Utils.rbuf.getInt()));
	}

	public static void handleBulletMove(final Consumer<Bullet> func) {
		func.accept(bullets.get(Utils.rbuf.getInt()));
	}

	@Override
	public void keyPressed() {
		updateMoveState((final int bit) -> move_state |= bit);
		switch (keyCode) {
			case ' ':
				switch (shot_type) {
					case Utils.S_SHOOT_NORMAL:
						if (normal_shots != 0) {
							shoot(() -> --normal_shots);
						}
						break;
					case Utils.S_SHOOT_BLUE:
						if (blue_shots != 0) {
							shoot(() -> --blue_shots);
						}
						break;
					case Utils.S_SHOOT_RED:
						if (red_shots != 0) {
							shoot(() -> --red_shots);
						}
						break;
				}
				return;
			case '1':
				shot_type = Utils.S_SHOOT_NORMAL;
				return;
			case '2':
				shot_type = Utils.S_SHOOT_BLUE;
				return;
			case '3':
				shot_type = Utils.S_SHOOT_RED;
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
		image(bullet_normal, 810, 10, scale, scale);
		image(bullet_blue, 810, 10 + scale, scale, scale);
		image(bullet_red, 810, 10 + 2 * scale, scale, scale);
		fill(0xFFFFFFFF);
		text(normal_shots, 850, scale);
		text(blue_shots, 850, scale * 2);
		text(red_shots, 850, scale * 3);
	}
}
