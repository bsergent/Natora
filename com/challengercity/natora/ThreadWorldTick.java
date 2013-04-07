
package com.challengercity.natora;

import org.lwjgl.opengl.Display;

/**
 *
 * @author Ben Sergent V/ha1fBit
 */
public class ThreadWorldTick implements Runnable {

    final int TICKS_PER_SECOND = 60;
    final int SKIP_TICKS = 1000 / TICKS_PER_SECOND;
    final int MAX_FRAMESKIP = 5;

    @Override
    public void run() {
        double next_game_tick = System.currentTimeMillis();
        int loops;
        long lastFrame = Natora.getTime();
        int delta;

        while (true) {
            if (!Natora.paused) {
                loops = 0;
                while (System.currentTimeMillis() > next_game_tick && loops < MAX_FRAMESKIP) {
                    long time = Natora.getTime();
                    delta = (int) (time - lastFrame)/TICKS_PER_SECOND;
                    //System.out.println("[WorldTick] Time passed:"+(time-lastFrame));
                    //System.out.println("[WorldTick] Delta:"+delta);
                    lastFrame = time;
                    if (delta > 60) {
                        delta = 60;
                    }

                    Natora.world.tick(Natora.cam.getLocation(),1);

                    next_game_tick += SKIP_TICKS;
                    loops++;
                }


            }
        }
    }
    
}
