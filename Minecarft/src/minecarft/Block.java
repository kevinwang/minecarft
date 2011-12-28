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
    public static final boolean DOES_EMIT_LIGHT = true;
    public static final boolean DOES_NOT_EMIT_LIGHT = false;

    private int type;
    private int brightness;
    private boolean emitsLight;

    public Block(int type, boolean emitsLight) {
        this.type = type;
        this.emitsLight = emitsLight;
        if (type == World.TYPE_LAVA) {
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
