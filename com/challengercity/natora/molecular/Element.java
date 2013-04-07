/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.challengercity.natora.molecular;

/**
 *
 * @author Ben Sergent V/ha1fBit
 */
public enum Element {

    CAH("Cah - Dark Matter", 0, 0f, 0f, 0f, 0.9f, -0.1f, 0f, 0f, 0, 0.95f, 500, 100, 0),
    DAH("Dah - Firelike", 1, 0.7f, 0.2f, 0f, 0.5f, 0.01f, 0f, 0f, 10, 0.2f, 0, -100, 1),
    DEY("Dey - Waterlike", 2, 0f, 0.1f, 0.9f, 0.8f, 0.5f, 0f, 0.5f, 0, 0.90f, 100, 0, 0),
    FEY("Fey - Earthlike", 3, 0.35f, 0.28f, 0.2f, 0f, 0.95f, 0f, 0f, 0, 0.92f, 250, -50, 0),
    FIL("Fil - Airlike", 4, 0.8f, 0.8f, 0.85f, 0.95f, 0.05f, 0f, 0f, 0, 0.7f, -50, -128, 0);
//    MARE(5, ),
//    MIZE(6 ,),
//    PHY(7 ,),
//    QUE(8, ),
//    RHI(9 ,),
//    SEY(10 ,),
//    TAH(11 ,),
//    TEY(12, ),
//    WARE(13, ),
//    ZOH(14, );
    
    private String name;
    private int id;
    private float red,green,blue,transparency,density,sharpness,conductivity;
    private int lumosity;
    private float heatResis;
    private int boilingPoint,meltingPoint;
    private int charge;

    private Element(String name, int id, float red, float green, float blue, float transperancy, float density, float sharpness, float conductivity, int lumonosity, float heatResis, int boilingPoint, int meltingPoint, int charge) {
        this.name = name;
        this.id = id;
        this.red = red;
        this.green = green;
        this.blue = blue;
        this.transparency = transperancy;
        this.density = density;
        this.sharpness = sharpness;
        this.conductivity = conductivity;
        this.lumosity = lumonosity;
        this.heatResis = heatResis;
        this.boilingPoint = boilingPoint;
        this.meltingPoint = meltingPoint;
        this.charge = charge;
    }
    
    @Override
    public String toString() {
        return name;
    }
    
    public int getId() {
        return id;
    }
    
    public int getBoilingPoint() {
        return boilingPoint;
    }

    public int getCharge() {
        return charge;
    }

    public float getConductivity() {
        return conductivity;
    }

    public float getDensity() {
        return density;
    }

    public float getHeatResis() {
        return heatResis;
    }

    public int getLumosity() {
        return lumosity;
    }

    public int getMeltingPoint() {
        return meltingPoint;
    }

    public float getSharpness() {
        return sharpness;
    }

    public float getTransparency() {
        return transparency;
    }
    
    public float[] getColor() {
        float[] colorArray = new float[3];
        colorArray[0] = red;
        colorArray[1] = green;
        colorArray[2] = blue;
        return colorArray;
    }
    
}
