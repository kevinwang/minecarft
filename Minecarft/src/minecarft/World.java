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

/**
 *
 * @author kevin
 */
public class World {
    public static final int Z = 128;
    public static final int X = 128;
    public static final int Y = 128;
    
    public static final int TYPE_AIR = 0;
    public static final int TYPE_STONE = 1;
    public static final int TYPE_DIRT = 2;
    public static final int TYPE_WATER = 10;
    public static final int TYPE_DIRT_GRASS = 1336; // Special type for rendering, not in array
    public static final int TYPE_BEDROCK = 1337;
    
    private int[][][] world;
    
    private static World instance;
    
    public static World getInstance() {
        if (instance == null) {
            instance = new World();
        }
        return instance;
    }
    
    private World() {
        LandGen peniscupcake = new LandGen(Z, X, Y);
        world = peniscupcake.getWorld();
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
