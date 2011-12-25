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
public class CameraController {
    private Vector3f position = null;
    private float yaw = 0.0f;
    private float pitch = 0.0f;
    
    // Camera velocity components
    private float vFwdRev = 0.0f;
    private float vStrafe = 0.0f;
    
    private boolean noclip = true;
    
    public CameraController(float x, float y, float z) {
        position = new Vector3f(x, y, z);
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
        vFwdRev = vFwdRev > acceleration ? acceleration : vFwdRev + acceleration / 10;
    }
    
    public void walkBackwards(float acceleration) {
        vFwdRev = vFwdRev < -acceleration ? -acceleration : vFwdRev - acceleration / 10;
    }
    
    public void slowDownFwdRev(float acceleration) {
        if (Math.abs(vFwdRev) < 0.01f) {
            vFwdRev = 0.0f;
        }
        if (vFwdRev > 0.0f) {
            vFwdRev -= acceleration / 10;
        }
        else if (vFwdRev < 0.0f) {
            vFwdRev += acceleration / 10;
        }
    }
    
    public void strafeLeft(float acceleration) {
        vStrafe = vStrafe > acceleration ? acceleration : vStrafe + acceleration / 10;
    }
    
    public void strafeRight(float acceleration) {
        vStrafe = vStrafe < -acceleration ? -acceleration : vStrafe - acceleration / 10;
    }
    
    public void slowDownStrafe(float acceleration) {
        if (Math.abs(vStrafe) < 0.01f) {
            vStrafe = 0.0f;
        }
        if (vStrafe > 0.0f) {
            vStrafe -= acceleration / 10;
        }
        else if (vStrafe < 0.0f) {
            vStrafe += acceleration / 10;
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
        
        // Apply camera changes to OpenGL matrix
        glRotatef(pitch, 1.0f, 0.0f, 0.0f);
        glRotatef(yaw, 0.0f, 1.0f, 0.0f);
        glTranslatef(position.x, position.y, position.z);
        
        // Print debug info
        System.out.println("x = " + position.x + " y = " + position.y + " z = " + position.z + " yaw = " + yaw + " pitch = " + pitch + " vFwdRev = " + vFwdRev + " vStrafe = " + vStrafe);
    }
}
