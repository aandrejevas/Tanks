
import processing.core.PConstants;
import processing.core.PImage;
import processing.core.PShape;
import utils.ArenaBlock;

public class TextureBlock extends ArenaBlock {

	public final PShape shape;

	public TextureBlock(final int x, final int y) {
		super(x, y);

		shape = Main.self.createShape(PShape.GEOMETRY);
		shape.setStroke(false);
		shape.setFill(false);
		shape.setTint(false);
		shape.setTextureMode(PConstants.NORMAL);
		shape.setTexture(Main.background_box);
		shape.translate(this.x * Main.scale_x, this.y * Main.scale_y);

		shape.beginShape(PConstants.POLYGON);
		shape.vertex(0, 0, /*                 */ 0, 0);
		shape.vertex(Main.scale_x, 0, /*      */ 1, 0);
		shape.vertex(Main.scale_x, Main.scale_y, 1, 1);
		shape.vertex(0, Main.scale_y, /*      */ 0, 1);
		shape.endShape(PConstants.CLOSE);
	}

	public void setShape(final PImage image) {
		shape.setTexture(image);
	}
}
