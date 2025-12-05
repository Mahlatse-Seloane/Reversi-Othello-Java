public class GameManager
{
    private Board board;
    private int curBoardSize;
    private int availableSpaces;
    private SquareState curPToken = SquareState.WHITE;

    GameManager()
    {

    }

    public void simulateSingleGame(final int boardSize)
    {
        if(!doesBoardSizeMeetRequirements(boardSize))
            return;

        initializeBoard(boardSize);

        System.out.println("Board size: " + boardSize + " x " + boardSize);
        GameLogger.printBoard(board.peekBoard(),curBoardSize);
    }

    private boolean doesBoardSizeMeetRequirements(final int boardSize)
    {
        return (boardSize >= 4 && boardSize <= 16 && boardSize % 2 == 0);
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
}
