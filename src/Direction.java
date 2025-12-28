public enum Direction
{
    UP(-1, 0),
    DIAGONAL_UP_RIGHT(-1, 1),
    RIGHT(0, 1),
    DIAGONAL_DOWN_RIGHT(1, 1),
    DOWN(1, 0),
    DIAGONAL_DOWN_LEFT(1, -1),
    LEFT(0, -1),
    DIAGONAL_UP_LEFT(-1, -1);

    private final int rowDelta;
    private final int colDelta;

    Direction(int rowDelta, int colDelta)
    {
        this.rowDelta = rowDelta;
        this.colDelta = colDelta;
    }

    public int getRowDelta()
    {
        return rowDelta;
    }

    public int getColDelta()
    {
        return colDelta;
    }
}
