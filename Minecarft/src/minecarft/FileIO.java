/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
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
