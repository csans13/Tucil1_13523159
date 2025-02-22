public class Piece {
    private char[][] shape;
    private int height, width;

    public Piece(char[][] shape)
    {
        this.shape = shape;
        this.height = shape.length;
        this.width = shape[0].length;
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
        return new Piece(newShape);
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
        return new Piece(newShape);
    }
}
