import java.util.ArrayList;
import java.util.List;
import java.util.*;

public class DFS {
    public static List<int[]> findShortestPath(int[][] verticalWalls, int[][] horizontalWalls, int rows, int cols, int startX, int startY, int endX, int endY) {
        List<int[]> shortestPath = new ArrayList<>();
        boolean[][] visited = new boolean[rows][cols];
        List<int[]> currentPath = new ArrayList<>();
        int[] minSteps = {Integer.MAX_VALUE};
        dfs(verticalWalls, horizontalWalls, rows, cols, startX, startY, endX, endY, visited, currentPath, shortestPath, minSteps, 0);

        return shortestPath;
    }

    private static void dfs(int[][] verticalWalls, int[][] horizontalWalls, int rows, int cols, int x, int y, int endX, int endY,
                                    boolean[][] visited, List<int[]> currentPath, List<int[]> shortestPath, int[] minSteps, int steps) {
        if (x < 0 || y < 0 || x >= rows || y >= cols || visited[x][y]) {
            return;
        }

        if (x == endX && y == endY) {
            if (steps < minSteps[0]) {
                minSteps[0] = steps;
                shortestPath.clear();
                shortestPath.addAll(currentPath);
                shortestPath.add(new int[]{x, y});
            }
            return;
        }

        visited[x][y] = true;
        currentPath.add(new int[]{x, y});

        // Ruch w prawo
        if (y < cols - 1 && verticalWalls[x][y] == 0) {
            dfs(verticalWalls, horizontalWalls, rows, cols, x, y + 1, endX, endY, visited, currentPath, shortestPath, minSteps, steps + 1);
        }
        // Ruch w lewo
        if (y > 0 && verticalWalls[x][y - 1] == 0) {
            dfs(verticalWalls, horizontalWalls, rows, cols, x, y - 1, endX, endY, visited, currentPath, shortestPath, minSteps, steps + 1);
        }
        // Ruch w dół
        if (x < rows - 1 && horizontalWalls[x][y] == 0) {
            dfs(verticalWalls, horizontalWalls, rows, cols, x + 1, y, endX, endY, visited, currentPath, shortestPath, minSteps, steps + 1);
        }
        // Ruch w górę
        if (x > 0 && horizontalWalls[x - 1][y] == 0) {
            dfs(verticalWalls, horizontalWalls, rows, cols, x - 1, y, endX, endY, visited, currentPath, shortestPath, minSteps, steps + 1);
        }

        visited[x][y] = false;
        currentPath.remove(currentPath.size() - 1);
    }

    public static void main(String[] args) {
        try {
            Scanner scanner = new Scanner(System.in);

            int rows = Integer.parseInt(scanner.nextLine());
            int cols = Integer.parseInt(scanner.nextLine());
            int startX = Integer.parseInt(scanner.nextLine());
            int startY = Integer.parseInt(scanner.nextLine());
            int endX = Integer.parseInt(scanner.nextLine());
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

            long startTime = System.nanoTime();
            List<int[]> path = findShortestPath(verticalWalls, horizontalWalls, rows, cols, startX, startY, endX, endY);
            long endTime = System.nanoTime();
            long durationMicroseconds = (endTime - startTime) / 1000;

            if (!path.isEmpty()){
                System.out.println(durationMicroseconds);

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
