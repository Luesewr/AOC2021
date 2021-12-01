import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Day1 {
    public static void main(String[] args) throws FileNotFoundException {
        File file = new File("input1.txt");
        Scanner scanner = new Scanner(file);
        //System.out.println(standardInput(scanner));
        System.out.println(slidingWindow(scanner));
    }

    public static int standardInput(Scanner scanner) {
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

    public static int slidingWindow(Scanner scanner) {
        int prev = 8500;
        int prevPrev = 8500;
        int prevPrevPrev = 8500;
        int count = 0;
        while (scanner.hasNextLine()) {
            int current = scanner.nextInt();
            if (current + prev + prevPrev > prev + prevPrev + prevPrevPrev) {
                count++;
            }
            System.out.println(prev + " " + prevPrev + " " + prevPrevPrev);
            prevPrevPrev = prevPrev;
            prevPrev = prev;
            prev = current;
        }
        return count;
    }
}
