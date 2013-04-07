package com.challengercity.natora;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import org.lwjgl.BufferUtils;
import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import static org.lwjgl.opengl.GL11.*;

/**
 *
 * @author Sgrt.
 */
public class Natora {

    public final static String VERSION = "0.0.6 Alpha";
    private static String username = "";
    //public static Player player = new Player(new Location(9.6f, 6.8f, 8.01f, 27f, 307f));
    public static Player player = new Player(new Location(3.0f, -3.0f, 3.0f, 0.0f, 0.0f));
    protected static World world;
    public static boolean paused = false; // Temp pause
    
    private static long lastFPS = getTime(); // Last time
    private static int fps; // FPS Counter
    public static int currentFPS; // This time
    public static Camera cam;
    
    public static boolean debug = false;
    
    private static Thread tCon;
    private static Thread tNet;
    private static Thread tRend;
    private static Thread tTick;

    public static void main(String[] args) {

        username = args.length>0?args[0]:"Player";
        System.out.println("[NT] Started as "+username);
        
        initDisplay();
        startGame();
        cleanUp();

    }

    private static void initDisplay() {
        
        try {
            setDisplayMode(1280, 720, false);
            Display.create();
            Display.setTitle("Natora "+Natora.VERSION);
            //Display.setIcon(icons);
            
            ByteBuffer[] list = new ByteBuffer[3];
            list[0] = createBuffer(ImageIO.read(Natora.class.getResource("/com/challengercity/natora/resources/icon16.png")), false);
            list[1] = createBuffer(ImageIO.read(Natora.class.getResource("/com/challengercity/natora/resources/icon32.png")), false);
            list[2] = createBuffer(ImageIO.read(Natora.class.getResource("/com/challengercity/natora/resources/icon128.png")), false);
            Display.setIcon(list);
            
            cam = new Camera(70,(float)Display.getWidth()/(float)Display.getHeight(),0.3f,50);
            
            Mouse.setGrabbed(true);
            System.out.println("[NT] Display created");
        } catch (Exception ex) {
            Logger.getLogger(Natora.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    
    public static void updateFPS() {
        if (getTime() - lastFPS > 1000) { 
            currentFPS=fps;
            fps = 0; //reset the FPS counter
            lastFPS += 1000; //add one second
        }
        fps++;
    }
    
    public static long getTime() {
        return (Sys.getTime() * 1000) / Sys.getTimerResolution();
    }
    
    private static ByteBuffer createBuffer(BufferedImage bi, boolean alpha) {
        int[] pixels = new int[bi.getWidth() * bi.getHeight()];
        bi.getRGB(0, 0, bi.getWidth(), bi.getHeight(), pixels, 0, bi.getWidth());
        ByteBuffer bb = BufferUtils.createByteBuffer(bi.getWidth()*bi.getHeight()*(alpha?4:3)); // 4 for RGBA, 3 for RGB
         for(int y = 0; y < bi.getHeight(); y++){
            for(int x = 0; x < bi.getWidth(); x++){
                int pixel = pixels[y * bi.getWidth() + x];
                bb.put((byte) ((pixel >> 16) & 0xFF));     // Red component
                bb.put((byte) ((pixel >> 8) & 0xFF));      // Green component
                bb.put((byte) (pixel & 0xFF));               // Blue component
                if (alpha) {
                    bb.put((byte) ((pixel >> 24) & 0xFF));    // Alpha component. Only for RGBA
                }
            }
        }

        bb.flip();
        return bb;
    }
    
    private static String defaultDirectory() {
        String OS = System.getProperty("os.name").toUpperCase();
        if (OS.contains("WIN"))
            return System.getenv("APPDATA");
        else if (OS.contains("MAC"))
            return System.getProperty("user.home") + "/Library/Application "
                    + "Support";
        else if (OS.contains("NUX"))
            return "$HOME/.config";
        return System.getProperty("user.dir");
    }

    private static void startGame() {
        
        world = new World();
        world.addPlayer(player);
        
        /* Large cave */
        for (int x = -80; x < 80; x++) { // Fill the world
        for (int y = -80; y < 80; y++)
        for (int z = -80; z < 80; z++) // TODO Some World Generation
            //if (world.getRandom().nextFloat() < 0.2f) {
                world.setBlockAt(new Vertex(x,y,z), new Block(BlockType.STONE));
            //}
        }
        for (int x = -6; x <= 5; x++) { // Fill the world
        for (int y = -6; y <= 5; y++)
        for (int z = -6; z <= 5; z++) // TODO Some World Generation
            //if (world.getRandom().nextFloat() < 0.2f) {
                world.setBlockAt(new Vertex(x,y,z), null);
            //}
        }
        for (int z = -50; z <= 50; z++) { // TODO Some World Generation
            //if (world.getRandom().nextFloat() < 0.2f) {
                world.setBlockAt(new Vertex(0,0,z), null);
            //}
        }
        for (int x = -50; x <= 50; x++) { // TODO Some World Generation
            //if (world.getRandom().nextFloat() < 0.2f) {
                world.setBlockAt(new Vertex(x,0,0), null);
                world.setBlockAt(new Vertex(x,-1,0), null);
                world.setBlockAt(new Vertex(x,-1,-1), null);
                world.setBlockAt(new Vertex(x,0,-1), null);
            //}
        }
        world.setBlockAt(new Vertex(4,5,0), new Block(BlockType.STONE));
        world.setBlockAt(new Vertex(4,4,0), new Block(BlockType.STONE));
        
        world.setBlockAt(new Vertex(3,5,0), new Block(BlockType.STONE));
        
        world.setBlockAt(new Vertex(-2,5,3), new Block(BlockType.STONE));
        world.setBlockAt(new Vertex(-2,4,3), new Block(BlockType.STONE));
        
        world.setBlockAt(new Vertex(5,-6,5), new Block(BlockType.STONE));
        world.setBlockAt(new Vertex(5,-6,4), new Block(BlockType.STONE));
        
        for (int x = -6; x <= -1; x++) { // Fill the world
        for (int z = -6; z <= -1; z++) // TODO Some World Generation
            //if (world.getRandom().nextFloat() < 0.2f) {
                world.setBlockAt(new Vertex(x,-7,z), new Block(BlockType.DIRT));
            //}
        }
        
        
        /* Polygon Testing */
//        for (int x = 2; x < 5; x++) { // Fill the world
//        for (int y = 2; y < 5; y++)
//        for (int z = 2; z < 5; z++) // TODO Some World Generation
//            world.setBlockAt(new Vertex(x,y,z), new Block(BlockType.STONE));
//        }
//        world.setBlockAt(new Vertex(4,3,4), null);
//        world.setBlockAt(new Vertex(3,3,3), null);
//        world.setBlockAt(new Vertex(4,2,5), new Block(BlockType.STONE));
//        
//        world.setBlockAt(new Vertex(1,3,7), new Block(BlockType.STONE));
//        world.setBlockAt(new Vertex(1,3,6), new Block(BlockType.STONE));
//        world.setBlockAt(new Vertex(1,4,7), new Block(BlockType.STONE));
//        
//        world.setBlockAt(new Vertex(7,3,2), new Block(BlockType.STONE));
//        world.setBlockAt(new Vertex(7,3,3), new Block(BlockType.STONE));
//        world.setBlockAt(new Vertex(7,3,4), new Block(BlockType.STONE));
//        world.setBlockAt(new Vertex(7,4,2), new Block(BlockType.DIRT));
//        world.setBlockAt(new Vertex(7,4,3), new Block(BlockType.DIRT));
//        world.setBlockAt(new Vertex(7,4,4), new Block(BlockType.DIRT));
//        world.setBlockAt(new Vertex(7,5,2), new Block(BlockType.STONE));
//        world.setBlockAt(new Vertex(7,5,3), new Block(BlockType.DIRT));
//        
//        world.setBlockAt(new Vertex(6,3,4), new Block(BlockType.STONE));
//        
//        world.setBlockAt(new Vertex(4,9,4), new Block(BlockType.STONE));
//        world.setBlockAt(new Vertex(4,9,5), new Block(BlockType.STONE));
//        world.setBlockAt(new Vertex(4,9,6), new Block(BlockType.STONE));
//        
//        world.setBlockAt(new Vertex(4,7,4), new Block(BlockType.STONE));
//        world.setBlockAt(new Vertex(4,7,6), new Block(BlockType.STONE));
//        world.setBlockAt(new Vertex(4,8,4), new Block(BlockType.STONE));
//        world.setBlockAt(new Vertex(4,8,6), new Block(BlockType.STONE));
//        
//        world.setBlockAt(new Vertex(4,6,4), new Block(BlockType.STONE));
//        world.setBlockAt(new Vertex(4,6,5), new Block(BlockType.STONE));
//        world.setBlockAt(new Vertex(4,6,6), new Block(BlockType.STONE));
        
        
        
        
        tTick = new Thread(new ThreadWorldTick(),"World Tick");
        tCon = new Thread(new ThreadControlListener(),"ControlListener");
        tNet = new Thread(new ThreadNetworking(),"Networking");
        tTick.start();
        tCon.start();
        tNet.start();
        
        while (!Display.isCloseRequested()) {
            updateFPS();
            cam.useView();
        }
        Display.destroy();
        System.exit(0);
        
    }

    private static void cleanUp() {
        Display.destroy();
        System.out.println("[NT] Closing");
        System.exit(0);
    }

    public static void setDisplayMode(int width, int height, boolean fullscreen) {

        if ((Display.getDisplayMode().getWidth() == width)
                && (Display.getDisplayMode().getHeight() == height)
                && (Display.isFullscreen() == fullscreen)) {
            return;
        }

        try {
            DisplayMode targetDisplayMode = null;

            if (fullscreen) {
                DisplayMode[] modes = Display.getAvailableDisplayModes();
                int freq = 0;

                for (int i = 0; i < modes.length; i++) {
                    DisplayMode current = modes[i];

                    if ((current.getWidth() == width) && (current.getHeight() == height)) {
                        if ((targetDisplayMode == null) || (current.getFrequency() >= freq)) {
                            if ((targetDisplayMode == null) || (current.getBitsPerPixel() > targetDisplayMode.getBitsPerPixel())) {
                                targetDisplayMode = current;
                                freq = targetDisplayMode.getFrequency();
                            }
                        }

                        if ((current.getBitsPerPixel() == Display.getDesktopDisplayMode().getBitsPerPixel())
                                && (current.getFrequency() == Display.getDesktopDisplayMode().getFrequency())) {
                            targetDisplayMode = current;
                            break;
                        }
                    }
                }
            } else {
                targetDisplayMode = new DisplayMode(width, height);
            }

            if (targetDisplayMode == null) {
                System.out.println("Failed to find value mode: " + width + "x" + height + " fs=" + fullscreen);
                return;
            }

            Display.setDisplayMode(targetDisplayMode);
            Display.setFullscreen(fullscreen);

        } catch (LWJGLException e) {
            System.out.println("Unable to setup mode " + width + "x" + height + " fullscreen=" + fullscreen + e);
        }
    }

    public static String getUsername() {
        return username;
    }
}