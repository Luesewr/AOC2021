import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Day1 {
    public static void main(String[] args) throws FileNotFoundException {
        File file = new File("input1.txt");
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

    public static int part1(Scanner scanner) {
        int prev = Integer.MAX_VALUE;
        int count = 0;
        while (scanner.hasNextLine()) {
            int current = scanner.nextInt();
            if (current > prev) {
                count++;
            }
            prev = current;
        }
        return count;
    }

    public static int part2(Scanner scanner) {
        int prev = 8500;
        int prevPrev = 8500;
        int prevPrevPrev = 8500;
        int count = 0;
        while (scanner.hasNextLine()) {
            int current = scanner.nextInt();
            if (current + prev + prevPrev > prev + prevPrev + prevPrevPrev) {
                count++;
            }
            //System.out.println(prev + " " + prevPrev + " " + prevPrevPrev);
            prevPrevPrev = prevPrev;
            prevPrev = prev;
            prev = current;
        }
        return count;
    }
}
