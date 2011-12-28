/**
 * CameraController.java
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

import org.lwjgl.util.vector.Vector3f;
import static org.lwjgl.opengl.GL11.*;

/**
 *
 * @author kevin
 */
public class Player {
    public static final float PLAYER_HEIGHT = 0.5f;
    public static final float MOVEMENT_SPEED = 2.0f;
    
    private Vector3f position = null;
    private float yaw = 0.0f;
    private float pitch = 0.0f;
    
    // Camera velocity components
    private float vFwdRev = 0.0f;
    private float vStrafe = 0.0f;
    private float vY = 0.0f;
    
    private boolean noclip = true;
    
    private World world;
    
    public Player(float x, float y, float z) {
        position = new Vector3f(x, y, z);
        world = World.getInstance();
    }
    
    public void yaw(float amount) {
        yaw += amount;
    }
    
    public void pitch(float amount) {
        pitch += amount;
        pitch = pitch < -90 ? -90 : pitch;
        pitch = pitch > 90 ? 90 : pitch;
    }
    
    public void walkForward(float acceleration) {
        vFwdRev = vFwdRev > acceleration ? acceleration : vFwdRev + acceleration / 5;
    }
    
    public void walkBackwards(float acceleration) {
        vFwdRev = vFwdRev < -acceleration ? -acceleration : vFwdRev - acceleration / 5;
    }
    
    public void slowDownFwdRev(float acceleration) {
        float lastV = vFwdRev;
        if (vFwdRev > 0.0f) {
            vFwdRev -= acceleration / 5;
        }
        else if (vFwdRev < 0.0f) {
            vFwdRev += acceleration / 5;
        }
        if (vFwdRev < 0 && lastV > 0 || vFwdRev > 0 && lastV < 0) {
            vFwdRev = 0;
        }
    }
    
    public void strafeLeft(float acceleration) {
        vStrafe = vStrafe > acceleration ? acceleration : vStrafe + acceleration / 5;
    }
    
    public void strafeRight(float acceleration) {
        vStrafe = vStrafe < -acceleration ? -acceleration : vStrafe - acceleration / 5;
    }
    
    public void slowDownStrafe(float acceleration) {
        float lastV = vStrafe;
        if (vStrafe > 0.0f) {
            vStrafe -= acceleration / 5;
        }
        else if (vStrafe < 0.0f) {
            vStrafe += acceleration / 5;
        }
        if (vStrafe < 0 && lastV > 0 || vStrafe > 0 && lastV < 0) {
            vStrafe = 0;
        }
    }
    
    public void jump() {
        vY = 0.5f;
    }
    
    public void applyGravity() {
        if (vY > -0.5f) {
            vY -= 0.1f;
        }
        if (isOnGround()) {
            vY = 0.0f;
        }
    }
    
    public boolean isOnGround() {
        try {
            int arrayZ = (int) (position.z * (1.0f / Minecarft.BLOCK_SIZE));
            int arrayX = (int) (-position.x * (1.0f / Minecarft.BLOCK_SIZE));
            int arrayY = (int) ((-position.y - Minecarft.BLOCK_SIZE - PLAYER_HEIGHT) * (1.0f / Minecarft.BLOCK_SIZE));
            return world.getWorld()[arrayZ][arrayX][arrayY].getType() != World.TYPE_AIR;
        } catch (ArrayIndexOutOfBoundsException e) {
            return false;
        }
    }
    
    public void lookThrough() {
        // Move camrea forward/backward
        float dx = vFwdRev * (float) Math.sin(Math.toRadians(yaw));
        float dz = vFwdRev * (float) Math.cos(Math.toRadians(yaw));
        if (noclip) {
            dx *= (float) Math.cos(Math.toRadians(pitch));
            dz *= (float) Math.cos(Math.toRadians(pitch));
            position.y += vFwdRev * (float) Math.sin(Math.toRadians(pitch));
        }
        position.x -= dx;
        position.z += dz;
        
        // Strafe camera
        position.x -= vStrafe * (float) Math.sin(Math.toRadians(yaw - 90));
        position.z += vStrafe * (float) Math.cos(Math.toRadians(yaw - 90));
        
        // Gravity
        if (!noclip) {
            position.y -= vY;
        }
        
        // Apply camera changes to OpenGL matrix
        glRotatef(pitch, 1.0f, 0.0f, 0.0f);
        glRotatef(yaw, 0.0f, 1.0f, 0.0f);
        glTranslatef(position.x, position.y, position.z);
        
        // Print debug info
//        System.out.println("x = " + position.x + " y = " + position.y + " z = " + position.z + " yaw = " + yaw + " pitch = " + pitch + " vFwdRev = " + vFwdRev + " vStrafe = " + vStrafe);
    }
    
    public Vector3f getPosition() {
        return position;
    }
}
