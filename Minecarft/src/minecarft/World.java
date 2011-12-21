/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package minecarft;

/**
 *
 * @author kevin
 */
public class World {
    public static final int X = 16;
    public static final int Y = 16;
    public static final int Z = 16;
    public int[][][] world;
    
    public World() {
        world = new int[Z][X][Y];
        // Generate terrain and fill world array
    }
    
    public int[][][] getWorld() {
        return world;
    }
    
    public boolean isVisible(int x, int y, int z) {
        return true;
    }
}
