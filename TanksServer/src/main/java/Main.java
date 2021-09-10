
import java.util.Arrays;
import processing.core.PApplet;
import processing.net.Client;
import processing.net.Server;

// Server
public class Main extends PApplet {

	public static final int W = 600, H = 500;

	public Server s;
	public Client c;
	public String input;
	public int data[];

	public static void main(String[] args) {
		PApplet.main(Main.class, args);
	}

	@Override
	public void settings() {
		size(W, H);
	}

	@Override
	public void setup() {
		s = new Server(this, 12345);
	}

	@Override
	public void draw() {
		if (mousePressed == true) {
			// Draw our line
			stroke(255);
			line(pmouseX, pmouseY, mouseX, mouseY);
			// Send mouse coords to other person
			s.write(pmouseX + " " + pmouseY + " " + mouseX + " " + mouseY + "\n");
		}

		// Receive data from client
		c = s.available();
		if (c != null) {
			input = c.readString();
			println(input);
			input = input.substring(0, input.indexOf("\n"));  // Only up to the newline
			data = Arrays.stream(split(input, ' ')).mapToInt(a -> Integer.parseInt(a)).toArray(); // Split values into an array
			// Draw line using received coords
			stroke(0);
			line(data[0], data[1], data[2], data[3]);
		}
	}
}
