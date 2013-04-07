
package com.challengercity.natora.network;

/**
 *
 * @author Ben Sergent V/ha1fBit
 */
public class PacketEntityVelocity extends PacketEntity {

    float pitch = 0;
    float yaw = 0;
    
    public PacketEntityVelocity(float pitch, float yaw, long timestamp) {
        super(timestamp);
        this.pitch = pitch;
        this.yaw = yaw;
    }
}
