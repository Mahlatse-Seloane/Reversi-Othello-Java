import java.util.Random;

public class GameManager
{
    private Board board;
    private int curBoardSize;
    private int availableSpaces;
    private PlayerType[] players;
    private int currentIndex = 0;
    private PlayerType curPlayer;
    private SquareState curPToken = SquareState.WHITE;
    private static final int[][] dir = {{-1, 0},{-1, 1},{0, 1},{1, 1},{1, 0},{1, -1},{0, -1},{-1, -1}};

    GameManager()
    {
        players = new PlayerType[2];
    }

    public void simulateSingleGame(final int boardSize, PlayerType p1, PlayerType p2)
    {
        if(!doesBoardSizeMeetRequirements(boardSize))
            return;

        if(p1 == null || p2 == null)
            throw new IllegalArgumentException("Players cannot be null");

        players[0] = p1;
        players[1] = p2;

        assignPlayersIDsAndTokens();
        initializeBoard(boardSize);

        System.out.println("Board size: " + boardSize + " x " + boardSize);
        GameLogger.printBoard(board.peekBoard(),curBoardSize);

        int conservativePasses = 0;
        while(availableSpaces > 0 && conservativePasses < 2)
        {
            conservativePasses++;
            Move[] validMoves = MoveInspector.findValidMoves(board.peekBoard(), curPToken);
            if(validMoves.length > 0)
            {
                Move chosenMove = chooseMove(validMoves);
                applyMove(chosenMove); flipOpponentTokens(chosenMove);
                GameLogger.printBoard(board.peekBoard(), curBoardSize);
                conservativePasses = 0;
            }

            availableSpaces--;
        }
    }

    private boolean doesBoardSizeMeetRequirements(final int boardSize)
    {
        return (boardSize >= 4 && boardSize <= 16 && boardSize % 2 == 0);
    }

    private void assignPlayersIDsAndTokens()
    {
        players[0].setPlayerID("alg1");
        players[0].setPlayerToken(SquareState.WHITE);

        players[1].setPlayerID("alg2");
        players[1].setPlayerToken(SquareState.BLACK);
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

    private Move chooseMove(Move[] validMoves)
    {
        if(validMoves == null)
            throw new IllegalArgumentException("Valid moves list cannot be null");

        final int maxValidMoves = validMoves.length;

        Random random = new Random();
        final int chosenMoveIndex = random.nextInt(0, maxValidMoves);
        if(chosenMoveIndex < 0 || chosenMoveIndex > maxValidMoves)
            throw new IndexOutOfBoundsException("Chosen move index: " + chosenMoveIndex + " is out of bounds.");

        return validMoves[chosenMoveIndex];
    }

    private void applyMove(Move chosenMove)
    {
        if(chosenMove == null)
            throw new IllegalArgumentException("Chosen move cannot be null");

        board.setCellContent(chosenMove.row(),chosenMove.col(), curPToken);
    }

    private void flipOpponentTokens(Move chosenMove)
    {
        if(chosenMove == null)
            throw new IllegalArgumentException("Chosen move cannot be null");

        for(int i = 0; i < 8; i++)
        {
            int row = chosenMove.row(), col = chosenMove.col();
            int dRow = dir[i][0], dCol = dir[i][1];
            int maxSteps = switch (i)
            {
                case 0 -> MoveInspector.countUpFlips(board.peekBoard(),curPToken,row,col);
                case 1 -> MoveInspector.countDiagonalUpRightFlips(board.peekBoard(),curPToken,row,col);
                case 2 -> MoveInspector.countRightFlips(board.peekBoard(),curPToken,row,col);
                case 3 -> MoveInspector.countDiagonalDownRightFlips(board.peekBoard(),curPToken,row,col);
                case 4 -> MoveInspector.countDownFlips(board.peekBoard(),curPToken,row,col);
                case 5 -> MoveInspector.countDiagonalDownLeftFlips(board.peekBoard(),curPToken,row,col);
                case 6 -> MoveInspector.countLeftFlips(board.peekBoard(),curPToken,row,col);
                case 7 -> MoveInspector.countDiagonalUpLeftFlips(board.peekBoard(),curPToken,row,col);
                default -> throw new IllegalStateException("Unexpected value: " + i);
            };

            for (int j = 0; j < maxSteps; j++)
            {
                row += dRow;
                col += dCol;
                board.setCellContent(row, col, curPToken);
            }
        }
    }
}
