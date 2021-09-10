
import java.util.Arrays;
import processing.core.PApplet;
import processing.net.Client;

// Client
public class Main extends PApplet {

	public static final int W = 600, H = 500;

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
		c = new Client(this, "127.0.0.1", 12345);
	}

	@Override
	public void draw() {
		if (mousePressed == true) {
			// Draw our line
			stroke(255);
			line(pmouseX, pmouseY, mouseX, mouseY);
			// Send mouse coords to other person
			c.write(pmouseX + " " + pmouseY + " " + mouseX + " " + mouseY + "\n");
		}

		// Receive data from server
		while (c.available() > 0) {
			input = c.readString();
			input = input.substring(0, input.indexOf("\n"));  // Only up to the newline
			data = Arrays.stream(split(input, ' ')).mapToInt(a -> Integer.parseInt(a)).toArray(); // Split values into an array
			// Draw line using received coords
			stroke(0);
			line(data[0], data[1], data[2], data[3]);
		}
	}
}
