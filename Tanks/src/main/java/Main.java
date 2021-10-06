
import java.lang.invoke.MethodHandles;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.IntConsumer;
import java.util.function.UnaryOperator;
import processing.core.PApplet;
import processing.core.PImage;
import processing.net.Client;
import utils.ArenaMap;
import utils.MapBuilder;
import utils.Utils;

// Client
public class Main extends PApplet {

	public static final int W = 600, H = 600;
	public static final Map<Integer, Tank> tanks = new HashMap<>();
	public static Map<Byte, PImage> imgMap = new HashMap<>();
	public static final long timeout = 100_000_000;

	public static PApplet self;
	public static Client this_client;
	public static PImage red_tank, t34_tank, tiger_tank, sherman_tank,
			background_box, water_box, lava_box, metal_box, wood_box,
			drop_sammo, drop_mammo, drop_lammo,
			drop_sarmor, drop_marmor, drop_larmor,
			drop_shealth, drop_mhealth, drop_lhealth;
	public static float scale_x, scale_y;
	public static boolean initialized = false;
	public static int move_state = 0;
	public static long start_time = System.nanoTime();


	public static int seed; //todo hardcoded for now
	public static int edge; //todo hardcoded for now
	public static ArenaMap map = null;




	public static void main(final String[] args) {
		PApplet.main(MethodHandles.lookup().lookupClass(), args);
	}

	@Override
	public void settings() {
		size(W, H, P2D);
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

		imgMap.put(Utils.MAP_EMPTY, background_box);
		imgMap.put(Utils.MAP_BORDER, metal_box);
		imgMap.put(Utils.MAP_WALL, wood_box);
		imgMap.put(Utils.MAP_WATER, water_box);
		imgMap.put(Utils.MAP_LAVA, lava_box);
		imgMap.put(Utils.MAP_T34, t34_tank);
		imgMap.put(Utils.MAP_SHERMAN, sherman_tank);
		imgMap.put(Utils.MAP_TIGER, tiger_tank);
		imgMap.put(Utils.DROP_LAMMO, drop_lammo);
		imgMap.put(Utils.DROP_MAMMO, drop_mammo);
		imgMap.put(Utils.DROP_SAMMO, drop_sammo);
		imgMap.put(Utils.DROP_LARMOR, drop_larmor);
		imgMap.put(Utils.DROP_MARMOR, drop_marmor);
		imgMap.put(Utils.DROP_SARMOR, drop_sarmor);
		imgMap.put(Utils.DROP_LHEALTH, drop_lhealth);
		imgMap.put(Utils.DROP_MHEALTH, drop_mhealth);
		imgMap.put(Utils.DROP_SHEALTH, drop_shealth);

		this_client = new Client(this, "127.0.0.1", 12345);
		Utils.send(this_client::write, Utils.S_INIT_CLIENT);
	}

	@Override
	public void draw() {
		while (this_client.available() != 0) {
			switch (this_client.read()) {
				case Utils.INITIALIZE:
					initialized = true;
					break;
				case Utils.INITIALIZE_GRID:
					Utils.readII(this_client);
					edge = Utils.i1;
					scale_x = scale_y = (float)H / Utils.i1;
					seed = Utils.i2;

					handleGenMap();
					break;
				// <><><><><><><><><><><><><><><> ADD <><><><><><><><><><><><><><><>
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
					tanks.remove(Utils.readInt(this_client));
					break;
				case Utils.ADD_DROP:
					Utils.readIV(this_client);
					handleAddDrop();
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
				default: throw new AssertionError();
			}
		}

		if (initialized) {
			if (System.nanoTime() - start_time > timeout) {
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

			background(0xFFFFFFFF);

			for (int i = 0; i < edge; i++) {
				for (int j = 0; j < edge; j++) {
					((TextureBlock)(map.map[j][i])).shape.draw(g);
				}
			}

			tanks.values().forEach((final Tank tank) -> {
				tank.shape.draw(g);
			});
		}
	}

	public static void handleGenMap() {
		map = new ArenaMap(seed, edge, false);

		for (int i = 0; i < edge; i++) {
			for (int j = 0; j < edge; j++) {
				map.setBlock(new TextureBlock(i, j));
				map.setDefBlock(new TextureBlock(i, j));
			}
		}

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

		for (int i = 0; i < edge; i++) {
			for (int j = 0; j < edge; j++) {
				((TextureBlock)(map.map[j][i])).setShape(imgMap.get(map.map[j][i].value));
				((TextureBlock)(map.defMap[j][i])).setShape(imgMap.get(map.defMap[j][i].value));
			}
		}
	}

	public static void handleAddDrop() {
		((TextureBlock)(map.map[Utils.i3][Utils.i4])).setShape(imgMap.get((byte)Utils.i1));
		//todo do something with value (i2)

	}

	public static void sendMove(final byte message) {
		Utils.send(this_client::write, message);
		start_time = System.nanoTime();
	}

	public static void handleAdd(final UnaryOperator<Tank> func) {
		/*Utils.readIII(this_client);
		tanks.put(Utils.i1, func.apply(new Tank(Utils.i2, Utils.i3)));*/
		Utils.readIV(this_client);
		tanks.put(Utils.i1, func.apply(new Tank(Utils.i2, Utils.i3, Utils.i4)));
	}

	public static void handleMove(final Consumer<Tank> func) {
		func.accept(tanks.get(Utils.readInt(this_client)));
	}

	@Override
	public void keyPressed() {
		updateMoveState((final int bit) -> move_state |= bit);
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

	public static int GetRand() {
		seed = ((seed * 1103515245) + 12345) & 0x7fffffff;
		return seed;
	}
}
