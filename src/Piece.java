import java.util.List;
import java.util.ArrayList;

public class Piece
    {
    private final char id;
    private char[][] shape;
    private int height, width;

    public Piece(List<String> shapeLine, char id)
    {
        this.id = id;

        int htemp = shapeLine.size();
        int wtemp = shapeLine.stream().mapToInt(String::length).max().orElse(0);

        shape = new char[htemp][wtemp];
        for (int i = 0; i < htemp; i++)
        {
            String line = shapeLine.get(i);
            for (int j = 0; j < wtemp; j++)
            {
                shape[i][j] = (j < line.length()) ? line.charAt(j) : ' ';
            }
        }

        this.height = shape.length;
        this.width = shape[0].length;
    }

    public char getId()
    {
        return id;
    }

    public char[][] getShape()
    {
        return shape;
    }

    public int getHeight()
    {
        return height;
    }

    public int getWidth()
    {
        return width;
    }

    private List<String> charArrayToList(char[][] arr)
    {
        List<String> list = new ArrayList<>();
        for (char[] row : arr)
        {
            list.add(new String(row));
        }
        return list;
    }

    public Piece rotate()
    // Blok dirotasikan searah jarum jam
    {
        char[][] newShape = new char[width][height];

        for (int i = 0; i < height; i++)
        {
            for (int j = 0; j < width; j++)
            {
                newShape[j][height - 1 - i] = shape[i][j];
            }
        }
        return new Piece(charArrayToList(newShape), id);
    }

    public Piece flip()
    // Blok dicerminkan
    {
        char[][] newShape = new char[height][width];
        for (int i = 0; i < height; i++)
        {
            for (int j = 0; j < width; j++)
            {
                newShape[i][width - i - j] = shape[i][j];
            }   
        }
        return new Piece(charArrayToList(newShape), id);
    }

    // Menghasilkan semua transformasi unik dari suatu blok
    public List<Piece> getAllTransformations()
    {
        List<Piece> transformations = new ArrayList<>();
        transformations.add(this);
        
        Piece rotated = this;
        for (int i = 0; i < 3; i++)
        {
            rotated = rotated.rotate();
            transformations.add(rotated);
        }

        Piece flipped = this.flip();
        transformations.add(flipped);

        rotated = flipped;
        for (int i = 0; i < 3; i++)
        {
            rotated = rotated.rotate();
            transformations.add(rotated);
        }

        return transformations;
    }

    // Pencetakan blok
    public void printPiece()
    {
        for (char[] row : shape)
        {
            System.out.println(new String(row));
        }
        System.out.println();
    }
}
