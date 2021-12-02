import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Day2 {
    public static void main(String[] args) throws FileNotFoundException {
        File file = new File("input2.txt");
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
        int depth = 0;
        int hor = 0;
        while (scanner.hasNextLine()) {
            String[] line = scanner.nextLine().split(" ");
            switch (line[0]) {
                case "forward" -> hor += Integer.parseInt(line[1]);
                case "down" -> depth += Integer.parseInt(line[1]);
                case "up" -> depth -= Integer.parseInt(line[1]);
            }
        }
        return depth * hor;
    }

    private static int part2(Scanner scanner) {
        int depth = 0;
        int aim = 0;
        int hor = 0;
        while (scanner.hasNextLine()) {
            String[] line = scanner.nextLine().split(" ");
            switch (line[0]) {
                case "forward" -> {hor += Integer.parseInt(line[1]); depth += aim * Integer.parseInt(line[1]);}
                case "down" -> aim += Integer.parseInt(line[1]);
                case "up" -> aim -= Integer.parseInt(line[1]);
            }
        }
        return depth * hor;
    }
}
