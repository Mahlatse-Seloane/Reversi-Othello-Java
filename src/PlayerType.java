import java.util.ArrayList;

public class PlayerType
{
    private SquareState token;
    private String playerID;

    public PlayerType() {}

    public void setPlayerToken(final SquareState token)
    {
        this.token = token;
    }

    public void setPlayerID(final String ID)
    {
        this.playerID = ID;
    }

    public SquareState getPlayerToken()
    {
        return token;
    }

    public String getPlayerID()
    {
        return playerID;
    }
}
