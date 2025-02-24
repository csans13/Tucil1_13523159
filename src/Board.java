import java.util.*;

public class Board {
    private int rows, cols;
    private char[][] grid;

    public Board(int rows, int cols)
    {
        this.rows = rows;
        this.cols = cols;
        this.grid = new char[rows][cols];

        // Papan mula-mula kosong
        for (int i = 0; i < rows; i++)
        {
            for (int j = 0; j < cols; j++)
            {
                grid[i][j] = ' '; // Menandakan space kosong
            }
        }
    }

    public int getRows()
    {
        return rows;
    }

    public int getCols()
    {
        return cols;
    }

    public char[][] getGrid()
    {
        return grid;
    }

    public void clear()
    {
        for (char[] row : grid)
        {
            Arrays.fill(row, ' ');
        }
    }

    private boolean isPlaceable(Piece piece, int x, int y)
    // Apakah suatu blok dapat ditempatkan pada posisi tertentu
    {
        char[][] shape = piece.getShape();
        int height = piece.getHeight();
        int width = piece.getWidth();

        if (x + height > rows || y + width > cols) return false;
            
        for (int i = 0; i < height; i++)
        {
            for (int j = 0; j < width; j++)
            {
                if (shape[i][j] != ' ' && grid[x + i][y + j] != ' ')
                {
                    return false;
                }
            }
        }
        return true;
    }

    private void placePiece(Piece piece, int x, int y)
    // Menempatkan blok pada papan
    {
        char[][] shape = piece.getShape();
        int height = piece.getHeight();
        int width = piece.getWidth();

        for (int i = 0; i < height; i++)
        {
            for (int j = 0; j < width; j++)
            {
                if (shape[i][j] != ' ')
                {
                    grid[x + i][y + j] = piece.getId();
                }
            }
        }
    }

    public boolean areAllPlaceable(List<Piece> pieces)
    {
        for (Piece piece : pieces)
        {
            boolean placed = false;

            for (Piece transformation : piece.getAllTransformations())
            {
                for (int i = 0; i < rows; i++)
                {
                    for (int j = 0; j < cols; j++)
                    {
                        if (isPlaceable(transformation, i, j))
                        {
                            placePiece(transformation, i, j);
                            placed = true;
                            break;
                        }
                    }
                    if (placed) break;
                }
                if (placed) break;
            }
            if (!placed) return false;
        }
        return true;
    }

    public boolean isFull()
    // Apakah papan telah terisi penuh
    {
        for (char[] row : grid)
        {
            for (char cell : row)
            {
                if (cell == ' ') // terdapat space kosong
                {
                    return false;
                }
            }
        }
        return true;
    }

    public void printBoard()
    // Cetak papan
    {
        for (int i = 0; i < rows; i++)
        {
            for (int j = 0; j < cols; j++)
            {
                System.out.print(grid[i][j]);
            }
            System.out.println();
        }
        System.out.println();
    }
}
