
package com.challengercity.natora;

import static org.lwjgl.opengl.GL11.*;
/**
 *
 * @author Ben Sergent V/ha1fBit
 */
public class Block {
    
    protected short hitpoints;
    private BlockType type;

    public Block(BlockType type) {
        this.type = type;
        hitpoints = type.getMaxHp();
    }
    
    public void breakBlockNaturally(Vertex vert) {
        breakBlock(vert);
        dropItems(vert);
    }
    
    public void breakBlock(Vertex vert) {
        Natora.world.setBlockAt(vert, null);
    }
    
    public void save(java.io.DataOutputStream dos) throws java.io.IOException {
        
    }
    
    public void hitBlock(Vertex vert, int damage) {
        if (type.isBreakable() && hitpoints - damage <= 0) {
            breakBlock(vert);
        } else {
            hitpoints--;
        }
    }
    
    public void dropItems(Vertex vert) {
        // Spawn items on ground using the block type's drops
    }
    
    public BlockType getType() {
        return type;
    }
    
}
