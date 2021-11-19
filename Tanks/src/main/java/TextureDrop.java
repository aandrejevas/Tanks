
import processing.core.PShape;
import utils.Drop;

public class TextureDrop extends Drop {
	public final PShape shape;

	public TextureDrop(final byte name, final int value, final int x, final int y) {
		super(name, value);
		shape = Facade.createShape(Main.images.getImage(name), x, y);
	}
}
