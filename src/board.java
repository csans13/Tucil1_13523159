public class board {
    private int rows, cols;
    private char[][] grid;

    public board(int rows, int cols)
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

    public boolean isPlaceable(piece piece, int row, int col)
    // Apakah suatu blok dapat ditempatkan pada posisi tertentu
    {
        for (int i = 0; i < piece.getHeight(); i++)
        {
            for (int j = 0; j < piece.getWidth(); j++)
            {
                if (piece.getShape()[i][j] != ' ') // Jika blok mengisi suatu space pada papan
                {
                    int boardRow = row + i;
                    int boardCol = col + j;
                    if (boardRow >= rows || boardCol >= cols || grid[boardRow][boardCol] != ' ')
                    {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public void placePiece(piece piece, int row, int col, char symbol)
    // Menempatkan blok pada papan
    {
        for (int i = 0; i < piece.getHeight(); i++)
        {
            for (int j = 0; j < piece.getWidth(); j++)
            {
                if (piece.getShape()[i][j] != ' ')
                {
                    grid[row + i][col + j] = symbol;
                }
            }
        }
    }

    public void removePiece(piece piece, int row, int col)
    // Menghapus blok dari papan (backtracking)
    {
        for (int i = 0; i < piece.getHeight(); i++)
        {
            for (int j = 0; j < piece.getWidth(); j++)
            {
                if (piece.getShape()[i][j] != ' ')
                {
                    grid[row + i][col + j] = ' ';   
                }   
            }            
        }
    }

    public boolean isFull()
    // Apakah papan telah terisi penuh
    {
        for (int i = 0; i < rows; i++)
        {
            for (int j = 0; j < cols; j++)
            {
                if (grid[i][j] == ' ') // terdapat space kosong
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
