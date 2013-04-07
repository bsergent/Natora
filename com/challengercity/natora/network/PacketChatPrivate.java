
package com.challengercity.natora.network;

/**
 *
 * @author Ben Sergent V/ha1fBit
 */
public class PacketChatPrivate extends PacketChat {

    public String to = "";
    public String from = "";
    
    public PacketChatPrivate(String to, String from, String message, long timestamp) {
        super(message, timestamp);
        this.to = to;
        this.from = from;
    }
    
}
