/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package minecarft;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.*;
import static org.lwjgl.opengl.GL11.*;

/**
 *
 * @author Kevin Wang
 */
public class Minecarft {
    
    public void start() {
        try {
            Display.setDisplayMode(new DisplayMode(800, 600));
            Display.setTitle("Minecarft");
            Display.create();
        } catch (LWJGLException e) {
            e.printStackTrace();
            System.exit(0);
        }
        
        glMatrixMode(GL_PROJECTION);
        glLoadIdentity();
        glOrtho(0, 800, 600, 0, 1, -1);
        glMatrixMode(GL_MODELVIEW);
        
        while (!Display.isCloseRequested()) {
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
            
            glColor3f(0.5f, 0.5f, 1.0f);
            
            glBegin(GL_QUADS);
            glVertex2f(100,100);
            glVertex2f(100 + 200, 100);
            glVertex2f(100 + 200, 100 + 200);
            glVertex2f(100, 100 + 200);
            glEnd();
            
            glColor3f(1, 1, 1);
            
            glBegin(GL_QUADS);
            int x = Mouse.getX();
            int y = 600 - Mouse.getY();
            glVertex2f(x - 100, y - 100);
            glVertex2f(x + 100, y - 100);
            glVertex2f(x + 100, y + 100);
            glVertex2f(x - 100, y + 100);
            glEnd();
            
            Display.update();
        }
        Display.destroy();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Minecarft minecarft = new Minecarft();
        minecarft.start();
    }

}
