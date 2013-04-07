
package com.challengercity.natora;

/**
 *
 * @author Ben Sergent V/ha1fBit
 */
public class Vertex {

    private double x;
    private double y;
    private double z;

    public Vertex(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }
    
    @Override
    public Vertex clone() {
        return new Vertex(x,y,z);
    }
    
    @Override
    public String toString() {
        return x+","+y+","+z;
    }
    
    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getZ() {
        return z;
    }
    
    public Vertex setPos(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
        return this;
    }
    
    public Vertex setRelPos(double x, double y, double z) {
        this.x += x;
        this.y += y;
        this.z += z;
        return this;
    }
    
    public double distance(Vertex vert) {
        if (vert != null) {
        return Math.sqrt(
                ((vert.getX()-x)*(vert.getX()-x)) + 
                ((vert.getY()-y)*(vert.getY()-y)) + 
                ((vert.getZ()-z)*(vert.getZ()-z))
                );
        }
        return 0;
    }
    
    public double distanceSq(Vertex vert) {
        if (vert != null) {
            double dist1 = Math.abs(x-vert.getX());
            double dist2 = Math.abs(y-vert.getY());
            double dist3 = Math.abs(z-vert.getZ());
            return dist1+dist2+dist3;
        }
        return 0;
    }
    
}
