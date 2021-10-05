package Tank_Game.Patterns.Singletone;

public class Game_Context
{
    private int seed;

    private int edge;

    private int tic_time;

    private int player_count = 0;

    private int enemies_count = 0;

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
}
