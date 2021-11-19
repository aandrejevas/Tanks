
import processing.core.PShape;
import utils.ArenaBlock;
import utils.Utils;

public class TextureBlock extends ArenaBlock {
	public final PShape shape;

	public TextureBlock(final int x, final int y) {
		super(x, y);
		shape = Facade.createShape(Main.images.getImage(Utils.MAP_EMPTY), this.x, this.y);
	}
}
