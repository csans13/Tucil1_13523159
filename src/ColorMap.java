import java.awt.*;
import java.util.*;

public class ColorMap
{
    public static final String[] ANSI_PALETTE =
    {
        "\u001B[31m", "\u001B[32m", "\u001B[33m", "\u001B[34m", "\u001B[35m", "\u001B[36m",
        "\u001B[91m", "\u001B[92m", "\u001B[93m", "\u001B[94m", "\u001B[95m", "\u001B[96m",
        "\u001B[103m", "\u001B[90m", "\u001B[97m", "\u001B[30m", "\u001B[41m", "\u001B[42m",
        "\u001B[43m", "\u001B[44m", "\u001B[45m", "\u001B[46m", "\u001B[104m", "\u001B[100m",
        "\u001B[101m", "\u001B[102m"
    };

    public static final Map<Character, Color> IMAGE_COLORS = new HashMap<>();
    static
    {
        IMAGE_COLORS.put('A', new Color(255, 0, 0));   IMAGE_COLORS.put('B', new Color(0, 255, 0));
        IMAGE_COLORS.put('C', new Color(255, 255, 0)); IMAGE_COLORS.put('D', new Color(0, 0, 255));
        IMAGE_COLORS.put('E', new Color(255, 0, 255)); IMAGE_COLORS.put('F', new Color(0, 255, 255));
        IMAGE_COLORS.put('G', new Color(250, 85, 85)); IMAGE_COLORS.put('H', new Color(85, 255, 85));
        IMAGE_COLORS.put('I', new Color(255, 255, 85)); IMAGE_COLORS.put('J', new Color(85, 85, 255));
        IMAGE_COLORS.put('K', new Color(255, 85, 255)); IMAGE_COLORS.put('L', new Color(85, 255, 255));
        IMAGE_COLORS.put('M', new Color(255, 255, 85)); IMAGE_COLORS.put('N', new Color(85, 85, 85));
        IMAGE_COLORS.put('O', new Color(200, 200, 200)); IMAGE_COLORS.put('P', new Color(0, 0, 0));
        IMAGE_COLORS.put('Q', new Color(255, 0, 0)); IMAGE_COLORS.put('R', new Color(0, 255, 0));
        IMAGE_COLORS.put('S', new Color(255, 255, 0)); IMAGE_COLORS.put('T', new Color(0, 0, 255));
        IMAGE_COLORS.put('U', new Color(255, 0, 255)); IMAGE_COLORS.put('V', new Color(0, 255, 255));
        IMAGE_COLORS.put('W', new Color(0, 0, 128)); IMAGE_COLORS.put('X', new Color(85, 85, 85));
        IMAGE_COLORS.put('Y', new Color(255, 85, 85)); IMAGE_COLORS.put('Z', new Color(85, 255, 85));
    }
}
