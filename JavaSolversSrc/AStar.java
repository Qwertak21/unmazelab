import java.util.*;

public class AStar {
    public static List<int[]> findPathAStar(int[][] verticalWalls, int[][] horizontalWalls, int rows, int cols, int startX, int startY, int endX, int endY) {
        PriorityQueue<Node> openSet = new PriorityQueue<>(Comparator.comparingInt(node -> node.priority));
        openSet.add(new Node(startX, startY, 0));

        Map<String, Integer> costs = new HashMap<>();
        costs.put(hash(startX, startY), 0);

        Map<String, int[]> predecessors = new HashMap<>();
        predecessors.put(hash(startX, startY), null);

        while (!openSet.isEmpty()) {
            Node current = openSet.poll();

            // Jeśli osiągnięto cel
            if (current.x == endX && current.y == endY) {
                List<int[]> path = new ArrayList<>();
                int[] position = new int[]{current.x, current.y};

                while (position != null) {
                    path.add(position);
                    position = predecessors.get(hash(position[0], position[1]));
                }
                Collections.reverse(path);
                return path;
            }

            // Przegląd sąsiadów
            for (int[] direction : new int[][]{{0, 1}, {1, 0}, {0, -1}, {-1, 0}}) {
                int neighborX = current.x + direction[0];
                int neighborY = current.y + direction[1];

                // Sprawdzenie granic i przeszkód
                if (neighborX < 0 || neighborY < 0 || neighborX >= rows || neighborY >= cols) {
                    continue;
                }

                // Sprawdzenie ścian
                if (direction[0] == 0 && direction[1] == 1 && neighborY > 0 && verticalWalls[current.x][current.y] == 1) {
                    continue; // Ściana na prawo
                }
                if (direction[0] == 0 && direction[1] == -1 && current.y > 0 && verticalWalls[current.x][current.y - 1] == 1) {
                    continue; // Ściana na lewo
                }
                if (direction[0] == 1 && direction[1] == 0 && neighborX > 0 && horizontalWalls[current.x][current.y] == 1) {
                    continue; // Ściana w dół
                }
                if (direction[0] == -1 && direction[1] == 0 && current.x > 0 && horizontalWalls[current.x - 1][current.y] == 1) {
                    continue; // Ściana w górę
                }

                int newCost = costs.get(hash(current.x, current.y)) + 1;

                if (!costs.containsKey(hash(neighborX, neighborY)) || newCost < costs.get(hash(neighborX, neighborY))) {
                    costs.put(hash(neighborX, neighborY), newCost);
                    int heuristic = Math.abs(neighborX - endX) + Math.abs(neighborY - endY);
                    int priority = newCost + heuristic;

                    openSet.add(new Node(neighborX, neighborY, priority));
                    predecessors.put(hash(neighborX, neighborY), new int[]{current.x, current.y});
                }
            }
        }

        return new ArrayList<>();
    }

    private static String hash(int x, int y) {
        return x + "," + y;
    }

    private static class Node {
        int x, y, priority;

        Node(int x, int y, int priority) {
            this.x = x;
            this.y = y;
            this.priority = priority;
        }
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

            long startTime = System.nanoTime();
            List<int[]> path = findPathAStar(verticalWalls, horizontalWalls, rows, cols, startX, startY, endX, endY);
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