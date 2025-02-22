import java.util.List;

public class Solver {
    private Board board;
    private List<Piece> pieces;

    public Solver(Board board, List<Piece> pieces)
    {
        this.board = board;
        this.pieces = pieces;
    }

    public boolean solve(int index)
    {
        if (index == pieces.size())
        {
            return board.isFull(); // Ditemukan solusi apabila semua blok telah dicoba dan papan terisi penuh
        }

        Piece piece = pieces.get(index);

        // Meletakkan blok di setiap posisi dengan rotasi dan pencerminan
        for (int i = 0; i < board.getRows(); i++)
        {
            for (int j = 0; j < board.getCols(); j++)
            {
                for (int k = 0; k < 4; k++)
                // Mencoba 4 bentuk blok (rotasi)
                {
                    for (int l = 0; l < 2; l++)
                    // Mencoba 2 bentuk blok (pencerminan)
                    {
                        if (board.isPlaceable(piece, i, j))
                        {
                            board.placePiece(piece, i, j, (char) ('A' + index));
                            if (solve(index + 1))
                            {
                                return true;
                            }
                            board.removePiece(piece, i, j); // Backtrack
                        }
                        piece = piece.flip();
                    }
                    piece = piece.rotate();
                }
            }
        }
        return false;
    }
}
