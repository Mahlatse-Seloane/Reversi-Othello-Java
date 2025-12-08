public class HumanPlayer extends PlayerType
{
    public HumanPlayer()
    {
        setPlayerID("Human Player");
    }

    @Override
    public int chooseMove(final SquareState[][] board,final Move[] moves)
    {
        int totalMoves = moves.length;
        StringBuilder prompt = new StringBuilder("Your options:");
        for(int i = 0; i < totalMoves; i++)
            prompt.append(String.format("\n%s. row: %d col: %d",(i+1),moves[i].row(),moves[i].col()));

        prompt.append(String.format("\n\nSelect a move (%d - %d): " ,1,totalMoves));
        return InputValidator.ReadInt(prompt.toString(),1,totalMoves) - 1;
    }
}
