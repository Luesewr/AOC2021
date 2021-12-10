import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Day10 {
    private static final HashMap<Character, Character> normalOpposites = new HashMap<>() {{
        put('}', '{');
        put(']', '[');
        put('>', '<');
        put(')', '(');
    }};
    private static final HashMap<Character, Integer> values = new HashMap<>() {{
        put(')', 3);
        put(']', 57);
        put('}', 1197);
        put('>', 25137);
    }};
    private static final HashMap<Character, Integer> autocompleteValues = new HashMap<>() {{
        put('(', 1);
        put('[', 2);
        put('{', 3);
        put('<', 4);
    }};

    public static void main(String[] args) throws FileNotFoundException {
        File file = new File("input10.txt");
        Scanner scanner = new Scanner(file);
        List<String> list = new ArrayList<>();
        while (scanner.hasNextLine()) {
            list.add(scanner.nextLine());
        }
        Scanner sc = new Scanner(System.in);
        System.out.println("""
                Please enter a mode:
                1: Part 1
                2: Part 2""");
        switch (sc.nextInt()) {
            case 1 -> System.out.println(part1(list));
            case 2 -> System.out.println(part2(list));
            default -> System.out.println("Invalid input, please rerun and fill in a valid index.");
        }
    }

    public static int part1(List<String> list) {
        int total = 0;
        for (String line : list) {
            StringBuilder check = new StringBuilder();
            for (char s : line.toCharArray()) {
                if (normalOpposites.containsKey(s)) {
                    if (check.lastIndexOf(Character.toString(normalOpposites.get(s))) != check.length() - 1) {
                        total += values.get(s);
                        break;
                    } else {
                        check.deleteCharAt(check.length() - 1);
                    }
                } else {
                    check.append(s);
                }
            }
        }
        return total;
    }

    private static long part2(List<String> list) {
        List<Long> scores = new ArrayList<>();
        for (String line : list) {
            long total = 0;
            StringBuilder check = new StringBuilder();
            boolean broken = false;
            for (char s : line.toCharArray()) {
                if (normalOpposites.containsKey(s)) {
                    if (check.lastIndexOf(Character.toString(normalOpposites.get(s))) != check.length() - 1) {
                        broken = true;
                        break;
                    } else {
                        check.deleteCharAt(check.length() - 1);
                    }
                } else {
                    check.append(s);
                }
            }
            if (!broken) {
                for (int i = check.length() - 1; i >= 0; i--) {
                    total *= 5;
                    total += autocompleteValues.get(check.charAt(i));
                }
                scores.add(total);
            }
        }
        Collections.sort(scores);
        return scores.get((scores.size() - 1) / 2);
    }
}
