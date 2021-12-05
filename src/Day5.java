import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Day5 {
    public static void main(String[] args) throws FileNotFoundException {
        File file = new File("input5.txt");
        Scanner scanner = new Scanner(file);
        Scanner sc = new Scanner(System.in);
        int[][] grid = new int[991][991];
        System.out.println("""
                Please enter a mode:
                1: Part 1
                2: Part 2""");
        switch (sc.nextInt()) {
            case 1 -> part1(scanner, grid);
            case 2 -> part2(scanner, grid);
            default -> System.out.println("Invalid input, please rerun and fill in a valid index.");
        }
        int total = 0;
        for (int[] ints : grid) {
            for (int anInt : ints) {
                if (anInt >= 2) {
                    total++;
                }
            }
        }
        System.out.println(total);
    }

    private static void part1(Scanner scanner, int[][] grid) {
        while (scanner.hasNextLine()) {
            Segment temp = new Segment(scanner.nextLine());
            if (temp.getX1() == temp.getX2() || temp.getY1() == temp.getY2())
                temp.addToGrid(grid);
        }
    }

    private static void part2(Scanner scanner, int[][] grid) {
        while (scanner.hasNextLine())
            new Segment(scanner.nextLine()).addToGrid(grid);
    }

    public static class Segment {
        private final int x1;
        private final int y1;
        private final int x2;
        private final int y2;
        private final int xDir;
        private final int yDir;

        public Segment(String elements) {
            String[] points = elements.split(" -> ");
            String[] point1 = points[0].split(",");
            String[] point2 = points[1].split(",");
            x1 = Integer.parseInt(point1[0]);
            y1 = Integer.parseInt(point1[1]);
            x2 = Integer.parseInt(point2[0]);
            y2 = Integer.parseInt(point2[1]);
            xDir = Integer.compare(x2, x1);
            yDir = Integer.compare(y2, y1);
        }

        public void addToGrid(int[][] grid) {
            if (x1 == x2 && y1 == y2) {
                grid[y1][x1]++;
                return;
            }
            for (int i = x1, j = y1; (x2 > x1 ? (i <= x2) : (i >= x2)) && ((y2 > y1) ? (j <= y2) : (j >= y2)); i += xDir, j += yDir) {
                grid[j][i]++;
            }
        }

        public int getY1() {
            return y1;
        }

        public int getX2() {
            return x2;
        }

        public int getY2() {
            return y2;
        }

        public int getX1() {
            return x1;
        }
    }
}
