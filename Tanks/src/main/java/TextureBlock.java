import processing.core.PConstants;
import processing.core.PImage;
import processing.core.PShape;
import utils.ArenaBlock;

public class TextureBlock extends ArenaBlock {

    public PShape shape;

    public TextureBlock(int x, int y) {
        super(x, y);
    }

    public void setShape(PImage image) {
        shape = Main.self.createShape(PShape.GEOMETRY);
        shape.setStroke(false);
        shape.setFill(false);
        shape.setTint(false);
        shape.setTextureMode(PConstants.NORMAL);
        shape.setTexture(image);
        shape.translate(this.x * Main.scale_x, this.y * Main.scale_y);

        shape.beginShape(PConstants.POLYGON);
    }
}
