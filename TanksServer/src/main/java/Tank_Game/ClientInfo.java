package Tank_Game;

public class ClientInfo {
	private static int count = 0;

	public final int index;
	public int points;

	public ClientInfo() {
		index = count++;
		points = 0;
	}
}
