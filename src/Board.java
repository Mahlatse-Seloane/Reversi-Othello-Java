import java.util.Arrays;

public class Board
{
    private SquareState[][] board;
    private final int boardSize;

    Board(final int boardSize)
    {
        this.boardSize = boardSize;
        createBoard();
    }

    public void createBoard()
    {
        this.board = new SquareState[boardSize][boardSize];

        for(int i = 0; i < boardSize; i++)
            Arrays.fill(board[i], SquareState.EMPTY);
    }

    public SquareState[][] peekBoard()
    {
        SquareState[][] copy = new SquareState[boardSize][];
        for (int i = 0; i < boardSize; i++)
            copy[i] = Arrays.copyOf(board[i], boardSize);

        return copy;
    }

    public void setCellContent(final int row,final int col,final SquareState square)
    {
        if(row < 0 || row >= boardSize)
            throw new IndexOutOfBoundsException("Row: " + row + " is out the bounds.");
        if(col < 0 || col >= boardSize)
            throw new IndexOutOfBoundsException("Col: " + col + " is out the bounds.");

        this.board[row][col] = square;
    }

    public SquareState lookAtCellContent(final int row,final int col)
    {
        if(row < 0 || row >= boardSize)
            throw new IndexOutOfBoundsException("Row: " + row + " is out the bounds.");
        if(col < 0 || col >= boardSize)
            throw new IndexOutOfBoundsException("Col: " + col + " is out the bounds.");

        return this.board[row][col];
    }
}
