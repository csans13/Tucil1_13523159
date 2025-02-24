import java.util.ArrayList;
import java.util.List;

public class Solver
{
    private static void swapPieces(List<Piece> pieces, int i, int j)
    {
        Piece temp = pieces.get(i);
        pieces.set(i, pieces.get(j));
        pieces.set(j, temp);
    }

    private static void getPermsHelper(List<Piece> pieces, int index, List<List<Piece>> perms)
    {
        if (index == pieces.size())
        {
            perms.add(new ArrayList<>(pieces));
            return;
        }
        for (int i = index; i < pieces.size(); i++)
        {
            swapPieces(pieces, index, i);
            getPermsHelper(pieces, index + 1, perms);
            swapPieces(pieces, index, i);
        }
    }

    private static List<List<Piece>> getPerms(List<Piece> pieces)
    {
        List<List<Piece>> perms = new ArrayList<>();
        getPermsHelper(pieces, 0, perms);
        return perms;
    }

    public static long tryAllConfig(Board board, List<Piece> pieces, long attempts)
    {
        List<List<Piece>> allPerms = getPerms(pieces);
        for (List<Piece> perm : allPerms)
        {
            attempts++;
            board.clear();

            if (board.areAllPlaceable(perm)) return attempts;
        }
        return -attempts;
    }
}
