import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class GameManager
{
    private Board board;
    private int availableSpaces;
    private final PlayerType[] players;
    private int currentIndex = 0;
    private PlayerType curPlayer;
    private enum TurnPhase{START, NORMAL, GAMEOVER}
    private TurnPhase turnPhase = TurnPhase.START;

    GameManager()
    {
        players = new PlayerType[2];
    }

    public void simulateSingleGame(final int boardSize,final PlayerType p1,final PlayerType p2)
    {
        if (!doesBoardSizeMeetRequirements(boardSize))
        {
            System.out.println("Invalid board size. Must be even and between 4 and 16.");
            return;
        }

        if(p1 == null || p2 == null)
            throw new IllegalArgumentException("Players cannot be null");

        setupGame(boardSize, p1, p2);
        initialState(boardSize);

        int consecutivePasses = 0;
        Move chosenMove = null;
        Move[] flippedTokens = new Move[0];
        Move[] validMoves;

        do
        {
            validMoves = MoveRules.findValidMoves(board.peekBoard(), curPlayer.getPlayerToken());

            renderTurnState(validMoves, chosenMove, flippedTokens);
            chosenMove = null;
            flippedTokens = new Move[0];

            if (validMoves.length > 0)
            {
                chosenMove = chooseMove(validMoves);
                applyMove(chosenMove);
                flippedTokens = flipCapturedTokens(chosenMove);

                consecutivePasses = 0;
                availableSpaces--;
            }
            else
            {
                curPlayer.passTurn();
                consecutivePasses++;
            }

            turnPhase = (availableSpaces == 0 || consecutivePasses == 2) ? TurnPhase.GAMEOVER: TurnPhase.NORMAL;

            if (turnPhase != TurnPhase.GAMEOVER)
                alternatingTurns();
        }
        while(turnPhase != TurnPhase.GAMEOVER);

        renderTurnState(validMoves, chosenMove, flippedTokens);
        showResults();
    }

    private boolean doesBoardSizeMeetRequirements(final int boardSize)
    {
        return (boardSize >= 4 && boardSize <= 16 && boardSize % 2 == 0);
    }

    private void setupGame(int boardSize, PlayerType p1, PlayerType p2)
    {
        players[0] = p1;
        players[1] = p2;

        assignPlayersIDsAndTokens();
        selectStartingPlayer();
        initializeBoard(boardSize);
    }

    private void initialState(int boardSize)
    {
        System.out.println("STARTING CONFIGURATION");
        System.out.println();
        System.out.println("Board size: " + boardSize + " x " + boardSize);
        System.out.println();

        BoardRender.printBoard(board.peekBoard(), new BoardRenderContext());

        System.out.println("Player 1: " + players[currentIndex].getPlayerID() + " (" + players[currentIndex].getPlayerToken().getSymbol() + ")");
        System.out.println("Player 2: " + players[1 - currentIndex].getPlayerID() + " (" + players[1 - currentIndex].getPlayerToken().getSymbol() + ")");
        System.out.println();

        InputValidator.readEnter("Press ENTER to start the game.", "Please press ENTER only to start the game.");
        System.out.println();
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
        board = new Board(boardSize);

        int initialRow = (boardSize/2) - 1;
        int initialCol = (boardSize/2) - 1;

        board.setCellContent(initialRow, initialCol,SquareState.BLACK);
        board.setCellContent(initialRow, initialCol+1,SquareState.WHITE);
        board.setCellContent(initialRow+1, initialCol,SquareState.WHITE);
        board.setCellContent(initialRow+1, initialCol+1,SquareState.BLACK);

        availableSpaces = (boardSize * boardSize) - 4;
    }

    private void renderTurnState(final Move[] validMoves, final Move chosenMove, final Move[] flippedTokens)
    {
        if((turnPhase == TurnPhase.START && !(curPlayer instanceof HumanPlayer)))
            return;

        System.out.println("=".repeat(35)+"\n");
        if(turnPhase != TurnPhase.START)
            GameLogger.logMoves(chosenMove, new ArrayList<>(Arrays.asList(flippedTokens)), players[1 - currentIndex].getPlayerID());

        BoardRenderContext context = new BoardRenderContext();
        context.setValidMoves(new ArrayList<>(Arrays.asList(validMoves)));
        context.setFlippedTokens(new ArrayList<>(Arrays.asList(flippedTokens)));
        context.setChosenMove(chosenMove);
        context.setShowValidMoves(curPlayer instanceof HumanPlayer);

        BoardRender.printBoard(board.peekBoard(), context);
    }

    private Move chooseMove(final Move[] validMoves)
    {
        if(validMoves == null || validMoves.length == 0)
            throw new IllegalArgumentException("Valid moves list cannot be null or empty");

        final int maxValidMoves = validMoves.length;

        final int chosenMoveIndex = curPlayer.chooseMove(board.peekBoard(),validMoves);
        if(chosenMoveIndex < 0 || chosenMoveIndex >= maxValidMoves)
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

   private void showResults()
   {
       System.out.println("=".repeat(35)+"\n");
       System.out.println("RESULTS\n");
       EndResults results = ResultsEvaluator.determineGameResult(board.peekBoard(), players[0].getPlayerID(), players[0].getPlayerToken(), players[1].getPlayerID(), players[1].getPlayerToken());
       GameLogger.logGameResults(results);
   }
}
