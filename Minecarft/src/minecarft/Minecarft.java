/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package minecarft;

import java.io.FileInputStream;
import org.newdawn.slick.opengl.Texture;
import org.lwjgl.util.glu.GLU;
import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.*;
import org.newdawn.slick.opengl.TextureLoader;
import static org.lwjgl.opengl.GL11.*;

/**
 *
 * @author Kevin Wang
 */
public class Minecarft {
    public static final float BLOCK_SIZE = 0.25f;
    
    World world = new World();
    
    Texture stoneTexture;
    Texture dirtTexture;
    Texture dirtGrassTexture;
    Texture grassTexture;

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
        
        initTextures();

        runLoop();

        Display.destroy();
    }
    
    public void initTextures() {
        glEnable(GL_TEXTURE_2D);
        glTexEnvf(GL_TEXTURE_ENV, GL_TEXTURE_ENV_MODE, GL_REPLACE); 
        try {
            dirtTexture = TextureLoader.getTexture("PNG", new FileInputStream("dirt.png"));
            dirtGrassTexture = TextureLoader.getTexture("PNG", new FileInputStream("dirt_grass.png"));
            grassTexture = TextureLoader.getTexture("PNG", new FileInputStream("grass.png"));
        } catch (Exception e) {
            System.out.println("Error! Could not load textures.");
            System.exit(0);
        }
    }

    public void runLoop() {
        CameraController camera = new CameraController(0.0f, -16.0f, -3.0f);

        float dx = 0.0f;
        float dy = 0.0f;
        float dt = 0.0f;
        long lastTime = 0;
        long time = 0;

        float mouseSensitivity = 0.1f;
        float movementSpeed = 5.0f;

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
            
            drawWorld();
            
            glLoadIdentity();
            
            camera.lookThrough();

            Display.update();
            Display.sync(60);
        }
    }
    
    public void drawWorld() {
        int[][][] blocks = world.getWorld();
        for (int z = 0; z < blocks.length; z++) {
            for (int x = 0; x < blocks[0].length; x++) {
                for (int y = 0; y < blocks[0][0].length; y++) {
                    int block = blocks[z][x][y];
                    if (block != World.TYPE_AIR && world.isVisible(x, y, z)) {
                        if (block == World.TYPE_DIRT && (y == World.Y - 1 || blocks[z][x][y + 1] == World.TYPE_AIR)) {
                            drawCube(x * BLOCK_SIZE, y * BLOCK_SIZE, -z * BLOCK_SIZE, World.TYPE_DIRT_GRASS);
                        }
                        else {
                            drawCube(x * BLOCK_SIZE, y * BLOCK_SIZE, -z * BLOCK_SIZE, block);
}
                    }
                }
            }
        }
    }

    public void drawCube(float x, float y, float z, int type) {
        switch (type) {
            case World.TYPE_STONE:
                stoneTexture.bind();
                break;
            case World.TYPE_DIRT:
                dirtTexture.bind();
                break;
            case World.TYPE_DIRT_GRASS:
                grassTexture.bind();
                break;
        }
        
        glBegin(GL_QUADS);		// Draw The Cube Using quads
        
        glColor3f(1.0f, 1.0f, 1.0f);
        glTexCoord2f(0.0f, 0.0f);
        glVertex3f(x + BLOCK_SIZE/2, y + BLOCK_SIZE, z - BLOCK_SIZE/2);	// Top Right Of The Quad (Top)
        glTexCoord2f(0.0f, 1.0f);
        glVertex3f(x - BLOCK_SIZE/2, y + BLOCK_SIZE, z - BLOCK_SIZE/2);	// Top Left Of The Quad (Top)
        glTexCoord2f(1.0f, 1.0f);
        glVertex3f(x - BLOCK_SIZE/2, y + BLOCK_SIZE, z + BLOCK_SIZE/2);	// Bottom Left Of The Quad (Top)
        glTexCoord2f(1.0f, 0.0f);
        glVertex3f(x + BLOCK_SIZE/2, y + BLOCK_SIZE, z + BLOCK_SIZE/2);	// Bottom Right Of The Quad (Top)
//      
        if (type == World.TYPE_DIRT_GRASS) {
            glEnd();
            dirtTexture.bind();
            glBegin(GL_QUADS);
        }
        
        glTexCoord2f(0.0f, 0.0f);
        glVertex3f(x + BLOCK_SIZE/2, y, z + BLOCK_SIZE/2);	// Top Right Of The Quad (Bottom)
        glTexCoord2f(0.0f, 1.0f);
        glVertex3f(x - BLOCK_SIZE/2, y, z + BLOCK_SIZE/2);	// Top Left Of The Quad (Bottom)
        glTexCoord2f(1.0f, 1.0f);
        glVertex3f(x - BLOCK_SIZE/2, y, z - BLOCK_SIZE/2);	// Bottom Left Of The Quad (Bottom)
        glTexCoord2f(1.0f, 0.0f);
        glVertex3f(x + BLOCK_SIZE/2, y, z - BLOCK_SIZE/2);	// Bottom Right Of The Quad (Bottom)

        if (type == World.TYPE_DIRT_GRASS) {
            glEnd();
            dirtGrassTexture.bind();
            glBegin(GL_QUADS);
        }
        
        glTexCoord2f(1.0f, 0.0f);
        glVertex3f(x + BLOCK_SIZE/2, y + BLOCK_SIZE, z + BLOCK_SIZE/2);	// Top Right Of The Quad (Front)
        glTexCoord2f(0.0f, 0.0f);
        glVertex3f(x - BLOCK_SIZE/2, y + BLOCK_SIZE, z + BLOCK_SIZE/2);	// Top Left Of The Quad (Front)
        glTexCoord2f(0.0f, 1.0f);
        glVertex3f(x - BLOCK_SIZE/2, y, z + BLOCK_SIZE/2);	// Bottom Left Of The Quad (Front)
        glTexCoord2f(1.0f, 1.0f);
        glVertex3f(x + BLOCK_SIZE/2, y, z + BLOCK_SIZE/2);	// Bottom Right Of The Quad (Front)
        
        glTexCoord2f(1.0f, 1.0f);
        glVertex3f(x + BLOCK_SIZE/2, y, z - BLOCK_SIZE/2);	// Top Right Of The Quad (Back)
        glTexCoord2f(0.0f, 1.0f);
        glVertex3f(x - BLOCK_SIZE/2, y, z - BLOCK_SIZE/2);	// Top Left Of The Quad (Back)
        glTexCoord2f(0.0f, 0.0f);
        glVertex3f(x - BLOCK_SIZE/2, y + BLOCK_SIZE, z - BLOCK_SIZE/2);	// Bottom Left Of The Quad (Back)
        glTexCoord2f(1.0f, 0.0f);
        glVertex3f(x + BLOCK_SIZE/2, y + BLOCK_SIZE, z - BLOCK_SIZE/2);	// Bottom Right Of The Quad (Back)
        
        glTexCoord2f(1.0f, 0.0f);
        glVertex3f(x - BLOCK_SIZE/2, y + BLOCK_SIZE, z + BLOCK_SIZE/2);	// Top Right Of The Quad (Left)
        glTexCoord2f(0.0f, 0.0f);
        glVertex3f(x - BLOCK_SIZE/2, y + BLOCK_SIZE, z - BLOCK_SIZE/2);	// Top Left Of The Quad (Left)
        glTexCoord2f(0.0f, 1.0f);
        glVertex3f(x - BLOCK_SIZE/2, y, z - BLOCK_SIZE/2);	// Bottom Left Of The Quad (Left)
        glTexCoord2f(1.0f, 1.0f);
        glVertex3f(x - BLOCK_SIZE/2, y, z + BLOCK_SIZE/2);	// Bottom Right Of The Quad (Left)
        
        glTexCoord2f(1.0f, 0.0f);
        glVertex3f(x + BLOCK_SIZE/2, y + BLOCK_SIZE, z - BLOCK_SIZE/2);	// Top Right Of The Quad (Right)
        glTexCoord2f(0.0f, 0.0f);
        glVertex3f(x + BLOCK_SIZE/2, y + BLOCK_SIZE, z + BLOCK_SIZE/2);	// Top Left Of The Quad (Right)
        glTexCoord2f(0.0f, 1.0f);
        glVertex3f(x + BLOCK_SIZE/2, y, z + BLOCK_SIZE/2);	// Bottom Left Of The Quad (Right)
        glTexCoord2f(1.0f, 1.0f);
        glVertex3f(x + BLOCK_SIZE/2, y, z - BLOCK_SIZE/2);	// Bottom Right Of The Quad (Right)
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
