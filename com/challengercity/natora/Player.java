
package com.challengercity.natora;

/**
 *
 * @author Ben Sergent V/ha1fBit
 */
public class Player extends Entity {

    private float headPitch = 0.0f;
    private float headYaw = 0.0f;
    
    public Player(Location loc) {
        super(loc);
    }
    
    public void setHeadRot(float pitch, float yaw) {
        // Pitch
        if (pitch > 90) {
            this.headPitch = 90;
        }
        if (pitch < -90) {
            this.headPitch = -90;
        }
        if (pitch >= -90 && pitch <= 90) {
            this.headPitch += pitch;
        }
        // Yaw
        if (yaw > 1.0f) {
            this.headYaw = yaw-1.0f;
        }
        if (yaw < 0.0f) {
            this.headYaw = yaw+1.0f;
        }
        if (yaw <= 1.0f && yaw >= 0.0f) {
            this.headYaw = yaw;
        }
    }
    
    public void setRelHeadRot(float pitch, float yaw) {
        // Pitch
        if (this.headPitch+pitch > 90) {
            this.headPitch = 90;
        }
        if (this.headPitch+pitch < -90) {
            this.headPitch = -90;
        }
        if (this.headPitch+pitch > -90 && this.headPitch+pitch < 90) {
            this.headPitch += pitch;
        }
        // Yaw
        if (this.headYaw+yaw > 360.0f) {
            this.headYaw = this.headYaw+yaw-360.0f;
        }
        if (this.headYaw+yaw < 0.0f) {
            this.headYaw = this.headYaw+yaw+360.0f;
        }
        if (this.headYaw+yaw <= 360.0f && this.headYaw+yaw >= 0.0f) {
            this.headYaw += yaw;
        }
    }
    
    public Location getViewLocation() {
        return loc.clone().setRot(headPitch, headYaw);
    }
    
    @Override
    public void tick(int delta) {
        if (super.getYVel() == 0f) {
            loc.setRot(headPitch, headYaw);
        }
        super.tick(delta);
    }
    
}
