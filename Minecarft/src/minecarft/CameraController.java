/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
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
    
    public void walkForward(float distance) {
        position.x -= distance * (float) Math.sin(Math.toRadians(yaw));
        position.z += distance * (float) Math.cos(Math.toRadians(yaw));
    }
    
    public void walkBackwards(float distance) {
        position.x += distance * (float) Math.sin(Math.toRadians(yaw));
        position.z -= distance * (float) Math.cos(Math.toRadians(yaw));
    }
    
    public void strafeLeft(float distance) {
        position.x -= distance * (float) Math.sin(Math.toRadians(yaw - 90));
        position.z += distance * (float) Math.cos(Math.toRadians(yaw - 90));
    }
    
    public void strafeRight(float distance) {
        position.x -= distance * (float) Math.sin(Math.toRadians(yaw + 90));
        position.z += distance * (float) Math.cos(Math.toRadians(yaw + 90));
    }
    
    public void lookThrough() {
        glRotatef(pitch, 1.0f, 0.0f, 0.0f);
        glRotatef(yaw, 0.0f, 1.0f, 0.0f);
        glTranslatef(position.x, position.y, position.z);
    }
}
