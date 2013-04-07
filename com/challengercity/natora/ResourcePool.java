
package com.challengercity.natora;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;

/**
 *
 * @author Ben Sergent V/ha1fBit
 */
public class ResourcePool {

    private static File resDir;
    private static HashMap texturePool = new HashMap<String, Texture>();
    //private static HashMap soundPool = new HashMap<String, Sound>();
    
    public static void loadAll() throws IOException {

        if (resDir == null) {
            throw new java.io.IOException("Resource directoy not set");
        }
        // Use at startup so game doesn't temp freeze whenever new texture is introduced
        // getTexture("test");
        // getSound("step");
        
    }
    
    public static void setResDir(File resDir) {
        ResourcePool.resDir = resDir;
    }
    
    public static File getResDir() {
        return resDir;
    }
    
    public static Texture getTexture(String key) {
        
        if (!texturePool.containsKey(key)) {
            try {
                return TextureLoader.getTexture("png", Natora.class.getResourceAsStream("/com/challengercity/natora/resources/"+key+".png"));
            } catch (IOException ex) {
                Logger.getLogger(ResourcePool.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            return (Texture) texturePool.get(key);
        }
        return null;
    }
    
}
