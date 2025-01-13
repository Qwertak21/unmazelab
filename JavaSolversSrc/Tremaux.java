import java.io.FileWriter;
import java.util.*;

public class Tremaux {public static List<int[]> findPathTremaux(int[][] verticalWalls, int[][] horizontalWalls, int rows, int cols, int startX, int startY, int endX, int endY) {
    Stack<int[]> stack = new Stack<>();
    Map<String, Integer> visitedCount = new HashMap<>();
    int[] position = new int[]{startX, startY};

    while (!(position[0] == endX && position[1] == endY)) {
        String posKey = hash(position[0], position[1]);
        visitedCount.put(posKey, visitedCount.getOrDefault(posKey, 0) + 1);

        List<int[]> neighbors = findNeighbors(position, verticalWalls, horizontalWalls, rows, cols);

        // Szukanie nowych sąsiadów (nieodwiedzonych)
        List<int[]> newNeighbors = new ArrayList<>();
        for (int[] neighbor : neighbors) {
            if (visitedCount.getOrDefault(hash(neighbor[0], neighbor[1]), 0) == 0) {
                newNeighbors.add(neighbor);
            }
        }

        if (!newNeighbors.isEmpty()) {
            stack.push(position);
            position = newNeighbors.get(0); // Wybierz pierwszego nowego sąsiada
        } else {
            if (!stack.isEmpty()) {
                position = stack.pop(); // Cofanie się
            } else {
                return new ArrayList<>(); // Brak ścieżki
            }
        }
    }

    // Tworzenie ścieżki
    List<int[]> path = new ArrayList<>(stack);
    path.add(position);
    return path;
}

    private static List<int[]> findNeighbors(int[] position, int[][] verticalWalls, int[][] horizontalWalls, int rows, int cols) {
        int[][] directions = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}};
        List<int[]> neighbors = new ArrayList<>();
        int x = position[0];
        int y = position[1];

        for (int[] direction : directions) {
            int nx = x + direction[0];
            int ny = y + direction[1];

            if (nx >= 0 && ny >= 0 && nx < rows && ny < cols) {
                // Sprawdzenie ścian
                if (direction[0] == 0 && direction[1] == 1 && ny > 0 && verticalWalls[x][y] == 1) {
                    continue; // Ściana na prawo
                }
                if (direction[0] == 0 && direction[1] == -1 && y > 0 && verticalWalls[x][y - 1] == 1) {
                    continue; // Ściana na lewo
                }
                if (direction[0] == 1 && direction[1] == 0 && nx > 0 && horizontalWalls[x][y] == 1) {
                    continue; // Ściana w dół
                }
                if (direction[0] == -1 && direction[1] == 0 && x > 0 && horizontalWalls[x - 1][y] == 1) {
                    continue; // Ściana w górę
                }

                neighbors.add(new int[]{nx, ny});
            }
        }

        return neighbors;
    }

    private static String hash(int x, int y) {
        return x + "," + y;
    }

    public static void main(String[] args) {
        try {
            Scanner scanner = new Scanner(System.in);

            int rows = Integer.parseInt(scanner.nextLine()); // Ilość wierszy labiryntu
            int cols = Integer.parseInt(scanner.nextLine()); // Ilość kolumn labiryntu
            int startX = Integer.parseInt(scanner.nextLine()); // Wiersz startowy
            int startY = Integer.parseInt(scanner.nextLine()); // Kolumna startowa
            int endX = Integer.parseInt(scanner.nextLine());   // Wiersz końcowy
            int endY = Integer.parseInt(scanner.nextLine());

            int[][] verticalWalls = new int[rows][cols-1];
            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < cols-1; j++) {
                    verticalWalls[i][j] = Integer.parseInt(scanner.nextLine());
                }
            }

            int[][] horizontalWalls = new int[rows - 1][cols];
            for (int i = 0; i < rows-1; i++) {
                for (int j = 0; j < cols; j++) {
                    horizontalWalls[i][j] = Integer.parseInt(scanner.nextLine());
                }
            }
            scanner.close();

            List<int[]> path = new ArrayList<>();
            List<Long> Time = new ArrayList<>();
            long lastDurationMicroseconds = 0;

            for (int i = 0; i < 20; i++) {
                long startTime = System.nanoTime();
                path = findPathTremaux(verticalWalls, horizontalWalls, rows, cols, startX, startY, endX, endY);
                long endTime = System.nanoTime();
                lastDurationMicroseconds = (endTime - startTime) / 1000;
                Time.add(lastDurationMicroseconds);
            }

            long avgTime = 0;

            for (int i = 10; i < 20; i++) {
                avgTime += Time.get(i);
            }
            avgTime /= 10;

            if (!path.isEmpty()){
                System.out.println(avgTime);

                for (int[] cell : path) {
                    System.out.println(cell[0]);
                    System.out.println(cell[1]);
                }
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
