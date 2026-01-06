import java.util.List;

public final class BoardRenderContext
{
    private List<Move> validMoves = List.of();
    private List<Move> flippedTokens = List.of();
    private Move chosenMove = null;
    private boolean showValidMoves = false;

    public void setValidMoves(List<Move> validMoves)
    {
        this.validMoves = validMoves;
    }

    public void setFlippedTokens(List<Move> flippedTokens)
    {
        this.flippedTokens = flippedTokens;
    }

    public void setChosenMove(Move chosenMove)
    {
        this.chosenMove = chosenMove;
    }

    public void setShowValidMoves(boolean showValidMoves)
    {
        this.showValidMoves = showValidMoves;
    }

    public List<Move> getValidMoves()
    {
        return validMoves;
    }

    public List<Move> getFlippedTokens()
    {
        return flippedTokens;
    }

    public Move getChosenMove()
    {
        return chosenMove;
    }

    public boolean showValidMoves()
    {
        return showValidMoves && validMoves != null && !validMoves.isEmpty();
    }

    public boolean showFlippedTokens()
    {
        return flippedTokens != null && !flippedTokens.isEmpty();
    }

    public boolean showChosenMove()
    {
        return chosenMove != null;
    }
}


