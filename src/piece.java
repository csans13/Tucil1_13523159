public class piece {
    private char[][] shape;
    private int height, width;

    public piece(char[][] shape)
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
}
