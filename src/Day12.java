import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class Day12 {
    private static Map<String, List<String>> map;

    public static void main(String[] args) throws FileNotFoundException {
        File file = new File("input12.txt");
        Scanner scanner = new Scanner(file);
        map = new HashMap<>();
        while (scanner.hasNextLine()) {
            String[] elements = scanner.nextLine().split("-");
            String start = elements[0];
            String end = elements[1];
            if (map.containsKey(start)) {
                map.get(start).add(end);
            } else {
                map.put(start, new ArrayList<>(List.of(end)));
            }
            if (map.containsKey(end)) {
                map.get(end).add(start);
            } else {
                map.put(end, new ArrayList<>(List.of(start)));
            }
        }
        System.out.println(map);
        List<String> lowerVisitedStart = new ArrayList<>(List.of("start"));
        Scanner sc = new Scanner(System.in);
        System.out.println("""
                Please enter a mode:
                1: Part 1
                2: Part 2""");
        switch (sc.nextInt()) {
            case 1 -> System.out.println(part1("start", lowerVisitedStart));
            case 2 -> System.out.println(part2("start", new ArrayList<>(), new ArrayList<>(), false));
            default -> System.out.println("Invalid input, please rerun and fill in a valid index.");
        }
    }

    private static int part1(String key, List<String> lowerVisited) {
        if (key.equals("end")) {
            return 1;
        }
        int total = 0;
        for (String s : map.get(key)) {
            if (!lowerVisited.contains(s)) {
                List<String> newLowerVisited = new ArrayList<>(lowerVisited);
                if (Character.isLowerCase(s.charAt(0))) {
                    newLowerVisited.add(s);
                }
                int result = part1(s, newLowerVisited);
                total += result;
            }
        }
        return total;
    }

    private static int part2(String key, List<String> visited,List<String> lowerVisited, boolean twoSmall) {
        if (key.equals("end")) {
            //System.out.println("Visited:" + visited);
            return 1;
        }
        if (key.equals("start")) {
            if (lowerVisited.contains("start"))
                return 0;
            lowerVisited.add("start");
            visited.add("start");
        }
        int total = 0;
        for (String s : map.get(key)) {
            AtomicInteger count = new AtomicInteger(0);
            if (Character.isLowerCase(s.charAt(0))) {
                lowerVisited.forEach(s1 -> {
                    if (s1.equals(s)) {count.getAndIncrement();
                        }
                });
            }
            if (!twoSmall || count.get() < 1) {
                List<String> newLowerVisited = new ArrayList<>(lowerVisited);
                List<String> newVisited = new ArrayList<>(visited);
                if (Character.isLowerCase(s.charAt(0))) {
                    newLowerVisited.add(s);
                }
                newVisited.add(s);
                boolean newTwoSmall = twoSmall;
                if (count.get() == 1) {
                    newTwoSmall = true;
                }
                int result = part2(s, newVisited, newLowerVisited, newTwoSmall);
                total += result;
            }
        }
        return total;
    }
}
