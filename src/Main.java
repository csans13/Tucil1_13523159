import java.io.*;
import java.util.*;

public class Main
{
    public static void main(String[] args)
    {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Masukkan nama file test case: ");
        String filename = scanner.nextLine();
        scanner.close();

        try
        {
            BufferedReader br = new BufferedReader(new FileReader("test/" + filename));
            String[] boardDimAndPieceCount = br.readLine().split(" ");
            int N = Integer.parseInt(boardDimAndPieceCount[0]);
            int M = Integer.parseInt(boardDimAndPieceCount[1]);
            int P = Integer.parseInt(boardDimAndPieceCount[2]);

            Board board = new Board(N, M);
            List<Piece> pieces = new ArrayList<>();

            for (int i = 0; i < P; i++)
            {
                char[][] shape = parsePiece(br);
                pieces.add(new Piece(shape));
            }
            br.close();

            Solver solver = new Solver(board, pieces);
            long startTime = System.currentTimeMillis();
            boolean foundSolution = solver.solve(0);
            long endTime = System.currentTimeMillis();

            if (foundSolution)
            {
                board.printBoard();
                System.out.println("Waktu pencarian: " + (endTime - startTime) + " ms");
            }
            else
            {
                System.out.println("Puzzle tidak memiliki solusi");
            }
        }
        catch (IOException e)
        {
            System.out.println("Gagal membaca file");
        }
    }

    private static char[][] parsePiece(BufferedReader br) throws IOException
    {
        List<String> lines = new ArrayList<>();
        String line;
        while ((line = br.readLine()) != null && !line.trim().isEmpty())
        {
            lines.add(line);
        }
        char[][] shape = new char[lines.size()][];
        for (int i = 0; i < lines.size(); i++)
        {
            shape[i] = lines.get(i).toCharArray();
        }
        return shape;
    }
}
