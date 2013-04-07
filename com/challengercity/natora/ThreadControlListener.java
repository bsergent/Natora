
package com.challengercity.natora;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;

/**
 *
 * @author Ben Sergent V/ha1fBit
 */
public class ThreadControlListener implements Runnable {

    final int TICKS_PER_SECOND = 60;
    final int SKIP_TICKS = 1000 / TICKS_PER_SECOND;
    final int MAX_FRAMESKIP = 5;
    private static int coolDown = 0;
    
    private static float speed = 0.05f; // Temp speed
    
    @Override
    public void run() {
        
        double next_game_tick = System.currentTimeMillis();
        int loops;
        
        try {
            Keyboard.create();
            Mouse.create();
        } catch (LWJGLException ex) {
            Logger.getLogger(ThreadControlListener.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        while (!Display.isCloseRequested()) {
            loops = 0;
            while (System.currentTimeMillis() > next_game_tick && loops < MAX_FRAMESKIP) {
                if (Keyboard.isKeyDown(Keyboard.KEY_ESCAPE) && coolDown <= 0) {
                    if (Natora.paused) {
                        Natora.paused = false;
                        Mouse.setGrabbed(true);
                    } else {
                        Natora.paused = true;
                        Mouse.setGrabbed(false);
                    }
                    coolDown = 10;
                }
                if (!Natora.paused) {
                    // Movement
                    if (Keyboard.isKeyDown(Keyboard.KEY_W)) {
                        Natora.player.setZVel(-speed);
                    }
                    if (Keyboard.isKeyDown(Keyboard.KEY_S)) {
                        Natora.player.setZVel(speed);
                    }
                    if (Keyboard.isKeyDown(Keyboard.KEY_A)) {
                        Natora.player.setXVel(-speed);
                    }
                    if (Keyboard.isKeyDown(Keyboard.KEY_D)) {
                        Natora.player.setXVel(speed);
                    }
                    if (Keyboard.isKeyDown(Keyboard.KEY_E)) {
                        Natora.player.setYVel(0.1f);
                    }
                    if (Keyboard.isKeyDown(Keyboard.KEY_Z)) {
                        Natora.world.setBlockAt(new Vertex(Natora.world.getRandom().nextInt(64)-32,Natora.world.getRandom().nextInt(64)-32,Natora.world.getRandom().nextInt(64)-32), null);
                    }
                    // View
                    Natora.player.setRelHeadRot(-Mouse.getDY(), Mouse.getDX());
                }
                if (Keyboard.isKeyDown(Keyboard.KEY_TAB) && coolDown <= 0) {
                    if (Natora.debug) {
                        Natora.debug = false;
                    } else {
                        Natora.debug = true;
                    }
                    coolDown = 10;
                }
                if (coolDown > 0) {coolDown--;}
                next_game_tick += SKIP_TICKS;
                loops++;
            }
        }
        
    }
    
}
