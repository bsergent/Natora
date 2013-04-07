
package com.challengercity.natora;

/**
 *
 * @author Ben Sergent V/ha1fBit
 */
public class Location extends Vertex {

    private float pitch,yaw;
    
    public Location(double x, double y, double z, float pitch, float yaw) {
        super(x,y,z);
        this.pitch = pitch;
        this.yaw = yaw;
    }
    
    @Override
    public Location clone() {
        return new Location(super.getX(),super.getY(),super.getZ(),pitch,yaw);
    }
    
    public double[] getPos() {
        double[] coords = new double[3];
        coords[0] = super.getX();
        coords[1] = super.getY();
        coords[2] = super.getZ();
        return coords;
    }
    
    public boolean inView(Vertex vert2) { // TODO Finish inView and lookingAt location methods
        return false;
    }
    
    public boolean inView(Vertex vert2, float fov) {
        return false;
    }
    
    public boolean lookingAt(Vertex vert2) {
        return false;
    }
    
    public Location setRot(float pitch, float yaw) {
        // Pitch
        if (pitch > 90) {
            this.pitch = 90;
        }
        if (pitch < -90) {
            this.pitch = -90;
        }
        if (pitch > -90 && pitch < 90) {
            this.pitch = pitch;
        }
        // Yaw
        if (yaw > 1.0f) {
            this.yaw = yaw-1.0f;
        }
        if (yaw < 0.0f) {
            this.yaw = yaw+1.0f;
        }
        if (yaw <= 1.0f && yaw >= 0.0f) {
            this.yaw = yaw;
        }
        return this;
    }
    
    public Location setRelRot(float pitch, float yaw) {
        // Pitch
        if (this.pitch+pitch > 90) {
            this.pitch = 90;
        }
        if (this.pitch+pitch < -90) {
            this.pitch = -90;
        }
        if (this.pitch+pitch > -90 && this.pitch+pitch < 90) {
            this.pitch += pitch;
        }
        // Yaw
        if (this.yaw+yaw > 360.0f) {
            this.yaw = this.yaw+yaw-360.0f;
        }
        if (this.yaw+yaw < 0.0f) {
            this.yaw = this.yaw+yaw+360.0f;
        }
        if (this.yaw+yaw <= 360.0f && this.yaw+yaw >= 0.0f) {
            this.yaw += yaw;
        }
        return this;
    }
    
    public float getPitch() {
        return pitch;
    }

    public float getYaw() {
        return yaw;
    }
    
}
