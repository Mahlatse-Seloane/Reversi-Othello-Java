public class ResultsEvaluator
{
    private ResultsEvaluator() {}

    public static EndResults determineGameResult(final SquareState[][] board,final String p1ID,final SquareState p1Tok,final String p2ID,final SquareState p2Tok)
    {
        exceptionChecks(board,p1ID,p1Tok,p2ID,p2Tok);

        TokenCount tokenCount = getTokensCount(board,p1Tok,p2Tok);
        String gameEndMessage = determineHowGameEnded(board);

        return new EndResults(p1ID, p2ID, tokenCount.p1(), tokenCount.p2(), determineOutcome(tokenCount,p1ID,p2ID),gameEndMessage);
    }

    private static void exceptionChecks(final SquareState[][] board,final String p1ID,final SquareState p1Tok,final String p2ID,final SquareState p2Tok)
    {
        BoardValidator.validateBoard(board);

        if (p1ID == null || p1ID.isEmpty())
            throw new IllegalArgumentException("Player 1 ID cannot be null or empty");

        if (p2ID == null || p2ID.isEmpty())
            throw new IllegalArgumentException("Player 2 ID cannot be null or empty");

        if (p1Tok == null || p2Tok == null)
            throw new IllegalArgumentException("Player tokens cannot be null");
    }

    private static TokenCount getTokensCount(final SquareState[][] board,final SquareState p1Tok,final SquareState p2Tok)
    {
        final int boardSize = board.length;
        int p1TokenCount = 0, p2TokenCount = 0;

        for (int row = 0; row < boardSize; row++)
        {
            for (int col = 0; col < boardSize; col++)
            {
                final SquareState cell = board[row][col];
                if (cell == p1Tok)
                    p1TokenCount++;
                if (cell == p2Tok)
                    p2TokenCount++;
            }
        }

        return new TokenCount(p1TokenCount,p2TokenCount);
    }

    private static String determineHowGameEnded(final SquareState[][] board)
    {
        final int boardSize = board.length;

        for (int row = 0; row < boardSize; row++)
        {
            for (int col = 0; col < boardSize; col++)
            {
                final SquareState cell = board[row][col];
                if (cell == SquareState.EMPTY)
                    return "Players passed turns consecutively";

            }
        }

        return "All spaces occupied";
    }

    private static String determineOutcome(final TokenCount tokenCount,final String p1ID,final String p2ID)
    {
        String winner = "DRAW";
        if (tokenCount.p1() > tokenCount.p2())
            winner = p1ID;
        else if (tokenCount.p1() < tokenCount.p2())
            winner = p2ID;

        return winner;
    }

    record TokenCount(int p1, int p2){}
}
