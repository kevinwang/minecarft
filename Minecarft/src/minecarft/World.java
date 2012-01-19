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
    
    private Block[][][] world;
    
    private static World instance;

    public static World getInstance() {
        if (instance == null) {
            instance = new World();
        }
        return instance;
    }

    public static World getInstance(String savefile) {
        if (instance == null) {
            instance = new World(savefile);
        }
        return instance;
    }
    
    private World() {
        LandGen peniscupcake = new LandGen(Z, X, Y);
        world = peniscupcake.getWorld();
    }

    private World(String savefile) {
        world = FileIO.loadMap(savefile);
    }
    
    public Block[][][] getWorld() {
        return world;
    }
    
    public boolean isVisible(int x, int y, int z) {
        if (x - 1 >= 0 && world[z][x - 1][y].getType() == Block.TYPE_AIR) {
            return true;
        }
        if (x + 1 < X && world[z][x + 1][y].getType() == Block.TYPE_AIR) {
            return true;
        }
        if (y - 1 >= 0 && world[z][x][y - 1].getType() == Block.TYPE_AIR) {
            return true;
        }
        if (y + 1 < Y && world[z][x][y + 1].getType() == Block.TYPE_AIR) {
            return true;
        }
        if (z - 1 >= 0 && world[z - 1][x][y].getType() == Block.TYPE_AIR) {
            return true;
        }
        if (z + 1 < Z && world[z + 1][x][y].getType() == Block.TYPE_AIR) {
            return true;
        }
        
        if (x - 1 >= 0 && world[z][x - 1][y].getType() == Block.TYPE_LEAVES) {
            return true;
        }
        if (x + 1 < X && world[z][x + 1][y].getType() == Block.TYPE_LEAVES) {
            return true;
        }
        if (y - 1 >= 0 && world[z][x][y - 1].getType() == Block.TYPE_LEAVES) {
            return true;
        }
        if (y + 1 < Y && world[z][x][y + 1].getType() == Block.TYPE_LEAVES) {
            return true;
        }
        if (z - 1 >= 0 && world[z - 1][x][y].getType() == Block.TYPE_LEAVES) {
            return true;
        }
        if (z + 1 < Z && world[z + 1][x][y].getType() == Block.TYPE_LEAVES) {
            return true;
        }
        
        return false;
    }
}
