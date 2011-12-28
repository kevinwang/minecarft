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
    public static final boolean EMITS_LIGHT = true;
    public static final boolean DOES_NOT_EMIT_LIGHT = false;

    private int type;
    private int brightness;
    private boolean emitsLight;

    public Block(int type, boolean emitsLight) {
        this.type = type;
        this.emitsLight = emitsLight;
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
