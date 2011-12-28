/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package minecarft;

/**
 *
 * @author Kevin Wang
 */
public class LightingController {
    private static Block[][][] world = World.getInstance().getWorld();

    public static void calculateLighting() {
        System.out.print("Calculating lighting");

        int[][][] brightnesses = new int[World.Z][World.X][World.Y];
        for (int z = 0; z < World.Z; z++) {
            for (int x = 0; x < World.X; x++) {
                for (int y = 0; y < World.Y; y++) {
                    if (world[z][x][y].isLightEmitter()) {
                        brightnesses[z][x][y] = 9;
                    }
                    else {
                        brightnesses[z][x][y] = -1000;
                    }
                }
            }
        }

        for (int d = 0; d < 8; d++) {
            for (int z = 0; z < World.Z; z++) {
                for (int x = 0; x < World.X; x++) {
                    for (int y = 0; y < World.Y; y++) {
                        if (brightnesses[z][x][y] != -1000) {
                            for (int i = -1; i < 2; i++) {
                                for (int j = -1; j < 2; j++) {
                                    for (int k = -1; k < 2; k++) {
                                        if (!(z == 0 && x == 0 & y == 0)) {
                                            try {
                                                if (brightnesses[z][x][y] > brightnesses[z + i][x + j][y + k]) {
                                                    brightnesses[z + i][x + j][y + k] = brightnesses[z][x][y] - 1;
                                                }
                                            } catch (ArrayIndexOutOfBoundsException e) {
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }

            for (int z = 0; z < World.Z; z++) {
                for (int x = 0; x < World.X; x++) {
                    for (int y = 0; y < World.Y; y++) {
                        brightnesses[z][x][y] = brightnesses[z][x][y] < 1 ? 1 : brightnesses[z][x][y];
                        world[z][x][y].setBrightness(brightnesses[z][x][y]);
                    }
                }
            }
            System.out.print(".");
        }
        System.out.println();
    }
}
