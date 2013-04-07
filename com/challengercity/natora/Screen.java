
package com.challengercity.natora;

/**
 *
 * @author Ben Sergent V/ha1fBit
 */
public class Screen {

    private java.util.ArrayList<GUI> guiEle = new java.util.ArrayList<GUI>();
    private boolean showWorld;

    public Screen(boolean showWorld) {
        this.showWorld = showWorld;
    }
    
    public boolean isWorldDrawn() {
        return showWorld;
    }
    
    public Screen addGUIEle(GUI gui) {
        guiEle.add(gui);
        return this;
    }
    
    public void draw() {
        for (int i = 0; i < guiEle.size(); i++) {
            guiEle.get(i).draw();
        }
    }
    
}
