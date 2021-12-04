import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

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
        List<BingoCard> boards = new ArrayList<>();
        List<String> lines = new ArrayList<>();
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            if (line.length() > 1)
                lines.add(line);
        }
        int i = 0;
        for (int j = 0; j < lines.size() / 5; j++) {
            BingoCard bingoCard = new BingoCard();
            for (int k = 0; k < 5; k++, i++) {
                bingoCard.setGridRow(k, Arrays.stream(lines.get(i).trim().split("[ ]+")).mapToInt(Integer::parseInt).toArray());
            }
            boards.add(bingoCard);
        }
        for (Integer integer : numbers) {
            for (BingoCard bingoCard : boards) {
                for (int l = 0; l < 5; l++) {
                    for (int m = 0; m < 5; m++) {
                        if (bingoCard.getInt(l, m) == integer) {
                            bingoCard.setTruth(l, m);
                            if (bingoCard.checkWin()) {
                                bingoCard.setWinningScore(integer);
                                System.out.println(integer + ", ");
                                bingoCard.print();
                                return bingoCard.getScore();
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
        List<BingoCard> boards = new ArrayList<>();
        List<String> lines = new ArrayList<>();
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            if (line.length() > 1)
                lines.add(line);
        }
        int i = 0;
        for (int j = 0; j < lines.size() / 5; j++) {
            BingoCard bingoCard = new BingoCard();
            for (int k = 0; k < 5; k++, i++) {
                bingoCard.setGridRow(k, Arrays.stream(lines.get(i).trim().split("[ ]+")).mapToInt(Integer::parseInt).toArray());
            }
            boards.add(bingoCard);
        }
        BingoCard lastWinner = new BingoCard();
        int winners = 0;
        for (Integer integer : numbers) {
            for (BingoCard bingoCard : boards) {
                for (int l = 0; winners < boards.size() && l < 5; l++) {
                    for (int m = 0; winners < boards.size() && m < 5; m++) {
                        if (bingoCard.getInt(l, m) == integer) {
                            boolean wonAlready = bingoCard.checkWin();
                            bingoCard.setTruth(l, m);
                            if (!wonAlready && bingoCard.checkWin()) {
                                bingoCard.setWinningScore(integer);
                                lastWinner = bingoCard;
                                winners++;
                            }
                        }
                    }
                }
            }
        }
        lastWinner.print();
        System.out.println(lastWinner.getWinningScore() + ", " + lastWinner.sumBoard());
        return lastWinner.getScore();
    }

    static class BingoCard {
        private final int[][] grid;
        private final boolean[][] truthGrid;
        private int winningScore;

        public BingoCard() {
            grid = new int[5][5];
            truthGrid = new boolean[5][5];
        }

        private boolean checkWin() {
            for (int i = 0; i < 5; i++) {
                boolean horWin = true;
                boolean verWin = true;
                for (int j = 0; j < 5; j++) {
                    if (!truthGrid[i][j])
                        horWin = false;
                    if (!truthGrid[j][i])
                        verWin = false;
                }
                if (horWin)
                    return true;
                if (verWin)
                    return true;
            }
            return false;
        }

        private int sumBoard() {
            int total = 0;
            for (int i = 0; i < 5; i++) {
                for (int j = 0; j < 5; j++) {
                    if (!truthGrid[i][j]) {
                        total += grid[i][j];
                    }
                }
            }
            return total;
        }

        public int getInt(int i, int j) {
            return grid[i][j];
        }

        public int getScore() {
            return winningScore * sumBoard();
        }

        public int getWinningScore() {
            return winningScore;
        }

        public void setWinningScore(int score) {
            winningScore = score;
        }

        public void setGridRow(int i, int[] value) {
            grid[i] = value;
        }

        public void setTruth(int i, int j) {
            truthGrid[i][j] = true;
        }

        public void print() {
            for (int n = 0; n < 5; n++) {
                System.out.println(Arrays.toString(grid[n]));
            }
            for (int n = 0; n < 5; n++) {
                System.out.println(Arrays.toString(truthGrid[n]));
            }
        }
    }
}
