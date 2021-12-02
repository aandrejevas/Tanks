package Tank_Game.Patterns.Singletone;

import Tank_Game.Main;

public class Game_Context {
	private int seed, edge, tic_time, player_count = 0, enemies_count = 0, ai_set = -1, kill_server_set = 0;
	private static Game_Context instance;

	private Game_Context() {
	}

	public static synchronized Game_Context getInstance() {
		if (instance == null) {
			instance = new Game_Context();
		}
		return instance;
	}

	public synchronized void setProp(String key, int val) {
		if (key.equals("ai")) {
			this.ai_set = val;
		} else if (key.equals("kill")) {
			this.kill_server_set = val;
		}
	}

	public int Player_Count() {
		return ++player_count;
	}

	public int getPlayer_count() {
		return player_count;
	}

	public int Set_Enemy() {
		return ++enemies_count;
	}

	public int Remove_Enemy() {
		return --enemies_count;
	}

	public int getEnemies_count() {
		return enemies_count;
	}

	public int getAi_set() {
		return ai_set;
	}

	public int getKill_server_set() {
		return kill_server_set;
	}

	public void receiveMessage(final byte[] data, final int offset, final int length) {
		Main.this_server.write(data, offset, length);
	}

	public void receiveMessage(final byte[] data) {
		Main.this_server.write(data, 0, data.length);
	}
}
