import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.OptionalInt;
import java.util.Scanner;
import java.lang.Math;

public class Day7 {
    public static void main(String[] args) throws FileNotFoundException {
        File file = new File("input7.txt");
        Scanner scanner = new Scanner(file);
        int[] pos = Arrays.stream(scanner.nextLine().split(",")).mapToInt(Integer::parseInt).toArray();
        System.out.println(Arrays.toString(pos));
        Scanner sc = new Scanner(System.in);
        System.out.println("""
                Please enter a mode:
                1: Part 1
                2: Part 2""");
        switch (sc.nextInt()) {
            case 1 -> System.out.println(part1(pos));
            case 2 -> System.out.println(part2(pos));
            default -> System.out.println("Invalid input, please rerun and fill in a valid index.");
        }
    }

    public static int part1(int[] pos) {
        OptionalInt max = Arrays.stream(pos).max();
        if (max.isEmpty()) {
            return 0;
        }
        int minFuel = Integer.MAX_VALUE;
        for (int i = 0; i <= max.getAsInt(); i++) {
            int fuel = 0;
            for (int j : pos) {
                fuel += Math.abs(j - i);
            }
            minFuel = Math.min(minFuel, fuel);
        }
        return minFuel;
    }

    public static int part2(int[] pos) {
        OptionalInt max = Arrays.stream(pos).max();
        if (max.isEmpty()) {
            return 0;
        }
        int minFuel = Integer.MAX_VALUE;
        for (int i = 0; i <= max.getAsInt(); i++) {
            int fuel = 0;
            for (int j : pos) {
                int dif = Math.abs(j - i);
                fuel += ((dif + 1) * (dif)) / 2;
            }
            minFuel = Math.min(minFuel, fuel);
        }
        return minFuel;
    }
}
