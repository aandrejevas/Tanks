package Tank_Game.Patterns.Singletone;

public class Game_Context
{
    private int seed;

    private int edge;

    private int tic_time;

    private int player_count = 0;

    private int enemies_count = 0;

    private int ai_set = -1;

    private int kill_server_set = 0;

    private static Game_Context instance;

    private Game_Context( ) {}

    public static synchronized Game_Context getInstance( )
    {
        if (instance == null)
        {
            instance = new Game_Context();
        }
        return instance;
    }

    public synchronized void setProp(String key, int val)
    {
        if (key.equals("ai")){
            this.ai_set = val;
        } else if (key.equals("kill")){
            this.kill_server_set = val;
        }
    }


    public int Player_Count(){
        player_count++;
        return player_count;
    }

    public int getPlayer_count(){
        return player_count;
    }

    public int Set_Enemy(){
        enemies_count++;
        return enemies_count;
    }

    public int Remove_Enemy(){
        enemies_count++;
        return enemies_count;
    }

    public int getEnemies_count(){
        return enemies_count;
    }

    public int getAi_set() {
        return ai_set;
    }

    public int getKill_server_set() {
        return kill_server_set;
    }
}
