package visitor;

import client.Bullet;
import client.Main;
import client.Tank;

public class Drawer {

	public void draw() {
		for (int i = 0; i != Main.edge; ++i) {
			for (int j = 0; j != Main.edge; ++j) {
				Main.map.background[i][j].draw();
				Main.map.map[i][j].draw();
				if (Main.map.map[i][j].drop != null) {
					Main.map.map[i][j].drop.draw();
				}
			}
		}

		Main.bullets.values().forEach((final Bullet bullet) -> bullet.draw());
		Main.tanks.values().forEach((final Tank tank) -> tank.draw());

		if (Main.show_help) {
			if (Main.show_second_language) {
				Main.chain.showHelp((1 << Main.language) | (1 << Main.language2));
			} else {
				Main.chain.showHelp(1 << Main.language);
			}
		}

		if (Main.show_chat) {
			Main.self.translate(0, Main.Df - Main.scale);
			Main.self.text(Main.message.get().array(), 0, Main.message.get().position(), 50, 0);
			Main.messages.forEach((final String message) -> {
				Main.self.translate(0, -Main.scale);
				Main.self.text(message, 50, 0);
			});
		}
	}

	public void accept(final SaverVisitor visitor) {
		visitor.visit(this);
	}
}
