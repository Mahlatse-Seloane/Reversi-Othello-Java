import java.util.ArrayList;

public class GameLogger
{
    private GameLogger() {}

    public static void logMoves(final Move chosenMove, final ArrayList<Move> flippedTokens, final String playerID)
    {
        if (flippedTokens == null)
            throw new IllegalArgumentException("Flipped token coordinates cannot be null");

        if (playerID == null || playerID.isEmpty())
            throw new IllegalArgumentException("Player ID cannot be null or empty");

        if (chosenMove == null && flippedTokens.isEmpty())
        {
            System.out.println(playerID + " : No valid moves available. Turn passed.");
            System.out.println();
            return;
        }

        assert chosenMove != null;
        StringBuilder playerTurn = new StringBuilder("r" + chosenMove.row() + "c" + chosenMove.col() + " " + playerID + " ,");
        final int noOfMoves = flippedTokens.size();

        for (int i = 0; i < noOfMoves; i++)
        {
            Move flip = flippedTokens.removeFirst();
            playerTurn.append(" r").append(flip.row()).append("c").append(flip.col());
        }

        System.out.println(playerTurn);
        System.out.println();
    }

    public static void logGameResults(final EndResults results)
    {
        if (results == null)
            throw new IllegalArgumentException("Results cannot be null");

        System.out.println(results.p1ID() + " = " + results.p1TokenCount());
        System.out.println(results.p2ID() + " = " + results.p2TokenCount());
        System.out.println("winner = " + results.winner());
        System.out.println("Reason: " + results.reason());
        System.out.println();
    }
}