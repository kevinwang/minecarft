/**
 * Block.java
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

    public Block(int type, int brightness) {
        this(type, false);
        setBrightness(brightness);
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
