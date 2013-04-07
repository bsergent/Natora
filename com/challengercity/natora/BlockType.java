
package com.challengercity.natora;

/**
 *
 * @author Ben Sergent V/ha1fBit
 */
public enum BlockType {

    STONE("Stone","stone128",0,100,new Drops(),0),
    DIRT("Dirt","dirt",1,20,new Drops(),0.0005f),
    BARRIER("Barrier","barrier",2,-1,new Drops(),0); // TODO Slope block with direction meta-databased on surrounding blocks when recalc'd on chunk modification
    
    private String name,textureKey;
    private int saveId,lumonosity,absorbtion; // TODO Liquid absorbtion
    private short maxHp;
    private Drops drops;
    private float friction;

    private BlockType(String name, String textureKey, int saveId, int maxHp, Drops drops, float friction) {
        this.name = name;
        this.textureKey = textureKey;
        this.saveId = saveId;
        this.drops = drops;
        this.maxHp = (short) maxHp;
        this.friction = friction;
        if (friction == 0) {
            this.friction = 0.005f;
        }
    }
    
    
    /* Start Public Methods */
    public short getMaxHp() {
        return maxHp;
    }
    public Drops getDrops() {
        return drops;
    }
    public String getName() {
        return name;
    }
    public int getSaveId() {
        return saveId;
    }
    public String getTextureKey() {
        return textureKey;
    }
    public boolean isBreakable() {
        return maxHp>0;
    }

    public float getFriction() {
        return friction;
    }
    
}
