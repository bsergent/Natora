
package com.challengercity.natora;

/**
 *
 * @author Ben Sergent V/ha1fBit
 */
public class Triangle {

    private Vertex v1;
    private Vertex v2;
    private Vertex v3;
    private Vertex normal;
    private BlockType type;

    public Triangle(Vertex v1, Vertex v2, Vertex v3, Vertex normal, BlockType type) {
        this.v1 = v1;
        this.v2 = v2;
        this.v3 = v3;
        this.normal = normal;
        this.type = type;
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
    
}
