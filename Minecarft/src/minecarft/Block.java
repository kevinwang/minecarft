/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package minecarft;

/**
 *
 * @author Kevin Wang
 */
public class Block {
    public static final int TYPE_AIR = 0;
    public static final int TYPE_STONE = 1;
    public static final int TYPE_DIRT = 2;
    public static final int TYPE_SAND = 3;
    public static final int TYPE_WOOD = 4;
    public static final int TYPE_LEAVES = 5;
    public static final int TYPE_WATER = 10;
    public static final int TYPE_LAVA = 11;
    public static final int TYPE_DIRT_GRASS = 1336; // Special type for rendering, not in array
    public static final int TYPE_BEDROCK = 1337;

    public static final boolean DOES_EMIT_LIGHT = true;
    public static final boolean DOES_NOT_EMIT_LIGHT = false;

    private int type;
    private int brightness;
    private boolean emitsLight;

    public Block(int type, boolean emitsLight) {
        this.type = type;
        this.emitsLight = emitsLight;
        if (type == TYPE_LAVA) {
            this.emitsLight = DOES_EMIT_LIGHT;
        }
    }

    public void setBrightness(int brightness) {
        this.brightness = brightness;
    }

    public int getType() {
        return type;
    }

    public int getBrightness() {
        return brightness;
    }

    public boolean isLightEmitter() {
        return emitsLight;
    }
}
