import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;

public class Day4 {
    public static void main(String[] args) throws FileNotFoundException {
        File file = new File("input4.txt");
        Scanner scanner = new Scanner(file);
        Scanner sc = new Scanner(System.in);
        System.out.println("""
                Please enter a mode:
                1: Part 1
                2: Part 2""");
        switch (sc.nextInt()) {
            case 1 -> System.out.println(part1(scanner));
            case 2 -> System.out.println(part2(scanner));
            default -> System.out.println("Invalid input, please rerun and fill in a valid index.");
        }
    }

    private static int part1(Scanner scanner) {
        List<Integer> numbers = new ArrayList<>();
        Arrays.stream(scanner.nextLine().split(",")).toList().forEach(s -> numbers.add(Integer.parseInt(s)));

        List<String> lines = new ArrayList<>();
        int boards = 0;
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            if (line.length() > 1)
                lines.add(line);
            else
                boards++;
        }
        int[][][] grid = new int[boards][5][5];
        boolean[][][] truthGrid = new boolean[boards][5][5];
        AtomicInteger i = new AtomicInteger(0);
        AtomicInteger j = new AtomicInteger(0);
        lines.forEach(s -> {
            grid[i.get()][j.get() % 5] = Arrays.stream(lines.get(j.get()).trim().split("[ ]+")).mapToInt(Integer::parseInt).toArray();
            j.getAndIncrement();
            if (j.get() % 5 == 0) {
                i.getAndIncrement();
            }
        });
        for (Integer integer : numbers) {
            for (int k = 0; k < grid.length; k++) {
                for (int l = 0; l < 5; l++) {
                    for (int m = 0; m < 5; m++) {
                        if (grid[k][l][m] == integer) {
                            truthGrid[k][l][m] = true;
                            if (checkWin(truthGrid[k])) {
                                System.out.println(integer + ", ");
                                for (int n = 0; n < 5; n++) {
                                    System.out.println(Arrays.toString(grid[k][n]));
                                }
                                for (int n = 0; n < 5; n++) {
                                    System.out.println(Arrays.toString(truthGrid[k][n]));
                                }
                                return integer * sumBoard(grid[k], truthGrid[k]);
                            }
                        }
                    }
                }
            }
        }
        return 0;
    }

    private static int part2(Scanner scanner) {
        List<Integer> numbers = new ArrayList<>();
        Arrays.stream(scanner.nextLine().split(",")).toList().forEach(s -> numbers.add(Integer.parseInt(s)));

        List<String> lines = new ArrayList<>();
        int boards = 0;
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            if (line.length() > 1)
                lines.add(line);
            else
                boards++;
        }
        int[][][] grid = new int[boards][5][5];
        boolean[][][] truthGrid = new boolean[boards][5][5];
        int[] winningScore = new int[boards];
        AtomicInteger i = new AtomicInteger(0);
        AtomicInteger j = new AtomicInteger(0);
        lines.forEach(s -> {
            grid[i.get()][j.get() % 5] = Arrays.stream(lines.get(j.get()).trim().split("[ ]+")).mapToInt(Integer::parseInt).toArray();
            j.getAndIncrement();
            if (j.get() % 5 == 0) {
                i.getAndIncrement();
            }
        });
        int lastWinner = -1;
        int winners = 0;
        for (Integer integer : numbers) {
            for (int k = 0; winners < boards && k < grid.length; k++) {
                for (int l = 0; winners < boards &&  l < 5; l++) {
                    for (int m = 0; winners < boards &&  m < 5; m++) {
                        if (grid[k][l][m] == integer) {
                            boolean wonAlready = checkWin(truthGrid[k]);
                            truthGrid[k][l][m] = true;
                            if (!wonAlready && checkWin(truthGrid[k])) {
                                winningScore[k] = integer;
                                lastWinner = k;
                                winners++;
                            }
                        }
                    }
                }
            }
        }
        for (int n = 0; n < 5; n++) {
            System.out.println(Arrays.toString(grid[lastWinner][n]));
        }
        for (int n = 0; n < 5; n++) {
            System.out.println(Arrays.toString(truthGrid[lastWinner][n]));
        }
        System.out.println(winningScore[lastWinner] + ", " + sumBoard(grid[lastWinner], truthGrid[lastWinner]));
        return winningScore[lastWinner] * sumBoard(grid[lastWinner], truthGrid[lastWinner]);
    }

    private static int sumBoard(int[][] ints, boolean[][] booleans) {
        int total = 0;
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                if (!booleans[i][j]) {
                    total += ints[i][j];
                }
            }
        }
        return total;
    }

    private static boolean checkWin(boolean[][] grid) {
        for (int i = 0; i < 5; i++) {
            boolean horWin = true;
            boolean verWin = true;
            for (int j = 0; j < 5; j++) {
                if (!grid[i][j])
                    horWin = false;
                if (!grid[j][i])
                    verWin = false;
            }
            if (horWin)
                return true;
            if (verWin)
                return true;
        }
        return false;
    }
}
