import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;

public class Day11 {
    public static final int width = 10;
    public static final int height = 10;
    public static final int steps = 100;

    public static void main(String[] args) throws FileNotFoundException {
        File file = new File("input11.txt");
        Scanner scanner = new Scanner(file);
        int[][] grid = new int[height][width];
        for (int i = 0; i < height; i++) {
            int[] row = new int[width];
            AtomicInteger j = new AtomicInteger(0);
            Arrays.stream(scanner.nextLine().split("")).toList().forEach(s -> {
                row[j.get()] = Integer.parseInt(s);
                j.getAndIncrement();
            });
            grid[i] = row;
        }
        Scanner sc = new Scanner(System.in);
        System.out.println("""
                Please enter a mode:
                1: Part 1
                2: Part 2""");
        switch (sc.nextInt()) {
            case 1 -> System.out.println(part1(grid));
            case 2 -> System.out.println(part2(grid));
            default -> System.out.println("Invalid input, please rerun and fill in a valid index.");
        }
    }

    private static int part1(int[][] grid) {
        int flashes = 0;
        for (int count = 0; count < steps; count++) {
//            System.out.println(count);
//            for (int[] ints : grid) {
//                System.out.println(Arrays.toString(ints));
//            }
            for (int[] row : grid) {
                for (int k = 0; k < row.length; k++) {
                    row[k]++;
                }
            }
            boolean updated = true;
            while (updated) {
                updated = false;
                int[][] newGrid = grid.clone();

                for (int j = 0, gridLength = grid.length; j < gridLength; j++) {
                    int[] row = grid[j];
                    for (int k = 0, rowLength = row.length; k < rowLength; k++) {
                        int i = row[k];
                        if (i == 10) {
                            flashes++;
//                            System.out.printf("Flashes: %d\n", flashes);
                            updated = true;
                            if (j > 0) {
                                if (k > 0) {
                                    if (newGrid[j - 1][k - 1] != 10)
                                        newGrid[j - 1][k - 1]++;
                                }
                                if (k + 1 < width) {
                                    if (newGrid[j - 1][k + 1] != 10)
                                    newGrid[j - 1][k + 1]++;
                                }
                                if (newGrid[j - 1][k] != 10)
                                newGrid[j - 1][k]++;
                            }
                            if (j + 1 < height) {
                                if (k > 0) {
                                    if (newGrid[j + 1][k - 1] != 10)
                                    newGrid[j + 1][k - 1]++;
                                }
                                if (k + 1 < width) {
                                    if (newGrid[j + 1][k + 1] != 10)
                                    newGrid[j + 1][k + 1]++;
                                }
                                if (newGrid[j + 1][k] != 10)
                                newGrid[j + 1][k]++;
                            }
                            if (k > 0) {
                                if (newGrid[j][k - 1] != 10)
                                newGrid[j][k - 1]++;
                            }
                            if (k + 1 < width) {
                                if (newGrid[j][k + 1] != 10)
                                newGrid[j][k + 1]++;
                            }
                            row[k]++;
                        }
                    }
                }
                grid = newGrid;
            }
            for (int[] row : grid) {
                for (int k = 0; k < row.length; k++) {
                    if (row[k] > 9) {
                        row[k] = 0;
                    }
                }
            }
        }
        return flashes;
    }

    private static int part2(int[][] grid) {
        int count = 0;
        while (true) {
//            System.out.println(count);
//            for (int[] ints : grid) {
//                System.out.println(Arrays.toString(ints));
//            }
            for (int[] row : grid) {
                for (int k = 0; k < row.length; k++) {
                    row[k]++;
                }
            }
            boolean updated = true;
            while (updated) {
                updated = false;
                int[][] newGrid = grid.clone();
                for (int j = 0, gridLength = grid.length; j < gridLength; j++) {
                    int[] row = grid[j];
                    for (int k = 0, rowLength = row.length; k < rowLength; k++) {
                        int i = row[k];
                        //System.out.println(i + ", " + k + ", " + j);
                        if (i == 10) {
                            updated = true;
                            if (j > 0) {
                                if (k > 0) {
                                    if (newGrid[j - 1][k - 1] != 10)
                                        newGrid[j - 1][k - 1]++;
                                }
                                if (k + 1 < width) {
                                    if (newGrid[j - 1][k + 1] != 10)
                                        newGrid[j - 1][k + 1]++;
                                }
                                if (newGrid[j - 1][k] != 10)
                                    newGrid[j - 1][k]++;
                            }
                            if (j + 1 < height) {
                                if (k > 0) {
                                    if (newGrid[j + 1][k - 1] != 10)
                                        newGrid[j + 1][k - 1]++;
                                }
                                if (k + 1 < width) {
                                    if (newGrid[j + 1][k + 1] != 10)
                                        newGrid[j + 1][k + 1]++;
                                }
                                if (newGrid[j + 1][k] != 10)
                                    newGrid[j + 1][k]++;
                            }
                            if (k > 0) {
                                if (newGrid[j][k - 1] != 10)
                                    newGrid[j][k - 1]++;
                            }
                            if (k + 1 < width) {
                                if (newGrid[j][k + 1] != 10)
                                    newGrid[j][k + 1]++;
                            }
                            row[k]++;
                        }
                    }
                }
                grid = newGrid;
            }
            for (int[] row : grid) {
                for (int k = 0; k < row.length; k++) {
                    if (row[k] > 9) {
                        row[k] = 0;
                    }
                }
            }
            int total = 0;
            for (int[] row : grid) {
                for (int i : row) {
                    if (i == 0) {
                        total++;
                    }
                }
            }
            count++;
            if (total == width * height) {
                break;
            }
        }
        return count;
    }
}
