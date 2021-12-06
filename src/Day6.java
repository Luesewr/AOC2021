import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Day6 {
    public static void main(String[] args) throws FileNotFoundException {
        File file = new File("input6.txt");
        Scanner scanner = new Scanner(file);
        Scanner sc = new Scanner(System.in);
        System.out.println("""
                Please enter a mode:
                1: Part 1
                2: Part 2""");
        int day = 0;
        switch (sc.nextInt()) {
            case 1 -> day = 80;
            case 2 -> day = 256;
            default -> System.out.println("Invalid input, please rerun and fill in a valid index.");
        }
        String[] elements = scanner.nextLine().split(",");
        long[] days = new long[9];
        for (String s : elements) {
            days[Integer.parseInt(s)]++;
        }
        for (int i = 0; i < day; i++) {
            long zero = days[0];
            //noinspection SuspiciousSystemArraycopy
            System.arraycopy(days, 1, days, 0, days.length - 1);
            days[6] += zero;
            days[8] = zero;
        }
        long total = 0;
        for (long l : days) {
            total += l;
        }
        System.out.println(total);
    }
}
