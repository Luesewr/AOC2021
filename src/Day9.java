import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class Day9 {
    private static int width = 10;
    private static int height = 5;

    public static void main(String[] args) throws FileNotFoundException {
        File file = new File("testInput9.txt");
        Scanner scanner = new Scanner(file);
        int[][] grid = new int[height][width];
        int j = 0;
        while (scanner.hasNextLine()) {
            int[] line = new int[width];
            AtomicInteger i = new AtomicInteger(0);
            Arrays.stream(scanner.nextLine().split("")).toList().forEach(s -> {
                line[i.get()] = Integer.parseInt(s);
                i.getAndIncrement();
            });
            grid[j] = line;
            j++;
        }
        Scanner sc = new Scanner(System.in);
        System.out.println("""
                Please enter a mode:
                1: Part 1
                2: Part 2""");
        switch (sc.nextInt()) {
            case 1 -> System.out.println(part1(grid));
            case 2 -> System.out.println(part2(grid));
            default -> System.out.println("Invalid input, please rerun and fill in a valid index.");
        }
    }

    private static int part1(int[][] grid) {
        int total = 0;
        for (int i = 0, gridLength = grid.length; i < gridLength; i++) {
            int[] line = grid[i];
            for (int j = 0, lineLength = line.length; j < lineLength; j++) {
                int depth = line[j];
                System.out.println(i);
                if (i - 1 >= 0)
                    if (grid[i - 1][j] <= depth)
                        continue;
                if (i + 1 < height)
                    if (grid[i + 1][j] <= depth)
                        continue;
                if (j - 1 >= 0)
                    if (grid[i][j - 1] <= depth)
                        continue;
                if (j + 1 < width)
                    if (grid[i][j + 1] <= depth)
                        continue;
                System.out.printf("%d, %d\n", j, i);
                total += depth + 1;
            }
        }
        return total;
    }

    private static int part2(int[][] grid) {
        List<Basin> list = new ArrayList<>();
        for (int i = 0, gridLength = grid.length; i < gridLength; i++) {
            int[] line = grid[i];
            for (int j = 0, lineLength = line.length; j < lineLength; j++) {
                int depth = line[j];
                System.out.println(i);
                if (i - 1 >= 0)
                    if (grid[i - 1][j] <= depth)
                        continue;
                if (i + 1 < height)
                    if (grid[i + 1][j] <= depth)
                        continue;
                if (j - 1 >= 0)
                    if (grid[i][j - 1] <= depth)
                        continue;
                if (j + 1 < width)
                    if (grid[i][j + 1] <= depth)
                        continue;
                Basin basin = new Basin(new Point(j, i));
                list.add(basin);
            }
        }
        for (int i = 0, gridLength = grid.length; i < gridLength; i++) {
            int[] line = grid[i];
            for (int j = 0, lineLength = line.length; j < lineLength; j++) {
                if (grid[i][j] != 9) {
                    addToBasin(grid, new Point(j, i), new Point(j, i), list);
                }
            }
        }
        list.sort(Comparator.comparingInt(Basin::getSize));
        Collections.reverse(list);
        System.out.println(list);
        System.out.println(list.get(0).getLow());
        return list.get(0).getSize() * list.get(1).getSize() * list.get(2).getSize();
    }

    public static void addToBasin(int[][] grid, Point origin, Point point, List<Basin> list) {
        boolean end = true;
        if (point.getY() - 1 >= 0)
            if (grid[point.getY() - 1][point.getX()] < grid[point.getY()][point.getX()]) {
                addToBasin(grid, origin, new Point(point.getX(), point.getY() - 1), list);
                end = false;
            }
        if (point.getY() + 1 < height)
            if (grid[point.getY() + 1][point.getX()] < grid[point.getY()][point.getX()]) {
                addToBasin(grid, origin, new Point(point.getX(), point.getY() + 1), list);
                end = false;
            }
        if (point.getX() - 1 >= 0)
            if (grid[point.getY()][point.getX() - 1] < grid[point.getY()][point.getX()]) {
                addToBasin(grid, origin, new Point(point.getX() - 1, point.getY()), list);
                end = false;
            }
        if (point.getX() + 1 < width)
            if (grid[point.getY()][point.getX() + 1] < grid[point.getY()][point.getX()]) {
                addToBasin(grid, origin, new Point(point.getX() + 1, point.getY()), list);
                end = false;
            }
        if (end) {
            for (Basin basin : list) {
                if (basin.getLow().equals(point) && !basin.hasPoint(point)) {
                    basin.increaseSize();
                    basin.addPoint(origin);
                }
            }
        }
    }

    public static class Basin {
        private int size;
        private final Point low;
        private final List<Point> points;

        public Basin(Point low) {
            this.low = low;
            this.size = 0;
            points = new ArrayList<>();
        }

        public Point getLow() {
            return this.low;
        }

        @Override
        public String toString() {
            return "Basin{" +
                    "size=" + size +
                    ", low=" + low +
                    ", points=" + points +
                    '}';
        }

        public void increaseSize() {
            this.size++;
        }

        public int getSize() {
            return this.size;
        }

        public void addPoint(Point point) {
            points.add(point);
        }

        public boolean hasPoint(Point point) {
            for (Point p : points) {
                if (point.equals(p)) {
                    return true;
                }
            }
            return false;
        }
    }

    public static class Point {
        private final int x;
        private final int y;

        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public String toString() {
            return "{" + x + ", " + y + "}";
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Point point = (Point) o;
            return x == point.x && y == point.y;
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }
    }
}
