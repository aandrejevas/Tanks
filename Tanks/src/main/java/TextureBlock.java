
import processing.core.PShape;
import utils.ArenaBlock;

public class TextureBlock extends ArenaBlock {
	public final PShape shape;

	public TextureBlock(final int x, final int y) {
		super(x, y);
		shape = Facade.createShape(Main.background_box, this.x, this.y);
	}
}
