
import processing.core.PApplet;
import processing.net.Client;
import processing.net.Server;

// Server
public class Main extends PApplet {

	public static final byte buffer[] = new byte[16];

	public static Server s;
	public static Client c;

	public static void main(final String[] args) {
		PApplet.main(Main.class, args);
	}

	@Override
	public void settings() {
	}

	@Override
	public void setup() {
		s = new Server(this, 12345);
	}

	@Override
	public void draw() {
		while ((c = s.available()) != null) {
			c.readBytes(buffer);
			s.write(buffer);
		}
	}
}
