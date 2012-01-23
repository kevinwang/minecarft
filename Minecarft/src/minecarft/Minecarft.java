/**
 * Minecarft.java
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

import java.io.File;
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
    public static final float MOUSE_SENSITIVITY = 0.1f;
    public static final float BLOCK_SIZE = 0.15f;
    public static final float[] RENDER_DISTANCES = {5.0f, 10.0f, 20.0f, 9000.0f};
    private int renderDistanceIndex = 0;
    
    private DisplayMode mode;
    private boolean isFullscreen = false;
    
    private Player player;
    
    private World world;
    
    private Texture stoneTexture;
    private Texture dirtTexture;
    private Texture sandTexture;
    private Texture dirtGrassTexture;
    private Texture waterTexture;
    private Texture lavaTexture;
    private Texture grassTexture;
    private Texture bedrockTexture;
    private Texture leavesTexture;
    private Texture woodTexture;
    
    public Minecarft(DisplayMode mode, boolean isFullscreen) {
        this.mode = mode;
        this.isFullscreen = isFullscreen;
        world = World.getInstance();
        LightingController.calculateLighting();
    }

    public Minecarft(DisplayMode mode, boolean isFullscreen, File savefile) {
        this.mode = mode;
        this.isFullscreen = isFullscreen;
        Launcher.getInstance().setProgressLabel("Loading savefile...");
        world = World.getInstance(savefile);
    }

    public void start() {
        Launcher.getInstance().close();

        try {
            Display.setDisplayMode(mode);
            Display.setFullscreen(isFullscreen);
            Display.setTitle("Minecarft");
            Display.create();
        } catch(UnsatisfiedLinkError e) {
            System.err.println("Error! Make sure your run config is set to the correct OS. Exiting.");
            System.exit(1);
        } catch (LWJGLException e) {
            System.err.println("Lasers error! Exiting.");
            System.exit(3);
        }

        glViewport(0, 0, mode.getWidth(), mode.getHeight());

        glMatrixMode(GL_PROJECTION);
        glLoadIdentity();
        GLU.gluPerspective(45.0f, (float)mode.getWidth() / (float)mode.getHeight(), 0.1f, 100.0f);
        glMatrixMode(GL_MODELVIEW);

        glShadeModel(GL_SMOOTH);						// Enable Smooth Shading
        glClearColor(135.0f/255.0f, 206.0f/255.0f, 235.0f/255.0f, 0.5f);				// Black Background
        glClearDepth(1.0f);								// Depth Buffer Setup
        glEnable(GL_DEPTH_TEST);						// Enables Depth Testing
        glDepthFunc(GL_LEQUAL);						// The Type Of Depth Testing To Do
        glHint(GL_PERSPECTIVE_CORRECTION_HINT, GL_NICEST);	// Really Nice Perspective Calculations

        glAlphaFunc(GL_GREATER, 0.5f);
        glEnable(GL_ALPHA_TEST);
        
        player = new Player(-16.0f, -13.0f, 16.0f);
        
        initTextures();

        runLoop();

        Display.destroy();
    }
    
    private void initTextures() {
        try {
            stoneTexture = TextureLoader.getTexture("PNG", new FileInputStream("stone.png"));
            dirtTexture = TextureLoader.getTexture("PNG", new FileInputStream("dirt.png"));
            sandTexture = TextureLoader.getTexture("PNG", new FileInputStream("sand.png"));
            leavesTexture = TextureLoader.getTexture("PNG", new FileInputStream("leaves.png"));
            dirtGrassTexture = TextureLoader.getTexture("PNG", new FileInputStream("dirt_grass.png"));
            waterTexture = TextureLoader.getTexture("PNG", new FileInputStream("water.png"));
            lavaTexture = TextureLoader.getTexture("PNG", new FileInputStream("lava.png"));
            grassTexture = TextureLoader.getTexture("PNG", new FileInputStream("grass.png"));
            bedrockTexture = TextureLoader.getTexture("PNG", new FileInputStream("bedrock.png"));
            woodTexture = TextureLoader.getTexture("PNG", new FileInputStream("wood.png"));
        } catch (Exception e) {
            System.err.println("Error! Could not load textures. Exiting.");
            System.exit(2);
        }
    }

    private void runLoop() {
        float dx = 0.0f;
        float dy = 0.0f;
        float dt = 0.0f;
        long lastTime = 0;
        long time = 0;

        Mouse.setGrabbed(true);

        boolean lastF = false;
        boolean lastEsc = false;

        while (!Display.isCloseRequested()) {
            time = Sys.getTime();
            dt = (time - lastTime) / 1000.0f;
            lastTime = time;

            // Control camera yaw/pitch with mouse
            dx = Mouse.getDX();
            dy = -Mouse.getDY();
            player.yaw(dx * MOUSE_SENSITIVITY);
            player.pitch(dy * MOUSE_SENSITIVITY);

            // Control camera position with keyboard
            if (Keyboard.isKeyDown(Keyboard.KEY_W)) {
                player.walkForward(Player.MOVEMENT_SPEED * dt);
            }
            else if (Keyboard.isKeyDown(Keyboard.KEY_S)) {
                player.walkBackwards(Player.MOVEMENT_SPEED * dt);
            }
            else {
                player.slowDownFwdRev(Player.MOVEMENT_SPEED * dt);
            }
            if (Keyboard.isKeyDown(Keyboard.KEY_A)) {
                player.strafeLeft(Player.MOVEMENT_SPEED * dt);
            }
            else if (Keyboard.isKeyDown(Keyboard.KEY_D)) {
                player.strafeRight(Player.MOVEMENT_SPEED * dt);
            }
            else {
                player.slowDownStrafe(Player.MOVEMENT_SPEED * dt);
            }
            if (Keyboard.isKeyDown(Keyboard.KEY_SPACE)) {
                player.jump();
            }
            else {
                player.applyGravity();
            }
            if (Keyboard.isKeyDown((Keyboard.KEY_F))) {
                if (!lastF) {
                    renderDistanceIndex = renderDistanceIndex == RENDER_DISTANCES.length - 1 ? 0 : renderDistanceIndex + 1;
                    lastF = true;
                }
            }
            else {
                lastF = false;
            }
            
            if (Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)) {
                if (!lastEsc) {
                    Mouse.setGrabbed(false);
                    PauseMenu pauseMenu = new PauseMenu();
                    pauseMenu.setVisible(true);
                    while (pauseMenu.isValid()) {
                    }
                    Mouse.setGrabbed(true);
                    lastEsc = true;
                }
            }
            else {
                lastEsc = false;
            }

            // Begin drawing
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
            
            drawWorld();
            
            glLoadIdentity();
            
            player.lookThrough();

            Display.update();
            Display.sync(60);
        }
    }
    
    private void drawWorld() {
        Block[][][] blocks = world.getWorld();
        for (int z = 0; z < blocks.length; z++) {
            for (int x = 0; x < blocks[0].length; x++) {
                for (int y = 0; y < blocks[0][0].length; y++) {
                    Block block = blocks[z][x][y];
                    if (block.getType() != Block.TYPE_AIR && world.isVisible(x, y, z)) {
                        if (block.getType() == Block.TYPE_DIRT && (y == World.Y - 1 || blocks[z][x][y + 1].getType() == Block.TYPE_AIR)) {
                            drawCube(x * BLOCK_SIZE, y * BLOCK_SIZE, -z * BLOCK_SIZE, Block.TYPE_DIRT_GRASS, block.getBrightness());
                        }
                        else {
                            drawCube(x * BLOCK_SIZE, y * BLOCK_SIZE, -z * BLOCK_SIZE, block.getType(), block.getBrightness());
                        }
                    }
                }
            }
        }
    }

    private void drawCube(float x, float y, float z, int type, int brightness) {
        if (Math.sqrt((player.getPosition().x + x) * (player.getPosition().x - -x) +
                (player.getPosition().y + y) * (player.getPosition().y - -y) +
                (player.getPosition().z + z) * (player.getPosition().z - -z)) > RENDER_DISTANCES[renderDistanceIndex]) {
            return;
        }
        
        switch (type) {
            case Block.TYPE_STONE:
                stoneTexture.bind();
                break;
            case Block.TYPE_DIRT:
                dirtTexture.bind();
                break;
            case Block.TYPE_SAND:
                sandTexture.bind();
                break;
            case Block.TYPE_WOOD:
                woodTexture.bind();
                break;
            case Block.TYPE_LEAVES:
                leavesTexture.bind();
                break;
            case Block.TYPE_WATER:
                waterTexture.bind();
                break;
            case Block.TYPE_LAVA:
                lavaTexture.bind();
                break;
            case Block.TYPE_DIRT_GRASS:
                grassTexture.bind();
                break;
            case Block.TYPE_BEDROCK:
                bedrockTexture.bind();
                break;
        }
        
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
        
        glBegin(GL_QUADS);		// Draw The Cube Using quads

        glColor3f(brightness / 8.0f, brightness / 8.0f, brightness / 8.0f);
        
        if (player.getPosition().y < -y + BLOCK_SIZE) {
            glTexCoord2f(0.0f, 0.0f);
            glVertex3f(x + BLOCK_SIZE / 2, y + BLOCK_SIZE, z - BLOCK_SIZE / 2);	// Top Right Of The Quad (Top)
            glTexCoord2f(0.0f, 1.0f);
            glVertex3f(x - BLOCK_SIZE / 2, y + BLOCK_SIZE, z - BLOCK_SIZE / 2);	// Top Left Of The Quad (Top)
            glTexCoord2f(1.0f, 1.0f);
            glVertex3f(x - BLOCK_SIZE / 2, y + BLOCK_SIZE, z + BLOCK_SIZE / 2);	// Bottom Left Of The Quad (Top)
            glTexCoord2f(1.0f, 0.0f);
            glVertex3f(x + BLOCK_SIZE/2, y + BLOCK_SIZE, z + BLOCK_SIZE/2);	// Bottom Right Of The Quad (Top)
        }
        
        if (type == Block.TYPE_DIRT_GRASS) {
            glEnd();
            dirtTexture.bind();
            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
            glBegin(GL_QUADS);
        }

        if (player.getPosition().y > -y) {
            glTexCoord2f(0.0f, 0.0f);
            glVertex3f(x + BLOCK_SIZE / 2, y, z + BLOCK_SIZE / 2);	// Top Right Of The Quad (Bottom)
            glTexCoord2f(0.0f, 1.0f);
            glVertex3f(x - BLOCK_SIZE / 2, y, z + BLOCK_SIZE / 2);	// Top Left Of The Quad (Bottom)
            glTexCoord2f(1.0f, 1.0f);
            glVertex3f(x - BLOCK_SIZE / 2, y, z - BLOCK_SIZE / 2);	// Bottom Left Of The Quad (Bottom)
            glTexCoord2f(1.0f, 0.0f);
            glVertex3f(x + BLOCK_SIZE/2, y, z - BLOCK_SIZE/2);	// Bottom Right Of The Quad (Bottom)
        }
        
        if (type == Block.TYPE_DIRT_GRASS) {
            glEnd();
            dirtGrassTexture.bind();
            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
            glBegin(GL_QUADS);
        }
        
        if (player.getPosition().z < -z + BLOCK_SIZE / 2) {
            glTexCoord2f(1.0f, 0.0f);
            glVertex3f(x + BLOCK_SIZE / 2, y + BLOCK_SIZE, z + BLOCK_SIZE / 2);	// Top Right Of The Quad (Front)
            glTexCoord2f(0.0f, 0.0f);
            glVertex3f(x - BLOCK_SIZE / 2, y + BLOCK_SIZE, z + BLOCK_SIZE / 2);	// Top Left Of The Quad (Front)
            glTexCoord2f(0.0f, 1.0f);
            glVertex3f(x - BLOCK_SIZE / 2, y, z + BLOCK_SIZE / 2);	// Bottom Left Of The Quad (Front)
            glTexCoord2f(1.0f, 1.0f);
            glVertex3f(x + BLOCK_SIZE / 2, y, z + BLOCK_SIZE/2);	// Bottom Right Of The Quad (Front)
        }
        
        if (player.getPosition().z > -z - BLOCK_SIZE / 2) {
            glTexCoord2f(1.0f, 1.0f);
            glVertex3f(x + BLOCK_SIZE / 2, y, z - BLOCK_SIZE / 2);	// Top Right Of The Quad (Back)
            glTexCoord2f(0.0f, 1.0f);
            glVertex3f(x - BLOCK_SIZE / 2, y, z - BLOCK_SIZE / 2);	// Top Left Of The Quad (Back)
            glTexCoord2f(0.0f, 0.0f);
            glVertex3f(x - BLOCK_SIZE / 2, y + BLOCK_SIZE, z - BLOCK_SIZE / 2);	// Bottom Left Of The Quad (Back)
            glTexCoord2f(1.0f, 0.0f);
            glVertex3f(x + BLOCK_SIZE/2, y + BLOCK_SIZE, z - BLOCK_SIZE/2);	// Bottom Right Of The Quad (Back)
        }
        
        if (player.getPosition().x > -x - BLOCK_SIZE / 2) {
            glTexCoord2f(1.0f, 0.0f);
            glVertex3f(x - BLOCK_SIZE / 2, y + BLOCK_SIZE, z + BLOCK_SIZE / 2);	// Top Right Of The Quad (Left)
            glTexCoord2f(0.0f, 0.0f);
            glVertex3f(x - BLOCK_SIZE / 2, y + BLOCK_SIZE, z - BLOCK_SIZE / 2);	// Top Left Of The Quad (Left)
            glTexCoord2f(0.0f, 1.0f);
            glVertex3f(x - BLOCK_SIZE / 2, y, z - BLOCK_SIZE / 2);	// Bottom Left Of The Quad (Left)
            glTexCoord2f(1.0f, 1.0f);
            glVertex3f(x - BLOCK_SIZE/2, y, z + BLOCK_SIZE/2);	// Bottom Right Of The Quad (Left)
        }
        
        if (player.getPosition().x < -x + BLOCK_SIZE / 2) {
            glTexCoord2f(1.0f, 0.0f);
            glVertex3f(x + BLOCK_SIZE / 2, y + BLOCK_SIZE, z - BLOCK_SIZE / 2);	// Top Right Of The Quad (Right)
            glTexCoord2f(0.0f, 0.0f);
            glVertex3f(x + BLOCK_SIZE / 2, y + BLOCK_SIZE, z + BLOCK_SIZE / 2);	// Top Left Of The Quad (Right)
            glTexCoord2f(0.0f, 1.0f);
            glVertex3f(x + BLOCK_SIZE / 2, y, z + BLOCK_SIZE / 2);	// Bottom Left Of The Quad (Right)
            glTexCoord2f(1.0f, 1.0f);
            glVertex3f(x + BLOCK_SIZE / 2, y, z - BLOCK_SIZE / 2);	// Bottom Right Of The Quad (Right)
        }
        glEnd();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            Launcher l = Launcher.getInstance();
            l.setVisible(true);
        } catch (Exception e) {
        }
    }
}
