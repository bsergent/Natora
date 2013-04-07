
package com.challengercity.natora.network;

/**
 *
 * @author Ben Sergent V/ha1fBit
 */
public abstract class Packet {

    public final long timestamp;
    private int packetID; // Needs to be implemented; all networking needs reorganization

    public Packet(long timestamp) {
        this.timestamp = timestamp;
    }

    public void handle() {
        // Do stuff
    }
    
}
