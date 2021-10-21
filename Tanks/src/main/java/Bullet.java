
import processing.core.PConstants;
import processing.core.PShape;
import utils.Utils;

public class Bullet {
	public final PShape shape;

	public Bullet(final int x, final int y, int type) {
		shape = Main.self.createShape(PShape.GEOMETRY);
		shape.setStroke(false);
		shape.setFill(false);
		shape.setTint(false);
		shape.setTextureMode(PConstants.NORMAL);
		System.out.println(type);
//		switch (type) {
//			case Utils.SHOT_NORMAL: shape.setTexture(Main.bullet_normal);break;
//			case Utils.DROP_MAMMO: shape.setTexture(Main.bullet_blue);break;
//			case Utils.DROP_LAMMO: shape.setTexture(Main.bullet_red);break;
//			default: throw new NullPointerException();
//		}
		shape.setTexture(Main.getImage((byte)type));
		shape.translate(x * Main.scale, y * Main.scale);

		shape.beginShape(PConstants.POLYGON);
		shape.vertex(0, 0, /*             */ 0, 0);
		shape.vertex(Main.scale, 0, /*    */ 1, 0);
		shape.vertex(Main.scale, Main.scale, 1, 1);
		shape.vertex(0, Main.scale, /*    */ 0, 1);
		shape.endShape(PConstants.CLOSE);
	}

	public void moveLeft() {
		shape.translate(-Main.scale, 0);
	}

	public void moveRight() {
		shape.translate(Main.scale, 0);
	}

	public void moveUp() {
		shape.translate(0, -Main.scale);
	}

	public void moveDown() {
		shape.translate(0, Main.scale);
	}
}
