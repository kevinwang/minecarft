/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package minecarft;

import org.lwjgl.util.glu.GLU;
import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.input.Keyboard;
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
        } catch(UnsatisfiedLinkError e) {
            System.out.println("Error! Make sure your run config is set to the correct OS. Exiting.");
            System.exit(0);
        } catch (LWJGLException e) {
            e.printStackTrace();
            System.exit(0);
        }

        glViewport(0, 0, 800, 600);

        glMatrixMode(GL_PROJECTION);
        glLoadIdentity();
        GLU.gluPerspective(45.0f, 800.0f / 600.0f, 0.1f, 100.0f);
        glMatrixMode(GL_MODELVIEW);

        glShadeModel(GL_SMOOTH);						// Enable Smooth Shading
        glClearColor(0.0f, 0.0f, 0.0f, 0.5f);				// Black Background
        glClearDepth(1.0f);								// Depth Buffer Setup
        glEnable(GL_DEPTH_TEST);						// Enables Depth Testing
        glDepthFunc(GL_LEQUAL);						// The Type Of Depth Testing To Do
        glHint(GL_PERSPECTIVE_CORRECTION_HINT, GL_NICEST);	// Really Nice Perspective Calculations

        runLoop();

        Display.destroy();
    }

    public void runLoop() {
        CameraController camera = new CameraController(0.0f, 0.0f, -7.0f);

        float dx = 0.0f;
        float dy = 0.0f;
        float dt = 0.0f;
        long lastTime = 0;
        long time = 0;

        float mouseSensitivity = 0.05f;
        float movementSpeed = 10.0f;

        Mouse.setGrabbed(true);

        while (!Display.isCloseRequested() && !Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)) {
            time = Sys.getTime();
            dt = (time - lastTime) / 1000.0f;
            lastTime = time;

            // Control camera yaw/pitch with mouse
            dx = Mouse.getDX();
            dy = -Mouse.getDY();
            camera.yaw(dx * mouseSensitivity);
            camera.pitch(dy * mouseSensitivity);

            // Control camera position with keyboard
            if (Keyboard.isKeyDown(Keyboard.KEY_W)) {
                camera.walkForward(movementSpeed * dt);
            }
            if (Keyboard.isKeyDown(Keyboard.KEY_S)) {
                camera.walkBackwards(movementSpeed * dt);
            }
            if (Keyboard.isKeyDown(Keyboard.KEY_A)) {
                camera.strafeLeft(movementSpeed * dt);
            }
            if (Keyboard.isKeyDown(Keyboard.KEY_D)) {
                camera.strafeRight(movementSpeed * dt);
            }

            // Begin drawing
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

            drawCube(0, 0, 0);
            
            glLoadIdentity();
            
            camera.lookThrough();

            Display.update();
            Display.sync(60);
        }
    }

    // TODO: Implement params
    public void drawCube(float x, float y, float z) {
        glBegin(GL_QUADS);		// Draw The Cube Using quads
        glColor3f(0.0f, 1.0f, 0.0f);	// Color Blue
        glVertex3f(1.0f, 1.0f, -1.0f);	// Top Right Of The Quad (Top)
        glVertex3f(-1.0f, 1.0f, -1.0f);	// Top Left Of The Quad (Top)
        glVertex3f(-1.0f, 1.0f, 1.0f);	// Bottom Left Of The Quad (Top)
        glVertex3f(1.0f, 1.0f, 1.0f);	// Bottom Right Of The Quad (Top)
        glColor3f(1.0f, 0.5f, 0.0f);	// Color Orange
        glVertex3f(1.0f, -1.0f, 1.0f);	// Top Right Of The Quad (Bottom)
        glVertex3f(-1.0f, -1.0f, 1.0f);	// Top Left Of The Quad (Bottom)
        glVertex3f(-1.0f, -1.0f, -1.0f);	// Bottom Left Of The Quad (Bottom)
        glVertex3f(1.0f, -1.0f, -1.0f);	// Bottom Right Of The Quad (Bottom)
        glColor3f(1.0f, 0.0f, 0.0f);	// Color Red	
        glVertex3f(1.0f, 1.0f, 1.0f);	// Top Right Of The Quad (Front)
        glVertex3f(-1.0f, 1.0f, 1.0f);	// Top Left Of The Quad (Front)
        glVertex3f(-1.0f, -1.0f, 1.0f);	// Bottom Left Of The Quad (Front)
        glVertex3f(1.0f, -1.0f, 1.0f);	// Bottom Right Of The Quad (Front)
        glColor3f(1.0f, 1.0f, 0.0f);	// Color Yellow
        glVertex3f(1.0f, -1.0f, -1.0f);	// Top Right Of The Quad (Back)
        glVertex3f(-1.0f, -1.0f, -1.0f);	// Top Left Of The Quad (Back)
        glVertex3f(-1.0f, 1.0f, -1.0f);	// Bottom Left Of The Quad (Back)
        glVertex3f(1.0f, 1.0f, -1.0f);	// Bottom Right Of The Quad (Back)
        glColor3f(0.0f, 0.0f, 1.0f);	// Color Blue
        glVertex3f(-1.0f, 1.0f, 1.0f);	// Top Right Of The Quad (Left)
        glVertex3f(-1.0f, 1.0f, -1.0f);	// Top Left Of The Quad (Left)
        glVertex3f(-1.0f, -1.0f, -1.0f);	// Bottom Left Of The Quad (Left)
        glVertex3f(-1.0f, -1.0f, 1.0f);	// Bottom Right Of The Quad (Left)
        glColor3f(1.0f, 0.0f, 1.0f);	// Color Violet
        glVertex3f(1.0f, 1.0f, -1.0f);	// Top Right Of The Quad (Right)
        glVertex3f(1.0f, 1.0f, 1.0f);	// Top Left Of The Quad (Right)
        glVertex3f(1.0f, -1.0f, 1.0f);	// Bottom Left Of The Quad (Right)
        glVertex3f(1.0f, -1.0f, -1.0f);	// Bottom Right Of The Quad (Right)
        glEnd();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Minecarft minecarft = new Minecarft();
        minecarft.start();
    }
}
