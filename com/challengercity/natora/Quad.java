
package com.challengercity.natora;

/**
 *
 * @author Ben Sergent V/ha1fBit
 */
public class Quad {

    private Vertex v1;
    private Vertex v2;
    private Vertex v3;
    private Vertex v4;
    private Vertex normal;
    private BlockType type;

    public Quad(Vertex v1, Vertex v2, Vertex v3, Vertex v4, Vertex normal, BlockType type) {
        this.v1 = v1;
        this.v2 = v2;
        this.v3 = v3;
        this.v4 = v4;
        this.normal = normal;
        this.type = type;
    }
    
    @Override
    public String toString() {
        return v1+"|"+v2+"|"+v3+"|"+v4;
    }

    public Vertex getNormal() {
        return normal;
    }

    public BlockType getType() {
        return type;
    }

    public Vertex getV1() {
        return v1;
    }

    public Vertex getV2() {
        return v2;
    }

    public Vertex getV3() {
        return v3;
    }

    public Vertex getV4() {
        return v4;
    }

    public Triangle[] convertToTriangles() {
        Triangle[] tris = new Triangle[2];
        tris[0] = new Triangle(v1,v2,v3,normal,type);
        tris[1] = new Triangle(v1,v4,v3,normal,type);
        return tris;
    }
    
}
