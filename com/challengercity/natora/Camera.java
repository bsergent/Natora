package com.challengercity.natora;

import java.awt.Font;
import org.lwjgl.opengl.Display;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.util.glu.GLU.gluPerspective;
import org.newdawn.slick.TrueTypeFont;
import java.text.*;
/**
 *
 * @author Ben Sergent V/ha1fBit
 */
public final class Camera {

    private Location loc;
    
    private float fov, aspect;
    private float near, far;
    
    private static TrueTypeFont font;
    private static org.newdawn.slick.Color color = new org.newdawn.slick.Color(0.00f, 1.00f, 0.00f);
    private static DecimalFormat locFormat = new DecimalFormat("###.##");
    
    public static boolean attachedToPlayer = true;
    
    public static int faceCount;
    
    private static Screen screen = new ScreenGame();

    public Camera(float fov, float aspect, float near, float far) {
        
        loc = Natora.player.getLocation();
        
        this.fov = fov;
        this.aspect = aspect;
        this.near = near;
        this.far = far;
        
        glEnable(GL_TEXTURE_2D);
        glBlendFunc (GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
    }
    
    public void useView() {
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
        glLoadIdentity();
        
        if (attachedToPlayer) {
            loc = Natora.player.getViewLocation();
        }
        
        if (screen.isWorldDrawn()) {
            draw3d();
        }
        draw2d();

        Display.update();
        //Display.sync(60);
    }
    
    private void prep3d() {
        glViewport(0, 0, Display.getWidth(), Display.getHeight());
        glMatrixMode(GL_PROJECTION);
        glLoadIdentity();
        gluPerspective(fov, aspect, near, Natora.debug?100:far);
        glMatrixMode(GL_MODELVIEW);
        glLoadIdentity();
        glEnable(GL_DEPTH_TEST);
        if (!Natora.debug) {
            glEnable(GL_FOG);
            glFogi(GL_FOG_MODE, GL_LINEAR);
            glFogf(GL_FOG_START, 40.0f);
            glFogf(GL_FOG_END, 48.0f);
        }
        glDisable(GL_BLEND);
        faceCount = 0;
        
        if (Natora.debug) {
            glPolygonMode(GL_FRONT_AND_BACK, GL_LINE);
        } else {
            glPolygonMode(GL_FRONT_AND_BACK, GL_FILL);
        }
        
        // TODO Lighting
        if (!Natora.debug) {
            glEnable(GL_LIGHTING);
            glEnable(GL_LIGHT0);
            glEnable(GL_COLOR_MATERIAL);
        }
        
        float lightAmbient[] =  { 0.0f, 0.0f, 0.0f, 1.0f }; // Ambient Light Values
        float lightDiffuse[] =  { 1.0f, 1.0f, 1.0f, 1.0f }; // Diffuse Light Values
        float lightPosition[] = { 0.0f, 0.0f, 0.0f, 1.0f }; // Light Position
        float lightSpecular[] = { 1.0f, 1.0f, 1.0f, 1.0f }; // Light Position

        java.nio.ByteBuffer temp = java.nio.ByteBuffer.allocateDirect(16);
        temp.order(java.nio.ByteOrder.nativeOrder());
        glLight(GL_LIGHT0, GL_SPECULAR, (java.nio.FloatBuffer)temp.asFloatBuffer().put(lightSpecular).flip());
        glLight(GL_LIGHT0, GL_AMBIENT, (java.nio.FloatBuffer)temp.asFloatBuffer().put(lightAmbient).flip());
        glLight(GL_LIGHT0, GL_DIFFUSE, (java.nio.FloatBuffer)temp.asFloatBuffer().put(lightDiffuse).flip());
        glLight(GL_LIGHT0, GL_POSITION,(java.nio.FloatBuffer)temp.asFloatBuffer().put(lightPosition).flip());
        
        glRotatef(loc.getPitch(),1,0,0);
        glRotatef(loc.getYaw(),0,1,0);
        double[] pos = loc.getPos();
        glTranslated(-pos[0],-pos[1],-pos[2]);
    }
    
    private void prep2d() {
        glMatrixMode(GL_PROJECTION);
        glLoadIdentity();
        glOrtho(0, Display.getWidth(), Display.getHeight(), 0, 1, -1);
        glMatrixMode(GL_MODELVIEW);
        glLoadIdentity();
        glDisable(GL_FOG);
        glDisable(GL_DEPTH_TEST);
        glEnable(GL_BLEND);
        
        glPolygonMode(GL_FRONT_AND_BACK, GL_FILL);
        
        glDisable(GL_LIGHTING);
        glDisable(GL_LIGHT0);
        glDisable(GL_COLOR_MATERIAL);
    }
    
    private void draw3d() {
        prep3d();
        glPushMatrix();
        {
            //ResourcePool.getTexture(BlockType.STONE.getTextureKey()).bind();
            glLineWidth(1.0f); 
            Natora.world.draw(loc.clone());
        }
        glPopMatrix();
    }
    
    private void draw2d() {
        prep2d();
        glDisable(GL_TEXTURE_2D);
        glBegin(GL_QUADS);
        {
                glColor3f(1f,1f,1f);
                glTexCoord2f(0,0); glVertex2i(0, 0);
                glTexCoord2f(0,1); glVertex2i(0, 91);
                glTexCoord2f(1,1); glVertex2i(200, 91);
                glTexCoord2f(1,0); glVertex2i(200, 0);
                
                glColor3f(0f,0f,0f);
                glVertex2i(5, 5);
                glVertex2i(5, 86);
                glVertex2i(195, 86);
                glVertex2i(195, 5);
        }
        glEnd();
        glEnable(GL_TEXTURE_2D);
        
        if (font == null) {
            font = new TrueTypeFont(new Font("Courier new", Font.PLAIN, 15),true);
        }
        font.drawString(5, 4, "FPS="+Natora.currentFPS, color);
        font.drawString(5, 19, "Loc="+locFormat.format(loc.getX())+","+locFormat.format(loc.getY())+","+locFormat.format(loc.getZ()), color);
        font.drawString(5, 34, "Dir="+locFormat.format(loc.getPitch())+","+locFormat.format(loc.getYaw()), color);
        font.drawString(5, 49, "Vel="+Natora.player.getXVel()+","+Natora.player.getYVel()+","+Natora.player.getZVel(), color);
        font.drawString(5, 64, "Faces="+faceCount, color);
        
        if (Natora.paused) {
            int strPosX = Display.getWidth()/2+-(font.getWidth("Paused")/2);
            int strPosY = Display.getHeight()/2+-(font.getHeight("Paused")/2);
            font.drawString(strPosX, strPosY, "Paused", color);
        }
        
        screen.draw();
        
    }
    
    public void setLocation(Location loc) {
        this.loc = loc;
    }
    
    public Location getLocation() {
        return loc;
    }
    
}
