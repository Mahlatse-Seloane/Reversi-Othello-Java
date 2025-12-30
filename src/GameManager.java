import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class GameManager
{
    private Board board;
    private int curBoardSize;
    private int availableSpaces;
    private final PlayerType[] players;
    private int currentIndex = 0;
    private PlayerType curPlayer;

    GameManager()
    {
        players = new PlayerType[2];
    }

    public void simulateSingleGame(final int boardSize,final PlayerType p1,final PlayerType p2)
    {
        if(!doesBoardSizeMeetRequirements(boardSize))
            return;

        if(p1 == null || p2 == null)
            throw new IllegalArgumentException("Players cannot be null");

        players[0] = p1;
        players[1] = p2;

        assignPlayersIDsAndTokens();
        selectStartingPlayer();
        initializeBoard(boardSize);

        System.out.println("STARTING CONFIGURATION");
        System.out.println("Board size: " + boardSize + " x " + boardSize);
        System.out.println();
        GameLogger.printBoard(board.peekBoard(),curBoardSize);

        int consecutivePasses = 0;
        Move chosenMove = null;
        Move[] flippedTokens = new Move[0];

        while(availableSpaces > 0 && consecutivePasses < 2)
        {
            Move[] validMoves = MoveRules.findValidMoves(board.peekBoard(), curPlayer.getPlayerToken());

            System.out.println("===================================\n");
            GameLogger.logMoves(chosenMove, new ArrayList<>(Arrays.asList(flippedTokens)), players[1 - currentIndex].getPlayerID());
            GameLogger.printBoard(board.peekBoard(), boardSize, new ArrayList<>(Arrays.asList(validMoves)), chosenMove, new ArrayList<>(Arrays.asList(flippedTokens)));
            chosenMove = null;
            flippedTokens = new Move[0];

            if(validMoves.length > 0)
            {
                chosenMove = chooseMove(validMoves);
                applyMove(chosenMove);
                flippedTokens = flipCapturedTokens(chosenMove);

                consecutivePasses = 0;
                availableSpaces--;
            }
            else
            {
                consecutivePasses++;
            }

            alternatingTurns();
        }

        System.out.println("===================================");
        System.out.println("RESULTS\n");
        EndResults results = ResultsEvaluator.determineGameResult(board.peekBoard(),p1.getPlayerID(),p1.getPlayerToken(),p2.getPlayerID(),p2.getPlayerToken(),availableSpaces > 0);
        GameLogger.logGameResults(results);
    }

    private boolean doesBoardSizeMeetRequirements(final int boardSize)
    {
        return (boardSize >= 4 && boardSize <= 16 && boardSize % 2 == 0);
    }

    private void assignPlayersIDsAndTokens()
    {
        int index = 1;
        for(PlayerType player : players)
        {
            if (player.getPlayerID().trim().isEmpty())
            {
                player.setPlayerID("alg " + (index));
                index++;
            }
        }

        if(players[0].getPlayerID().equals(players[1].getPlayerID()))
        {
            players[0].setPlayerID(players[0].getPlayerID() + " " + 1);
            players[1].setPlayerID(players[1].getPlayerID() + " " + 2);
        }

        players[0].setPlayerToken(SquareState.WHITE);
        players[1].setPlayerToken(SquareState.BLACK);
    }

    private void selectStartingPlayer()
    {
        Random random = new Random();
        currentIndex = random.nextInt(0, 2);
        curPlayer = players[currentIndex];
    }

    private void initializeBoard(final int boardSize)
    {
        curBoardSize = boardSize;
        board = new Board(curBoardSize);

        int initialRow = (curBoardSize/2) - 1;
        int initialCol = (curBoardSize/2) - 1;

        board.setCellContent(initialRow, initialCol,SquareState.BLACK);
        board.setCellContent(initialRow, initialCol+1,SquareState.WHITE);
        board.setCellContent(initialRow+1, initialCol,SquareState.WHITE);
        board.setCellContent(initialRow+1, initialCol+1,SquareState.BLACK);

        availableSpaces = (curBoardSize * curBoardSize) - 4;
    }

    private Move chooseMove(final Move[] validMoves)
    {
        if(validMoves == null || validMoves.length == 0)
            throw new IllegalArgumentException("Valid moves list cannot be null or empty");

        final int maxValidMoves = validMoves.length;

        final int chosenMoveIndex = curPlayer.chooseMove(board.peekBoard(),validMoves);
        if(chosenMoveIndex < 0 || chosenMoveIndex > maxValidMoves)
            throw new IndexOutOfBoundsException("Chosen move index: " + chosenMoveIndex + " is out of bounds.");

        return validMoves[chosenMoveIndex];
    }

    private void applyMove(final Move chosenMove)
    {
        if(chosenMove == null)
            throw new IllegalArgumentException("Chosen move cannot be null");

        board.setCellContent(chosenMove.row(),chosenMove.col(), curPlayer.getPlayerToken());
    }

    private Move[] flipCapturedTokens(final Move chosenMove)
    {
        if(chosenMove == null)
            return new Move[0];

        SquareState curPToken = curPlayer.getPlayerToken();
        Move[] capturedTokens = MoveRules.findCapturedTokens(board.peekBoard(), chosenMove, curPToken);

        for (Move capturedToken: capturedTokens)
            board.setCellContent(capturedToken.row(), capturedToken.col(), curPToken);

        return capturedTokens;
    }

   private void alternatingTurns()
   {
       if(currentIndex < 0 || currentIndex > 1)
           currentIndex = 0;

       currentIndex = 1 - currentIndex;
       curPlayer = players[currentIndex];
   }
}
