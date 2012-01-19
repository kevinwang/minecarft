/**
 * FileIO.java
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

import java.awt.Component;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.JFileChooser;

/**
 *
 * @author kevin
 */
public class FileIO extends Component {

    public static void saveMap() {
        try {
            String filename = "wangshi.sav";
            FileWriter writer = new FileWriter(new File(filename));
            Block[][][] world = World.getInstance().getWorld();
            writer.write("z,x,y,type,brightness\n");
            for (int z = 0; z < world.length; z++) {
                for (int x = 0; x < world[0].length; x++) {
                    for (int y = 0; y < world[0][0].length; y++) {
                        writer.write(z + "," + x + "," + y + "," + world[z][x][y].getType() + "," + world[z][x][y].getBrightness() + "\n");
                    }
                }
            }
            writer.close();
        } catch (IOException e) {
        }
    }
}