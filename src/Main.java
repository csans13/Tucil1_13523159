import java.io.*;
import java.util.*;

public class Main
{
    public static void main(String[] args)
    {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Masukkan nama file test case: ");
        String filename = scanner.nextLine();
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
