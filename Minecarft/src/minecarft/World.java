/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package minecarft;

import java.util.Random;

/**
 *
 * @author kevin
 */
public class World {
    public static final int X = 128;
    public static final int Y = 128;
    public static final int Z = 128;
    
    public static final int TYPE_AIR = 0;
    public static final int TYPE_STONE = 1;
    public static final int TYPE_DIRT = 2;
    public static final int TYPE_DIRT_GRASS = 1336; // Special type for rendering, not in array
    public static final int TYPE_BEDROCK = 1337;
    
    public int[][][] world;
    
    public World() {
        world = new int[Z][X][Y];
        // Generate terrain and fill world array
        Random r = new Random();
        for (int z = 0; z < world.length; z++) {
            for (int x = 0; x < world[0].length; x++) {
                for (int y = 0; y < world[0][0].length; y++) {
                    if (r.nextInt(1000) == 0) {
                        world[z][x][y] = r.nextInt(3);
                    }
                }
            }
        }
    }
    
    public int[][][] getWorld() {
        return world;
    }
    
    public boolean isVisible(int x, int y, int z) {
        if (x - 1 >= 0 && world[z][x - 1][y] == 0) {
            return true;
        }
        if (x + 1 < X && world[z][x + 1][y] == 0) {
            return true;
        }
        if (y - 1 >= 0 && world[z][x][y - 1] == 0) {
            return true;
        }
        if (y + 1 < Y && world[z][x][y + 1] == 0) {
            return true;
        }
        if (z - 1 >= 0 && world[z - 1][x][y] == 0) {
            return true;
        }
        if (z + 1 < Z && world[z + 1][x][y] == 0) {
            return true;
        }
        return false;
    }
}
