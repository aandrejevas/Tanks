
import processing.core.PImage;
import utils.Utils;

public class FlyweightFactory {
	private final PImage t34_tank, t34_tank_highlited, tiger_tank, sherman_tank,
		bullet_blue, bullet_red, bullet_normal,
		background_box, water_box, lava_box, metal_box, wood_box,
		drop_sammo, drop_mammo, drop_lammo,
		drop_sarmor, drop_marmor, drop_larmor,
		drop_shealth, drop_mhealth, drop_lhealth,
		bar_health,
		bullet_blue_big, bullet_red_big, bullet_normal_big,
		bullet_blue_big_selected, bullet_red_big_selected, bullet_normal_big_selected;

	public FlyweightFactory() {
		t34_tank = Main.self.loadImage("t-34.png");
		tiger_tank = Main.self.loadImage("tiger-1.png");
		sherman_tank = Main.self.loadImage("sherman.png");
		bullet_blue = Main.self.loadImage("Bullet_Blue_3.png");
		bullet_normal = Main.self.loadImage("Bullet_Green_2.png");
		bullet_red = Main.self.loadImage("Bullet_Red_2.png");
		background_box = Main.self.loadImage("Backgound_box.png");
		metal_box = Main.self.loadImage("metal_box.png");
		wood_box = Main.self.loadImage("wood_box.png");
		lava_box = Main.self.loadImage("lava_box.png");
		water_box = Main.self.loadImage("pudle_box.png");
		drop_lammo = Main.self.loadImage("Drops/Ammo_Drop/Large_Ammo.png");
		drop_mammo = Main.self.loadImage("Drops/Ammo_Drop/Medium_Ammo.png");
		drop_sammo = Main.self.loadImage("Drops/Ammo_Drop/Small_Ammo.png");
		drop_sarmor = Main.self.loadImage("Drops/Armor_Drop/Small_Armor.png");
		drop_marmor = Main.self.loadImage("Drops/Armor_Drop/Medium_Armor.png");
		drop_larmor = Main.self.loadImage("Drops/Armor_Drop/Large_Armor.png");
		drop_lhealth = Main.self.loadImage("Drops/Health_Drop/Large_Health.png");
		drop_mhealth = Main.self.loadImage("Drops/Health_Drop/Medium_Health.png");
		drop_shealth = Main.self.loadImage("Drops/Health_Drop/Small_Health.png");
		t34_tank_highlited = Main.self.loadImage("tank_t34_highlighted.png");
		bar_health = Main.self.loadImage("Health.png");
		bullet_blue_big = Main.self.loadImage("Bullet_Blue_3_Big.png");
		bullet_red_big = Main.self.loadImage("Bullet_Red_2_Big.png");
		bullet_normal_big = Main.self.loadImage("Bullet_Green_2_Big.png");
		bullet_blue_big_selected = Main.self.loadImage("Bullet_Blue_3_selected.png");
		bullet_red_big_selected = Main.self.loadImage("Bullet_Red_2_selected.png");
		bullet_normal_big_selected = Main.self.loadImage("Bullet_Green_2_selected.png");
	}

	public PImage getImage(final byte key) {
		switch (key) {
			case Utils.MAP_EMPTY: return background_box;
			case Utils.MAP_BORDER: return metal_box;
			case Utils.MAP_WALL: return wood_box;
			case Utils.MAP_WATER: return water_box;
			case Utils.MAP_LAVA: return lava_box;
			case Utils.MAP_T34: return t34_tank;
			case Utils.MAP_SHERMAN: return sherman_tank;
			case Utils.MAP_T34H: return t34_tank_highlited;
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
			case Utils.BIG_SHOT_NORMAL: return bullet_normal_big;
			case Utils.BIG_SHOT_RED: return bullet_red_big;
			case Utils.BIG_SHOT_BLUE: return bullet_blue_big;
			case Utils.HEALTH_ICON: return bar_health;
			default: throw new NullPointerException();
		}
	}

}
