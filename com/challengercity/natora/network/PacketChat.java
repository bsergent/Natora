
package com.challengercity.natora.network;

/**
 *
 * @author Ben Sergent V/ha1fBit
 */
public class PacketChat extends Packet {

    public String message = "";
    
    public PacketChat(String message, long timestamp) {
        super(timestamp);
        this.message = message;
    }

}
