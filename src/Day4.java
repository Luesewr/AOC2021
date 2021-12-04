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
        List<Integer> numbers = new ArrayList<>();
        Arrays.stream(scanner.nextLine().split(",")).toList().forEach(s -> numbers.add(Integer.parseInt(s)));
        List<String> lines = new ArrayList<>();
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            if (line.length() > 1)
                lines.add(line);
        }
        List<BingoCard> boards = new ArrayList<>();
        int i = 0;
        for (int j = 0; j < lines.size() / 5; j++) {
            BingoCard bingoCard = new BingoCard();
            for (int k = 0; k < 5; k++, i++) {
                bingoCard.setGridRow(k, Arrays.stream(lines.get(i).trim().split("[ ]+")).mapToInt(Integer::parseInt).toArray());
            }
            boards.add(bingoCard);
        }
        System.out.println("""
                Please enter a mode:
                1: Part 1
                2: Part 2""");
        switch (sc.nextInt()) {
            case 1 -> System.out.println(part1(numbers, boards));
            case 2 -> System.out.println(part2(numbers, boards));
            default -> System.out.println("Invalid input, please rerun and fill in a valid index.");
        }
    }

    private static int part1(List<Integer> numbers, List<BingoCard> boards) {

        for (Integer integer : numbers) {
            for (BingoCard bingoCard : boards) {
                if (bingoCard.checkNumber(integer)) {
                    bingoCard.setWinningScore(integer);
                    System.out.println(integer + ", ");
                    bingoCard.print();
                    return bingoCard.getScore();
                }
            }
        }
        return 0;
    }

    private static int part2(List<Integer> numbers, List<BingoCard> boards) {
        BingoCard lastWinner = new BingoCard();
        int winners = 0;
        for (Integer integer : numbers) {
            for (BingoCard bingoCard : boards) {
                boolean wonAlready = bingoCard.checkWin();
                if (!wonAlready && bingoCard.checkNumber(integer)) {
                    bingoCard.setWinningScore(integer);
                    lastWinner = bingoCard;
                    winners++;
                }
                if (winners == boards.size()) {
                    break;
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

        public boolean checkNumber(int integer) {
            for (int l = 0; l < 5; l++) {
                for (int m = 0; m < 5; m++) {
                    if (grid[l][m] == integer) {
                        setTruth(l, m);
                    }
                }
            }
            return checkWin();
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
