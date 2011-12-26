/**
 * World.java
 * Copyright (C) 2011  Kevin Wang and Shan Shi
 * 
 * This file is part of Minecarft.
 * 
 * Minecarft is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * Minecarft is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with Minecarft.  If not, see <http://www.gnu.org/licenses/>.
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
                        world[z][x][y] = r.nextInt(4);
                        world[z][x][y] = world[z][x][y] == 3 ? 1337 : world[z][x][y];
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
