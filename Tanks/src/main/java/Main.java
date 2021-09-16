
import java.nio.ByteBuffer;
import processing.core.PApplet;
import processing.net.Client;

// Client
public class Main extends PApplet {

	public static final int W = 600, H = 500;
	public static final ByteBuffer buffer = ByteBuffer.allocate(16);

	public static Client c;

	public static void main(final String[] args) {
		PApplet.main(Main.class, args);
	}

	@Override
	public void settings() {
		size(W, H);
	}

	@Override
	public void setup() {
		c = new Client(this, "127.0.0.1", 12345);
		stroke(0);
	}

	@Override
	public void draw() {
		if (mousePressed == true) {
			c.write(buffer.putInt(0, pmouseX).putInt(4, pmouseY).putInt(8, mouseX).putInt(12, mouseY).array());
		}

		while (c.available() != 0) {
			c.readBytes(buffer.array());
			line(buffer.getInt(0), buffer.getInt(4), buffer.getInt(8), buffer.getInt(12));
		}
	}
}
