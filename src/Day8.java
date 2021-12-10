import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class Day8 {
    public static void main(String[] args) throws FileNotFoundException {
        File file = new File("input8.txt");
        Scanner scanner = new Scanner(file);
        List<String> elements = new ArrayList<>();
        List<String> digits = new ArrayList<>();
        while (scanner.hasNextLine()) {
            String[] line = scanner.nextLine().split(" \\| ");
            elements.add(line[0]);
            digits.add(line[1]);
        }
        Scanner sc = new Scanner(System.in);
        System.out.println("""
                Please enter a mode:
                1: Part 1
                2: Part 2""");
        switch (sc.nextInt()) {
            case 1 -> System.out.println(part1(digits));
            case 2 -> System.out.println(part2(elements, digits));
            default -> System.out.println("Invalid input, please rerun and fill in a valid index.");
        }
    }

    private static int part1(List<String> digits) {
        int total = 0;
        for (String digit : digits) {
            for (String s : digit.split(" ")) {
                if (s.length() == 2 || s.length() == 3 || s.length() == 4 || s.length() == 7) {
                    total++;
                }
            }
        }
        return total;
    }

    private static int part2(List<String> elements, List<String> digits) {
        int total = 0;
        int count = 0;
        for (String digit : elements) {
            String[] content = new String[10];
            String[] split = digit.split(" ");
            Arrays.sort(split, Comparator.comparingInt(String::length));
            for (String s : split) {
                switch (s.length()) {
                    case 2 -> content[1] = s(s);
                    case 3 -> content[7] = s(s);
                    case 4 -> content[4] = s(s);
                    case 5 -> {
                        boolean three = true;
                        for (char c : content[1].toCharArray()) {
                            if (!s.contains(Character.toString(c))) {
                                three = false;
                                break;
                            }
                        }
                        if (three) {
                            content[3] = s(s);
                            break;
                        }
                        int overlap = 0;
                        for (char c : content[4].toCharArray()) {
                            if (s.contains(Character.toString(c))) {
                                overlap++;
                            }
                        }
                        if (overlap == 3) {
                            content[5] = s(s);
                        } else {
                            content[2] = s(s);
                        }
                    }
                    case 6 -> {
                        int overlap1 = 0;
                        for (char c : content[3].toCharArray()) {
                            if (s.contains(Character.toString(c))) {
                                overlap1++;
                            }
                        }
                        int overlap2 = 0;
                        for (char c : content[1].toCharArray()) {
                            if (s.contains(Character.toString(c))) {
                                overlap2++;
                            }
                        }
                        if (overlap1 == 5) {
                            content[9] = s(s);
                        } else if (overlap2 == 1){
                            content[6] = s(s);
                        } else {
                            content[0] = s(s);
                        }
                    }
                    case 7 -> content[8] = s(s);
                }
            }
            String number = "";
            for (String d : digits.get(count).split(" ")) {
                for (int i = 0; i < content.length; i++) {
                    if (s(d).equals(content[i])) {
                        number += String.valueOf(i);
                        break;
                    }
                }
            }
            total += Integer.parseInt(number);
            count++;
        }
        return total;
    }

    public static String s(String string) {
        char[] elements = string.toCharArray();
        Arrays.sort(elements);
        return String.valueOf(elements);
    }
}
