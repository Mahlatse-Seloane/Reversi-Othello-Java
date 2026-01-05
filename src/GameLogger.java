import java.util.ArrayList;

public class GameLogger
{
    private static final int viewSize = 62;
    private static final String separator = "-".repeat(viewSize);

    private GameLogger() {}

    public static void logGameTitle(String gameTitle)
    {
        final int centerAlignment = (viewSize/2) - (gameTitle.length()/2);
        final String padding = " ".repeat(centerAlignment);
        final String line = "=".repeat(viewSize);

        System.out.println(padding + gameTitle + padding);
        System.out.println(line);
        System.out.println();
    }

    public static void logGameDescription(String description)
    {
        System.out.println(description);
        System.out.println();
    }

    public static void logHeader(final String header)
    {
        final int headerWidth = header.length();
        final String underline = "-".repeat(headerWidth);

        final int centerAlignment = (viewSize/2) - (headerWidth/2);
        final String padding = " ".repeat(centerAlignment);

        System.out.println(separator);
        System.out.println(padding + header + padding);
        System.out.println(padding + underline + padding);
        System.out.println();
    }

    public static void logBoardSize(final int boardSize)
    {
        System.out.println("Board size: " + boardSize + " x " + boardSize);
        System.out.println();
    }

    public static void logMoves(final String playerID, final Move chosenMove, final ArrayList<Move> flippedTokens)
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
        StringBuilder playerTurn = new StringBuilder(playerID + " : r" + chosenMove.row() + "c" + chosenMove.col() + " →");

        for(Move flip: flippedTokens)
            playerTurn.append(" r").append(flip.row()).append("c").append(flip.col());

        System.out.println(playerTurn);
        System.out.println();
    }

    public static void logGameResults(final EndResults results)
    {
        if (results == null)
            throw new IllegalArgumentException("Results cannot be null");

        final int width = Math.max(results.p1ID().length(), results.p2ID().length());

        System.out.println("Results: ");
        System.out.println();
        System.out.printf("%-" + width + "s = %2d",results.p1ID(),  results.p1TokenCount());
        System.out.println();
        System.out.printf("%-" + width + "s = %2d",results.p2ID(), results.p2TokenCount());
        System.out.println();
        System.out.println("winner = " + results.winner());
        System.out.println("Reason: " + results.reason());
        System.out.println();
    }


}