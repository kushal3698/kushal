package com.chronodharma.level;

import java.util.Random;

/**
 * Generates levels using a 2D array grid.
 * 0 = Path
 * 1 = Wall/Tree
 * 2 = Enemy
 * 3 = Objective
 */
public class LevelGenerator {
    public static final int TILE_PATH = 0;
    public static final int TILE_WALL = 1;
    public static final int TILE_ENEMY = 2;
    public static final int TILE_OBJECTIVE = 3;

    private int width;
    private int height;
    private int[][] map;
    private Random random;

    public LevelGenerator(int width, int height) {
        this.width = width;
        this.height = height;
        this.map = new int[width][height];
        this.random = new Random();
    }

    /**
     * Generates a forest-like map for the Ramayana Era.
     */
    public int[][] generateForestLevel() {
        // Initialize with walls (dense forest)
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                map[x][y] = TILE_WALL;
            }
        }

        // Carve paths (Simple Random Walk or Cellular Automata could be used,
        // but for simplicity we'll carve a winding path from start to end)
        int currentX = 1;
        int currentY = 1;
        map[currentX][currentY] = TILE_PATH; // Start point

        // Simple path carving to ensure connectivity
        while (currentX < width - 2 || currentY < height - 2) {
            if (random.nextBoolean() && currentX < width - 2) {
                currentX++;
            } else if (currentY < height - 2) {
                currentY++;
            }
            map[currentX][currentY] = TILE_PATH;

            // Add some random width to the path
            if (currentX + 1 < width - 1)
                map[currentX + 1][currentY] = TILE_PATH;
            if (currentY + 1 < height - 1)
                map[currentX][currentY + 1] = TILE_PATH;
        }

        // Place Objective at the end
        map[width - 2][height - 2] = TILE_OBJECTIVE;

        // Place Enemies randomly on paths
        placeEnemies(5);

        return map;
    }

    private void placeEnemies(int count) {
        int placed = 0;
        while (placed < count) {
            int x = random.nextInt(width);
            int y = random.nextInt(height);

            if (map[x][y] == TILE_PATH) {
                map[x][y] = TILE_ENEMY;
                placed++;
            }
        }
    }

    public void printMapToConsole() {
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                System.out.print(map[x][y] + " ");
            }
            System.out.println();
        }
    }
}
