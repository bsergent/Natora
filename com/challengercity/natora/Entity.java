package com.challengercity.natora;


/**
 *
 * @author Ben Sergent V/ha1fBit
 */
public class Entity {

    protected Location loc;
    private int saveId;
    private float zVelocity = 0.0f;
    private float xVelocity = 0.0f;
    private float yVelocity = 0.0f;

    public Entity(Location loc) {
        this.loc = loc;
    }
    
    public void draw() {
        
    }
    
    public void save(java.io.DataOutputStream dos) throws java.io.IOException {
        dos.writeInt(saveId);
        // TODO Save entity type id and its variables
    }
    
    public Location getLocation() {
        return loc;
    }
    
    public void setZVel(float zVel) {
        if (yVelocity == 0.0f) {
            zVelocity = zVel;
        }
    }
    
    public void setXVel(float xVel) {
        if (yVelocity == 0.0f) {
            xVelocity = xVel;
        }
    }
    
    public void setYVel(float yVel) {
        if (yVelocity == 0.0f) {
            yVelocity = yVel;
        }
    }

    public float getXVel() {
        return xVelocity;
    }

    public float getYVel() {
        return yVelocity;
    }

    public float getZVel() {
        return zVelocity;
    }
    
    public void tick(int delta) {
        Block chestBlock = Natora.world.getBlockAt(loc.clone().setRelPos(0, 0, 0));
        Block floorBlock = Natora.world.getBlockAt(loc.clone().setRelPos(0, -0.75, 0));
        float friction;
        if (floorBlock != null) {
            friction = floorBlock.getType().getFriction();
        } else {
            friction = 0;
        }
        int curDirs = 0;
        if (xVelocity != 0.0f) {
            curDirs++;
        }
        if (zVelocity != 0.0f) {
            curDirs++;
        }
        if (curDirs == 0) {
            curDirs = 1;
        }
        int lastDelta = 1;
        for (int i = 1; i <= delta; i++) {
            if (Natora.world.getBlockAt(loc.clone()) != null) {
                yVelocity = 0f;
            } else if (Natora.world.getBlockAt(loc.clone().setRelPos(0, i, 0)) != null) { // TODO Why use frame for downward velocity? Shouldn't it be yVelocity*delta
                yVelocity = -Natora.world.GRAVITY;
            } else if (Natora.world.getBlockAt(loc.clone().setRelPos(0, 0.25-i, 0)) == null) { // Problem with the blocks in the chunk's getBlock using floor
                if (yVelocity >= Natora.world.MAXFALLSPEED) {
                    yVelocity -= Natora.world.GRAVITY;
                }
            } else {
                lastDelta = i;
                if (yVelocity < 0.0f) {
                    yVelocity = 0;
                }
                break;
            }
            // Do collison detection
            // stop and move location on first hit
        }
        if (zVelocity > 0.0f) { // TODO Horizontal physics
            zVelocity = zVelocity < friction?0f:zVelocity-friction;
        } else if (zVelocity < 0.0f) {
            zVelocity = zVelocity > friction?0f:zVelocity+friction;
        }
        if (xVelocity > 0.0f) {
            xVelocity = xVelocity < friction?0f:xVelocity-friction;
        } else if (xVelocity < 0.0f) {
            xVelocity = xVelocity > friction?0f:xVelocity+friction;
        }
        if (Natora.world.getBlockAt(new Vertex( // TODO Doesn't ever collide
                (zVelocity/curDirs) * Math.cos(Math.toRadians(loc.getYaw() + 90)) + (xVelocity/curDirs) * Math.cos(Math.toRadians(loc.getYaw())),
                0,
                (zVelocity/curDirs) * Math.sin(Math.toRadians(loc.getYaw() + 90)) + xVelocity * Math.sin(Math.toRadians(loc.getYaw()))
                )) == null) {
            loc.setRelPos((zVelocity/curDirs) * Math.cos(Math.toRadians(loc.getYaw() + 90)),0,(zVelocity/curDirs) * Math.sin(Math.toRadians(loc.getYaw() + 90)));
            loc.setRelPos((xVelocity/curDirs) * Math.cos(Math.toRadians(loc.getYaw())),0,xVelocity * Math.sin(Math.toRadians(loc.getYaw())));
        }
        loc.setRelPos(0, yVelocity*lastDelta, 0);
    }
    
}
