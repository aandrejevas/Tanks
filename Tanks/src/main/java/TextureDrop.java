
import processing.core.PConstants;
import processing.core.PImage;
import processing.core.PShape;
import utils.Drop;

public class TextureDrop extends Drop {

	public final PShape shape;

	public TextureDrop(final byte name, final int value, final PImage image, final int x, final int y) {
		super(name, value);
		shape = Main.self.createShape(PShape.GEOMETRY);
		shape.setStroke(false);
		shape.setFill(false);
		shape.setTint(false);
		shape.setTextureMode(PConstants.NORMAL);
		shape.setTexture(image);
		shape.translate(x * Main.scale_x, y * Main.scale_y);

		shape.beginShape(PConstants.POLYGON);
		shape.vertex(0, 0, /*                 */ 0, 0);
		shape.vertex(Main.scale_x, 0, /*      */ 1, 0);
		shape.vertex(Main.scale_x, Main.scale_y, 1, 1);
		shape.vertex(0, Main.scale_y, /*      */ 0, 1);
		shape.endShape(PConstants.CLOSE);
	}
}
