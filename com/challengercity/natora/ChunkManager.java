
package com.challengercity.natora;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
/**
 *
 * @author Ben Sergent V/ha1fBit
 */
public class ChunkManager {

    public final int CHUNKVIEWRANGE = 3;
    
    private HashMap<Long,Chunk> chunkList = new HashMap<Long,Chunk>(); // 2-dim chunk array
    private ArrayList<Location> chunkLoadList = new ArrayList<Location>();
    private ArrayList<Chunk> chunkSetupList = new ArrayList<Chunk>();
    private ArrayList<Chunk> chunkRebuildList = new ArrayList<Chunk>();
    private ArrayList<Chunk> chunkUnloadList = new ArrayList<Chunk>();
    private ArrayList<Chunk> chunkVisibilityList = new ArrayList<Chunk>();
    private ArrayList<Chunk> chunkRenderList = new ArrayList<Chunk>();
    
    private int chunkCamX = 0;
    private int chunkCamY = 0;
    private int chunkCamZ = 0;
    
    private boolean needVisibilityUpdate = true;
    private boolean needRenderUpdate = true;
    
    public void tick(Location cam) {

        updateLoadList();
        updateSetupList();
        updateRebuildList();
        updateUnloadList();
        if ((int)Math.floor(cam.getX()/Chunk.CHUNKSIZE) != chunkCamX || (int)Math.floor(cam.getY()/Chunk.CHUNKSIZE) != chunkCamY || (int)Math.floor(cam.getZ()/Chunk.CHUNKSIZE) != chunkCamZ) {
            needVisibilityUpdate = true;
            chunkCamX = (int)Math.floor(cam.getX()/Chunk.CHUNKSIZE);
            chunkCamY = (int)Math.floor(cam.getY()/Chunk.CHUNKSIZE);
            chunkCamZ = (int)Math.floor(cam.getZ()/Chunk.CHUNKSIZE);
        }
        if (needVisibilityUpdate) { // If you've moved across a chunk boundry
            updateVisibilityList(cam);
            needRenderUpdate = true;
        }
        updateRenderList();
        
    }
    
    private void updateLoadList() {
        if (!chunkLoadList.isEmpty()) {
            // TODO Attempt to load from file, generate on fail
            // TODO Force visibility update on load
            Location loc;
            for (int i = 0; i < chunkLoadList.size(); i++) {
                loc = chunkLoadList.get(i);
                //String key = (int)Math.floor(loc.getX()/Chunk.CHUNKSIZE)+","+(int)Math.floor(loc.getY()/Chunk.CHUNKSIZE)+","+(int)Math.floor(loc.getZ()/Chunk.CHUNKSIZE);
                long key = ((((long)Math.floor(loc.getX()/Chunk.CHUNKSIZE))<<42)&((long)0x1FFFFF<<42))|
                    ((((long)Math.floor(loc.getY()/Chunk.CHUNKSIZE))<<21)&((long)0x1FFFFF<<21))|
                    (((long)Math.floor(loc.getZ()/Chunk.CHUNKSIZE))&((long)0x1FFFFF));
                Chunk chunk = new Chunk((int)Math.floor(loc.getX()/Chunk.CHUNKSIZE),(int)Math.floor(loc.getY()/Chunk.CHUNKSIZE),(int)Math.floor(loc.getZ()/Chunk.CHUNKSIZE));
                chunkList.put(key, chunk);
            }
            chunkLoadList.clear();
            needVisibilityUpdate = true; // Will loop until loaded or generated
        }
    }
    
    private void updateSetupList() {
        
    }
    
    private void updateRebuildList() {
        
    }
    
    private void updateUnloadList() {
        
    }
    
    private void updateVisibilityList(Location cam) {
        // Check if player crossed chunk boundry before updating, then look for chunks in range
        chunkVisibilityList.clear();
        long key;
        for (int x = chunkCamX-CHUNKVIEWRANGE; x <= chunkCamX+CHUNKVIEWRANGE; x++) {
            for (int y = chunkCamY-CHUNKVIEWRANGE; y <= chunkCamY+CHUNKVIEWRANGE; y++) {
                for (int z = chunkCamZ-CHUNKVIEWRANGE; z <= chunkCamZ+CHUNKVIEWRANGE; z++) {
                    key = ((((long)x)<<42)&((long)0x1FFFFF<<42))|
                        ((((long)y)<<21)&((long)0x1FFFFF<<21))|
                        (((long)z)&((long)0x1FFFFF));
                    if (chunkList.get(key) != null) {
                        chunkVisibilityList.add(chunkList.get(key));
                        needRenderUpdate = true;
                    } else {
                        //chunkLoadList.add(new Location(x,y,z)); // Attempt to load the chunk, until then, show nothing
                    }
                }
            }
        } // TODO Check for chunks outside the range to unload
        needVisibilityUpdate = false;
    }
    
    private void updateRenderList() { // TODO THREADING! No better place to put this
        // Check around chunks if they are visible
        // Check if blocks have been changed before updating
        
        if (needRenderUpdate) {
            chunkRenderList.clear();
            Iterator<Chunk> itV = chunkVisibilityList.iterator(); // Check renderable chunks from visible chunks
            Chunk itVC;
            Vertex locV = new Vertex(0,0,0);
            while (itV.hasNext()) {
                itVC = itV.next();
                locV.setPos(itVC.xPos, itVC.yPos, itVC.zPos);
                // TODO Draw chunks in view with frustrum and occlusion culling and only draw chunks with a filled side using the isEmpty() method. Possibly apply the culling to the blocks (The chunks are so huge)
                if (true && itVC != null) { // If in view frustrum and not hidden
                    chunkRenderList.add(itVC);
                }
            }
            
            Iterator<Chunk> itR = chunkRenderList.iterator(); // Check renderable blocks in renderable chunks
            Chunk itRC;
            while (itR.hasNext()) {
                itR.next().calcRendBlks();
            }
            needRenderUpdate = false;
        }
    }
    
    public void forceRenderUpdate() {
        needRenderUpdate = true;
    }
    
    public void draw(Location cam) {
        for (int i = 0; i < chunkRenderList.size(); i++) {
            try {
                chunkRenderList.get(i).draw(cam);
            } catch (NullPointerException ex) {
                // Do nothing, a chunk was removed from the visibility list, but not the render list
            }
        }
    }
    
    private Chunk getChunk(Vertex vert) {
        //String key = ((int)Math.floor(loc.getX()/Chunk.CHUNKSIZE)+","+(int)Math.floor(loc.getY()/Chunk.CHUNKSIZE)+","+(int)Math.floor(loc.getZ()/Chunk.CHUNKSIZE));
        long key = ((((long)Math.floor(vert.getX()/Chunk.CHUNKSIZE))<<42)&((long)0x1FFFFF<<42))|
                ((((long)Math.floor(vert.getY()/Chunk.CHUNKSIZE))<<21)&((long)0x1FFFFF<<21))|
                (((long)Math.floor(vert.getZ()/Chunk.CHUNKSIZE))&((long)0x1FFFFF));
        
        if (chunkList.containsKey(key)) {
            return chunkList.get(key);
        } else {
            //chunkLoadList.add(loc);
            Chunk chunk = new Chunk((int)Math.floor(vert.getX()/Chunk.CHUNKSIZE),(int)Math.floor(vert.getY()/Chunk.CHUNKSIZE),(int)Math.floor(vert.getZ()/Chunk.CHUNKSIZE)); // Temp world gen
            chunkList.put(key, chunk);
            return chunk;
        }
        
    }
    
    public void setBlock(Vertex k, Block v) {
        if (getChunk(k) != null) { // Add to queue to set after load if null
            getChunk(k).setBlock(k, v);
        }
        needRenderUpdate = true;
    }
    
    public Block getBlock(Vertex k) {
        if (getChunk(k) != null) { // Add to queue to set after load if null
            return getChunk(k).getBlock(k);
        }
        return null;
    }
    
}
