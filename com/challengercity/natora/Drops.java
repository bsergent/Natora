
package com.challengercity.natora;

/**
 *
 * @author Ben Sergent V/ha1fBit
 */
public class Drops {

    ItemType[] items;
    int[] amts;

    public Drops() {
        this.items = null;
        this.amts = null;
    }
    
    public Drops(ItemType[] items, int[] amts) {
        if (items.length == amts.length) {
            this.items = items;
            this.amts = amts;
        } else {
            System.out.println("[ERR] Drops could not be initilized. Drops and amounts did not match.");
        }
    }
    
    public Drops setDrops(ItemType[] items, int[] amts) {
        if (items.length == amts.length) {
            this.items = items;
            this. amts = amts;
        } else {
            System.out.println("[ERR] Drops could not be set. Drops and amounts did not match.");
        }
        return this;
    }
    
//    public Drops addDrops(ItemType[] items, int[] amts) {
//        if (items.length == amts.length) {
//            for (int i = 0; i < items.length; i++) {
//            }
//        } else {
//            System.out.println("[ERR] Drops could not be added. Drops and amounts did not match.");
//        }
//        return this;
//    }
    
    // TODO Add ways of manipulating and removing drops
    
    public ItemType[] getItemTypes() {
        return items;
    }
    
    public int[] getItemAmounts() {
        return amts;
    }
    
}
