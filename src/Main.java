import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;
import java.util.List;
import javax.imageio.ImageIO;

public class Main
{
    private static int rows, cols;
    private static final Map<Character, String> CLI_COLORS = new HashMap<>();
    private static final String ANSI_RESET = "\u001B[0m";

    private static int parsePositiveInt(Scanner scanner, String missingMessage, String invalidMessage) throws Exception
    {
        if (!scanner.hasNextInt())
        {
            throw new Exception(missingMessage);
        }
        int value = scanner.nextInt();
        if (value <= 0)
        {
            throw new Exception(invalidMessage);
        };
        return value;
    }

    private static char validatePieceLine(String line) throws Exception
    {
        String upperCaseLine = line.trim().toUpperCase();
        char firstChar = upperCaseLine.charAt(0);
        for (char c : upperCaseLine.toCharArray())
        {
            if (!Character.isLetter(c) && c != ' ')
            {
                throw new Exception("Terdapat baris yang mengandung karakter bukan alphabet.");
            }
            if (c != firstChar && c != ' ')
            {
                throw new Exception("Terdapat baris yang mengandung lebih dari satu huruf.");
            }
        }
        return firstChar;
    }

    // Menguraikan input dan memvalidasi strukturnya
    private static List<Piece> parseInput(Scanner scanner) throws Exception
    {
        rows = parsePositiveInt(scanner, "Tidak ditemukan N.", "N harus lebih dari 0.");
        cols = parsePositiveInt(scanner, "Tidak ditemukan M.", "M harus lebih dari 0.");
        int pieceCount = parsePositiveInt(scanner, "Tidak ditemukan P.", "P harus lebih dari 0.");

        scanner.nextLine(); 
        if (!scanner.hasNextLine())
        {
            throw new Exception("Tidak ditemukan tipe konfigurasi.");
        }
        String configType = scanner.nextLine().trim(); // configuration type
        if (configType.isEmpty())
        {
            throw new Exception("Harap masukkan tipe konfigurasi.");
        }
        if (!configType.equalsIgnoreCase("default"))
        {
            throw new Exception("Tipe konfigurasi yang tersedia adalah 'default'.");
        }

        Map<Character, List<String>> pieceData = new LinkedHashMap<>();
        Set<Character> usedLetters = new HashSet<>();
        Character currentPiece = null;

        while (scanner.hasNextLine())
        {
            String line = scanner.nextLine();
            if (line.trim().isEmpty())
            {
                currentPiece = null;
                continue;
            }

            char identifier = validatePieceLine(line);
            if (currentPiece != null && identifier != currentPiece && usedLetters.contains(identifier))
            {
                throw new Exception("Huruf '" + identifier + "' digunakan oleh lebih dari satu piece terpisah.");
            }

            usedLetters.add(identifier);
            pieceData.putIfAbsent(identifier, new ArrayList<>());
            pieceData.get(identifier).add(line);
            currentPiece = identifier;
        }

        if (pieceData.size() != pieceCount)
        {
            throw new Exception("Banyaknya blok yang dimasukkan tidak sesuai dengan P.");
        }

        List<Piece> pieces = new ArrayList<>();
        for (Map.Entry<Character, List<String>> entry : pieceData.entrySet())
        {
            pieces.add(new Piece(entry.getValue(), entry.getKey()));
        }
        return pieces;
    }

    // Memberikan warna pada blok berdasarkan huruf unik
    private static void mapPieceColors(List<Piece> pieces)
    {
        for (Piece piece : pieces)
        {
            char identifier = piece.getId();
            if (!CLI_COLORS.containsKey(identifier))
            {
                int colorIndex = identifier - 'A';
                CLI_COLORS.put(identifier, (colorIndex >= 0 && colorIndex < ColorMap.ANSI_PALETTE.length) ? ColorMap.ANSI_PALETTE[colorIndex] : ANSI_RESET);
            }
        }
    }
    
    // Mencetak solusi papan dalam CLI
    private static void printBoard(Board board)
    {
        System.out.println("\nSolusi ditemukan.");
        for (char[] row : board.getGrid())
        {
            for (char cell : row)
            {
                System.out.print((cell == ' ') ? cell : CLI_COLORS.get(cell) + cell + ANSI_RESET);
            }
            System.out.println();
        }
        System.out.println();
    }

    // Menyimpan solusi dalam file .txt
    private static void saveSolution(Board board, Scanner scanner)
    {
        System.out.println();
        System.out.print("Simpan solusi sebagai teks? (ya/tidak) ");
        String userInput = scanner.nextLine();

        while (!userInput.equalsIgnoreCase("ya") && !userInput.equalsIgnoreCase("tidak") && !userInput.isEmpty())
        {
            System.out.println();
            System.out.print("Jawaban tidak valid. Masukkan 'ya' atau 'tidak': ");
            userInput = scanner.nextLine();
        }

        if (!userInput.equalsIgnoreCase("ya"))
        {
            return;
        }

        System.out.print("Masukkan nama file output: ");
        String fileName = scanner.nextLine();

        File dir = new File("../test");
        if (!dir.exists())
        {
            dir.mkdirs();
        }

        while (fileName.isEmpty() || !fileName.endsWith(".txt")) {
            System.out.println("Nama file tidak boleh kosong dan harus berekstensi .txt");
            System.out.println();
            System.out.print("Masukkan nama file output: ");
            fileName = scanner.nextLine();
        }

        fileName = "../test/" + fileName;

        try (PrintWriter writer = new PrintWriter(new File(fileName)))
        {
            for (int i = 0; i < board.getGrid().length; i++)
            {
                writer.print(new String(board.getGrid()[i]));
                if (i < board.getGrid().length - 1)
                {
                    writer.println(); 
                }
            }
            System.out.println("Solusi berhasil disimpan di " + fileName);
        } catch (FileNotFoundException e) {
            System.err.println("Terjadi kesalahan: " + e.getMessage());
        }
    }

    // Draws centered text on the board
    private static void drawCenteredText(Graphics2D g, char cell, int col, int row, int cellSize, FontMetrics fm) {
        String text = String.valueOf(cell);
        int textWidth = fm.stringWidth(text);
        int textHeight = fm.getAscent();
        
        g.setColor(g.getColor().getRed() + g.getColor().getGreen() + g.getColor().getBlue() < 382 ? Color.WHITE : Color.BLACK);
        int textX = col * cellSize + (cellSize - textWidth) / 2;
        int textY = row * cellSize + (cellSize + textHeight) / 2 - 5;
        g.drawString(text, textX, textY);
    }

    // Saves the generated image to a file
    private static void saveImageToFile(BufferedImage img, String filePath) {
        try {
            File directory = new File("../test");
            if (!directory.exists()) directory.mkdirs();
            ImageIO.write(img, "png", new File(filePath));
            System.out.println("Solusi disimpan sebagai: " + filePath);
        } catch (IOException e) {
            System.out.println("Gagal menyimpan gambar: " + e.getMessage());
        }
    }

    // Menghasilkan dan menyimpan gambar solusi
    private static void generateImage(Board board, String filePath) {
        int cellSize = 50;
        int imgWidth = board.getGrid()[0].length * cellSize;
        int imgHeight = board.getGrid().length * cellSize;

        BufferedImage img = new BufferedImage(imgWidth, imgHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = img.createGraphics();

        g.setColor(Color.WHITE);
        g.fillRect(0, 0, imgWidth, imgHeight);
        g.setFont(new Font("TimesRoman", Font.BOLD, cellSize / 2));
        FontMetrics fm = g.getFontMetrics();

        for (int row = 0; row < board.getGrid().length; row++) {
            for (int col = 0; col < board.getGrid()[0].length; col++) {
                char cell = board.getGrid()[row][col];
                if (cell != ' ') {
                    g.setColor(ColorMap.IMAGE_COLORS.getOrDefault(cell, Color.BLACK));
                    g.fillRect(col * cellSize, row * cellSize, cellSize, cellSize);
                }
                g.setColor(Color.BLACK);
                g.drawRect(col * cellSize, row * cellSize, cellSize, cellSize);

                if (cell != ' ') {
                    drawCenteredText(g, cell, col, row, cellSize, fm);
                }
            }
        }
        g.dispose();
        saveImageToFile(img, filePath);
    }

    // Mengekspor solusi sebagai file gambar
    private static void exportSolutionImage(Board board, Scanner scanner)
    {
        System.out.println();
        System.out.println("\nSimpan solusi sebagai gambar? (ya/tidak)");
        String userChoice = scanner.nextLine();

        while (!userChoice.equalsIgnoreCase("ya") && !userChoice.equalsIgnoreCase("tidak") && !userChoice.isEmpty()) {
            System.out.println();
            System.out.println("Jawaban tidak valid. Masukkan 'ya' atau 'tidak':");
            userChoice = scanner.nextLine();
        }

        if (userChoice.equalsIgnoreCase("ya")) {
            System.out.print("Masukkan nama file (format PNG): ");
            String fileName = scanner.nextLine();

            while (fileName.isEmpty() || !fileName.endsWith(".png")) {
                System.out.println("Nama file tidak boleh kosong dan harus berekstensi .png");
                System.out.println();
                System.out.print("Masukkan nama file (format PNG): ");
                fileName = scanner.nextLine();
            }

            String filePath = "../test/" + fileName;
            generateImage(board, filePath);
        }
    }

    private static void displayResults(Board board, long attempts, long start, long end, Scanner scanner)
    {
        long duration = (end - start) / 1_000_000; // Convert ns to ms
        
        if (attempts > 0 && board.isFull()) {
            printBoard(board);
            System.out.println("Banyaknya kasus yang ditinjau: " + attempts);
            System.out.println("Waktu eksekusi: " + duration + " ms");
            saveSolution(board, scanner);
            exportSolutionImage(board, scanner);
        } else {
            System.out.println();
            System.out.println("Tidak ada solusi.");
            System.out.println();
            System.out.println("Banyaknya kasus yang ditinjau: " + Math.abs(attempts));
            System.out.println("Waktu eksekusi: " + duration + " ms");
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("Selamat datang di IQ Puzzle Pro Solver!");
        System.out.println("Program ini dirancang untuk menyelesaikan IQ Puzzle Pro.");
        System.out.println();
        System.out.print("Silakan masukkan nama file puzzle: ");
        String filename = scanner.nextLine();

        while (filename.isEmpty() || !filename.endsWith(".txt")) {
            System.out.println("Nama file tidak boleh kosong dan harus berekstensi .txt");
            System.out.println();
            System.out.print("Silakan masukkan nama file puzzle: ");
            filename = scanner.nextLine();
        }
        
        try
        {
            File file = new File(filename);
            Scanner fileScanner = new Scanner(file);
            try
            {
                List<Piece> pieces = parseInput(fileScanner);
                mapPieceColors(pieces);
                
                Board board = new Board(rows, cols);
                for (char[] row : board.getGrid())
                {
                    Arrays.fill(row, ' ');
                }
                long attempts = 0;
                long start = System.nanoTime();
                attempts = Solver.tryAllConfig(board, pieces, attempts);
                long end = System.nanoTime();
                
                displayResults(board, attempts, start, end, scanner);
            }
            catch (Exception e)
            {
                System.out.println("Terjadi kesalahan: " + e.getMessage());
            }
        }
        catch (FileNotFoundException e)
        {
            System.out.println("Terjadi kesalahan: File tidak ditemukan.");
        }
        
        scanner.close();
    }
}
