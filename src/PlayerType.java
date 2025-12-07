import java.util.ArrayList;
import java.util.Random;

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

    public int chooseMove(final SquareState[][] board,final Move[] validMoves)
    {
        BoardValidator.validateBoard(board);
        if(validMoves == null || validMoves.length == 0)
            throw new IllegalArgumentException("Valid moves list cannot be null or empty");

        Random random = new Random();
        return random.nextInt(0, validMoves.length);
    }
}
