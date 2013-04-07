
package com.challengercity.natora;

import java.util.ArrayList;
import java.util.Random;
import static org.lwjgl.opengl.GL11.*;

/**
 *
 * @author Ben Sergent V/ha1fBit
 */
public class World {

    public final float GRAVITY = 0.005f;
    public final float MAXFALLSPEED = -0.5f;
    private Random rand;
    private long seed;
    private float[] fogColor = new float[4];
    private ArrayList<Player> players = new ArrayList<Player>(); // TODO Save players separately on log on and log off
    private ArrayList<Entity> entities = new ArrayList<Entity>(); // TODO Move into chunks?
    private ChunkManager cm = new ChunkManager();
    
    public World() {
        Random r = new Random();
        seed = r.nextLong();
        rand = new Random(seed);
    }
    
    public World(long seed) {
        rand = new Random(seed);
        this.seed = seed;
    }
    
    public Random getRandom() {
        return rand;
    }
    
    public Long getSeed() {
        return seed;
    }
    
    public Block getBlockAt(Vertex vert) {
        return cm.getBlock(vert);
    }
    
    public void setBlockAt(Vertex vert, Block blk) {
        cm.setBlock(vert, blk);
    }
    
    public void draw(Location cam) {
        
        if (Natora.debug) {
            glLineWidth(5.0f); 
            glBegin(GL_LINES);
            {
                glColor3f(1.0f, 0.0f, 0.0f);
                glVertex3i(0,0,0);
                glVertex3i(50,0,0);
                
                glColor3f(0.0f, 1.0f, 0.0f);
                glVertex3i(0,0,0);
                glVertex3i(0,50,0);
                
                glColor3f(0.0f, 0.0f, 1.0f);
                glVertex3i(0,0,0);
                glVertex3i(0,0,50);
            }
            glEnd();
        }
        
        cm.draw(cam);
    }
    
    public void tick(Location cam, int delta) {
        for (int e = 0; e < entities.size(); e++) {
            entities.get(e).tick(delta);
        }
        for (int p = 0; p < players.size(); p++) {
            players.get(p).tick(delta);
        }
        cm.tick(cam);
    }
    
    public void addEntity(Entity e) {
        entities.add(e);
    }
    
    public void addPlayer(Player p) {
        players.add(p);
    }
    
    public void save(java.io.DataOutputStream dos) throws java.io.IOException {
        // TODO Use streamable objects
    }
    
    public void load(java.io.DataInputStream dis) throws java.io.IOException {
        // Use Streamable Objects
    }
    
}
