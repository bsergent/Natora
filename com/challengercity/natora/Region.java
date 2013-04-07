
package com.challengercity.natora;

/**
 *
 * @author Ben Sergent V/ha1fBit
 */
public class Region {

    private Location loc1;
    private Location loc2;

    public Region(Location loc1, Location loc2) {
        this.loc1 = loc1;
        this.loc2 = loc2;
    }

    public Location getLoc1() {
        return loc1;
    }

    public Location getLoc2() {
        return loc2;
    }

    public void setLoc1(Location loc1) {
        this.loc1 = loc1;
    }

    public void setLoc2(Location loc2) {
        this.loc2 = loc2;
    }
    
    public boolean locInsideRegion(Location loc) {
        if (((loc.getX() <= loc1.getX() && loc.getX() >= loc2.getX()) || (loc.getX() >= loc1.getX() && loc.getX() <= loc2.getX())) // X is between the points
                && ((loc.getY() <= loc1.getY() && loc.getY() >= loc2.getY()) || (loc.getY() >= loc1.getY() && loc.getY() <= loc2.getY())) // Y is between the points
                &&((loc.getZ() <= loc1.getZ() && loc.getZ() >= loc2.getZ()) || (loc.getZ() >= loc1.getZ() && loc.getZ() <= loc2.getZ()))) { // Z is between the points
            return true;
        } else {
            return false;
        }
    }
    
}
