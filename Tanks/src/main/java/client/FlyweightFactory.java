package client;

import processing.core.PImage;
import utils.Utils;

public class FlyweightFactory {
	/*private final PImage t34_tank, t34_tank_highlited, tiger_tank, sherman_tank,
		bullet_blue, bullet_red, bullet_normal,
		background_box, water_box, lava_box, metal_box, wood_box,
		drop_sammo, drop_mammo, drop_lammo,
		drop_sarmor, drop_marmor, drop_larmor,
		drop_shealth, drop_mhealth, drop_lhealth,
		bar_health, bar_shield,
		bullet_blue_big, bullet_red_big, bullet_normal_big,
		bullet_blue_big_selected, bullet_red_big_selected, bullet_normal_big_selected;*/

	public FlyweightFactory() {
		/*t34_tank = new ProxyImage("t-34.png");
		tiger_tank = new ProxyImage("tiger-1.png");
		sherman_tank = new ProxyImage("sherman.png");
		bullet_blue = new ProxyImage("Bullet_Blue_3.png");
		bullet_normal = new ProxyImage("Bullet_Green_2.png");
		bullet_red = new ProxyImage("Bullet_Red_2.png");
		background_box = new ProxyImage("Backgound_box.png");
		metal_box = new ProxyImage("metal_box.png");
		wood_box = new ProxyImage("wood_box.png");
		lava_box = new ProxyImage("lava_box.png");
		water_box = new ProxyImage("pudle_box.png");
		drop_lammo = new ProxyImage("Drops/Ammo_Drop/Large_Ammo.png");
		drop_mammo = new ProxyImage("Drops/Ammo_Drop/Medium_Ammo.png");
		drop_sammo = new ProxyImage("Drops/Ammo_Drop/Small_Ammo.png");
		drop_sarmor = new ProxyImage("Drops/Armor_Drop/Small_Armor.png");
		drop_marmor = new ProxyImage("Drops/Armor_Drop/Medium_Armor.png");
		drop_larmor = new ProxyImage("Drops/Armor_Drop/Large_Armor.png");
		drop_lhealth = new ProxyImage("Drops/Health_Drop/Large_Health.png");
		drop_mhealth = new ProxyImage("Drops/Health_Drop/Medium_Health.png");
		drop_shealth = new ProxyImage("Drops/Health_Drop/Small_Health.png");
		t34_tank_highlited = new ProxyImage("tank_t34_highlighted.png");
		bar_health = new ProxyImage("Health.png");
		bar_shield = new ProxyImage("Shield.png");
		bullet_blue_big = new ProxyImage("Bullet_Blue_3_Big.png");
		bullet_red_big = new ProxyImage("Bullet_Red_2_Big.png");
		bullet_normal_big = new ProxyImage("Bullet_Green_2_Big.png");
		bullet_blue_big_selected = new ProxyImage("Bullet_Blue_3_selected.png");
		bullet_red_big_selected = new ProxyImage("Bullet_Red_2_selected.png");
		bullet_normal_big_selected = new ProxyImage("Bullet_Green_2_selected.png");*/
	}

	/*public PImage getImage(final byte key) {
		switch (key) {
			case Utils.MAP_EMPTY: return background_box.get();
			case Utils.MAP_BORDER: return metal_box.get();
			case Utils.MAP_WALL: return wood_box.get();
			case Utils.MAP_WATER: return water_box.get();
			case Utils.MAP_LAVA: return lava_box.get();
			case Utils.MAP_T34: return t34_tank.get();
			case Utils.MAP_SHERMAN: return sherman_tank.get();
			case Utils.MAP_T34H: return t34_tank_highlited.get();
			case Utils.MAP_TIGER: return tiger_tank.get();
			case Utils.DROP_LAMMO: return drop_lammo.get();
			case Utils.DROP_MAMMO: return drop_mammo.get();
			case Utils.DROP_SAMMO: return drop_sammo.get();
			case Utils.DROP_LARMOR: return drop_larmor.get();
			case Utils.DROP_MARMOR: return drop_marmor.get();
			case Utils.DROP_SARMOR: return drop_sarmor.get();
			case Utils.DROP_LHEALTH: return drop_lhealth.get();
			case Utils.DROP_MHEALTH: return drop_mhealth.get();
			case Utils.DROP_SHEALTH: return drop_shealth.get();
			case Utils.SHOT_NORMAL: return bullet_normal.get();
			case Utils.SHOT_RED: return bullet_red.get();
			case Utils.SHOT_BLUE: return bullet_blue.get();
			case Utils.BIG_SHOT_NORMAL: return bullet_normal_big.get();
			case Utils.BIG_SHOT_RED: return bullet_red_big.get();
			case Utils.BIG_SHOT_BLUE: return bullet_blue_big.get();
			case Utils.HEALTH_ICON: return bar_health.get();
			case Utils.SHIELD_ICON: return bar_shield.get();
			case Utils.SELECTED_SHOT_NORMAL: return bullet_normal_big_selected.get();
			case Utils.SELECTED_SHOT_RED: return bullet_red_big_selected.get();
			case Utils.SELECTED_SHOT_BLUE: return bullet_blue_big_selected.get();
			default: throw new NullPointerException();
		}
	}*/
	public PImage getImage(final byte key) {
		switch (key) {
			case Utils.MAP_EMPTY: return Main.self.loadImage("t-34.png");
			case Utils.MAP_BORDER: return Main.self.loadImage("tiger-1.png");
			case Utils.MAP_WALL: return Main.self.loadImage("sherman.png");
			case Utils.MAP_WATER: return Main.self.loadImage("Bullet_Blue_3.png");
			case Utils.MAP_LAVA: return Main.self.loadImage("Bullet_Green_2.png");
			case Utils.MAP_T34: return Main.self.loadImage("Bullet_Red_2.png");
			case Utils.MAP_SHERMAN: return Main.self.loadImage("Backgound_box.png");
			case Utils.MAP_T34H: return Main.self.loadImage("metal_box.png");
			case Utils.MAP_TIGER: return Main.self.loadImage("wood_box.png");
			case Utils.DROP_LAMMO: return Main.self.loadImage("lava_box.png");
			case Utils.DROP_MAMMO: return Main.self.loadImage("pudle_box.png");
			case Utils.DROP_SAMMO: return Main.self.loadImage("Drops/Ammo_Drop/Large_Ammo.png");
			case Utils.DROP_LARMOR: return Main.self.loadImage("Drops/Ammo_Drop/Medium_Ammo.png");
			case Utils.DROP_MARMOR: return Main.self.loadImage("Drops/Ammo_Drop/Small_Ammo.png");
			case Utils.DROP_SARMOR: return Main.self.loadImage("Drops/Armor_Drop/Small_Armor.png");
			case Utils.DROP_LHEALTH: return Main.self.loadImage("Drops/Armor_Drop/Medium_Armor.png");
			case Utils.DROP_MHEALTH: return Main.self.loadImage("Drops/Armor_Drop/Large_Armor.png");
			case Utils.DROP_SHEALTH: return Main.self.loadImage("Drops/Health_Drop/Large_Health.png");
			case Utils.SHOT_NORMAL: return Main.self.loadImage("Drops/Health_Drop/Medium_Health.png");
			case Utils.SHOT_RED: return Main.self.loadImage("Drops/Health_Drop/Small_Health.png");
			case Utils.SHOT_BLUE: return Main.self.loadImage("tank_t34_highlighted.png");
			case Utils.BIG_SHOT_NORMAL: return Main.self.loadImage("Health.png");
			case Utils.BIG_SHOT_RED: return Main.self.loadImage("Shield.png");
			case Utils.BIG_SHOT_BLUE: return Main.self.loadImage("Bullet_Blue_3_Big.png");
			case Utils.HEALTH_ICON: return Main.self.loadImage("Bullet_Red_2_Big.png");
			case Utils.SHIELD_ICON: return Main.self.loadImage("Bullet_Green_2_Big.png");
			case Utils.SELECTED_SHOT_NORMAL: return Main.self.loadImage("Bullet_Blue_3_selected.png");
			case Utils.SELECTED_SHOT_RED: return Main.self.loadImage("Bullet_Red_2_selected.png");
			case Utils.SELECTED_SHOT_BLUE: return Main.self.loadImage("Bullet_Green_2_selected.png");
			default: throw new NullPointerException();
		}
	}
}
