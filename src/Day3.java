import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class Day3 {
    public static void main(String[] args) throws FileNotFoundException {
        File file = new File("input3.txt");
        Scanner sc = new Scanner(System.in);
        System.out.println("""
                Please enter a mode:
                1: Part 1
                2: Part 2""");
        switch (sc.nextInt()) {
            case 1 -> System.out.println(part1(file));
            case 2 -> System.out.println(part2(file));
            default -> System.out.println("Invalid input, please rerun and fill in a valid index.");
        }
    }

    private static int part1(File file) throws FileNotFoundException {
        int len = new Scanner(file).nextLine().length();
        Scanner scanner = new Scanner(file);
        int[] zeros = new int[len];
        int[] ones = new int[len];
        while (scanner.hasNextLine()) {
            char[] elements = scanner.nextLine().toCharArray();
            for (int i = 0; i < elements.length; i++) {
                if (elements[i] == '0') {
                    zeros[i]++;
                } else {
                    ones[i]++;
                }
            }
        }
        StringBuilder gamma = new StringBuilder();
        StringBuilder epsilon = new StringBuilder();
        for (int i = 0; i < len; i++) {
            if (zeros[i] > ones[i]) {
                gamma.append('0');
                epsilon.append('1');
            } else {
                gamma.append('1');
                epsilon.append('0');
            }
        }
        return Integer.parseInt(gamma.toString(), 2) * Integer.parseInt(epsilon.toString(), 2);
    }

    private static int part2(File file) throws FileNotFoundException {
        Scanner scanner = new Scanner(file);
        List<String> list = new ArrayList<>();
        while (scanner.hasNextLine()) {
            list.add(scanner.nextLine());
        }
        List<String> listCopy = new ArrayList<>(list);
        int j = 0;
        while(list.size() > 1) {
            AtomicInteger ones = new AtomicInteger();
            AtomicInteger zeros = new AtomicInteger();
            int finalJ1 = j;
            list.forEach(s -> {
                    if (s.charAt(finalJ1) == '0') {
                        zeros.getAndIncrement();
                    } else {
                        ones.getAndIncrement();
                    }
            });
            int finalJ = j;
            if(list.size() > 1)
            list = list.stream().filter(s -> s.charAt(finalJ) == ((zeros.get() > ones.get())?'0':'1')).collect(Collectors.toList());
            ones.set(0);
            zeros.set(0);
            listCopy.forEach(s -> {
                if (s.charAt(finalJ1) == '0') {
                    zeros.getAndIncrement();
                } else {
                    ones.getAndIncrement();
                }
            });
            if(listCopy.size()>1)
            listCopy = listCopy.stream().filter(s -> s.charAt(finalJ) == ((zeros.get() <= ones.get())?'0':'1')).collect(Collectors.toList());
            j++;
        }
        return Integer.parseInt(list.get(0), 2) * Integer.parseInt(listCopy.get(0), 2);
    }
}
