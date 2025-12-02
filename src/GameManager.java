public class GameManager
{
    private Board board;
    private int currentBoardSize;
    private int availableSpaces;

    GameManager()
    {

    }

    private void initializeBoard()
    {
        board = new Board(currentBoardSize);

        int initialRow = (currentBoardSize/2) - 1;
        int initialCol = (currentBoardSize/2) - 1;

        board.setCellContent(initialRow, initialCol,SquareState.BLACK);
        board.setCellContent(initialRow, initialCol+1,SquareState.WHITE);
        board.setCellContent(initialRow+1, initialCol,SquareState.WHITE);
        board.setCellContent(initialRow+1, initialCol+1,SquareState.BLACK);

        availableSpaces = (currentBoardSize * currentBoardSize) - 4;
    }
}
