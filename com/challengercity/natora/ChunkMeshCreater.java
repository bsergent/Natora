
package com.challengercity.natora;

import java.util.ArrayList;
/**
 *
 * @author Ben Sergent V/ha1fBit
 */
public class ChunkMeshCreater {

    private static ArrayList<Triangle> currentMesh;
    private static Chunk curChunk;
    private static ArrayList<Vertex> poly1 = new ArrayList<Vertex>();
    private static ArrayList<Vertex> poly2 = new ArrayList<Vertex>();
    private static BlockType subSliceType = null;
    
    public static ArrayList<Triangle> calculateMesh(Chunk chunk) {
        
        currentMesh = new ArrayList<Triangle>();
        curChunk = chunk;
        double[] sides;

        //            0,2 ______2______ 1,2
        //               |          __/|
        //               |       __/   |
        //              0|    __/      |1
        //               | __/         |
        //            0,3|/____________|1,3
        //                      3
        
        for (int x = curChunk.xPos*Chunk.CHUNKSIZE; x < (curChunk.xPos+1)*Chunk.CHUNKSIZE; x++) { // Split by x
            for (int y = curChunk.yPos*Chunk.CHUNKSIZE; y < (curChunk.yPos+1)*Chunk.CHUNKSIZE; y++) { // Work with y and z
                for (int z = curChunk.zPos*Chunk.CHUNKSIZE; z < (curChunk.zPos+1)*Chunk.CHUNKSIZE; z++) {
                    if (subSliceType == null && Natora.world.getBlockAt(new Vertex(x,y,z)) != null) {
                        subSliceType = Natora.world.getBlockAt(new Vertex(x,y,z)).getType();
                        System.out.println("[ChunkMeshCreation] SubSliceType changed to "+subSliceType);
                    }
                    if (Natora.world.getBlockAt(new Vertex(x,y,z)) == null || Natora.world.getBlockAt(new Vertex(x,y,z)).getType() != subSliceType) {
                        // Flush both polygons, because it's an air block or not the same type
                        if (!poly1.isEmpty() && subSliceType!=null) {
                            sides = new double[4]; // [L,R,T,B]
                            sides[0] = poly1.get(0).getZ();
                            sides[1] = poly1.get(0).getZ()+1;
                            sides[2] = poly1.get(0).getY()+1;
                            sides[3] = poly1.get(0).getY();
                            for (int i = 1; i < poly1.size(); i++) {
                                if (poly1.get(i).getZ() < sides[0]) { // If farther left(-z)
                                    sides[0] = poly1.get(i).getZ();
                                }
                                if (poly1.get(i).getZ()+1 > sides[1]) { // If farther right(+z)
                                    sides[1] = poly1.get(i).getZ()+1;
                                }
                                if (poly1.get(i).getY() < sides[3]) { // If farther down(-y)
                                    sides[3] = poly1.get(i).getY();
                                }
                                if (poly1.get(i).getY()+1 > sides[2]) { // If farther up(+y)
                                    sides[2] = poly1.get(i).getY()+1;
                                }
                            }
                            System.out.println("[ChunkMeshCreation] Flushed quad "+new Quad(new Vertex(x,sides[3],sides[0]),new Vertex(x,sides[2],sides[0]),new Vertex(x,sides[2],sides[1]),new Vertex(x,sides[3],sides[1]),new Vertex(1,0,0),subSliceType));
                            flushQuad(new Quad(new Vertex(x,sides[3],sides[0]),new Vertex(x,sides[2],sides[0]),new Vertex(x,sides[2],sides[1]),new Vertex(x,sides[3],sides[1]),new Vertex(1,0,0),subSliceType)); // Flush face/polygon 1
                            poly1.clear();
                        }
                        
                        if (!poly2.isEmpty() && subSliceType!=null) {
                            sides = new double[4]; // [L,R,T,B]
                            sides[0] = poly2.get(0).getZ();
                            sides[1] = poly2.get(0).getZ()+1;
                            sides[2] = poly2.get(0).getY()+1;
                            sides[3] = poly2.get(0).getY();
                            for (int i = 1; i < poly2.size(); i++) {
                                if (poly2.get(i).getZ() < sides[0]) { // If farther left(-z)
                                    sides[0] = poly2.get(i).getZ();
                                }
                                if (poly2.get(i).getZ()+1 > sides[1]) { // If farther right(+z)
                                    sides[1] = poly2.get(i).getZ()+1;
                                }
                                if (poly2.get(i).getY() < sides[3]) { // If farther down(-y)
                                    sides[3] = poly2.get(i).getY();
                                }
                                if (poly2.get(i).getY()+1 > sides[2]) { // If farther up(+y)
                                    sides[2] = poly2.get(i).getY()+1;
                                }
                            }
                            System.out.println("[ChunkMeshCreation] Flushed quad "+new Quad(new Vertex(x+1,sides[3],sides[0]),new Vertex(x+1,sides[2],sides[0]),new Vertex(x+1,sides[2],sides[1]),new Vertex(x+1,sides[3],sides[1]),new Vertex(1,0,0),subSliceType));
                            flushQuad(new Quad(new Vertex(x+1,sides[3],sides[0]),new Vertex(x+1,sides[2],sides[0]),new Vertex(x+1,sides[2],sides[1]),new Vertex(x+1,sides[3],sides[1]),new Vertex(1,0,0),subSliceType)); // Flush face/polygon 2
                            poly2.clear();
                        }
                        
                        if (Natora.world.getBlockAt(new Vertex(x,y,z)) != null) { // If the type difference was the problem
                            subSliceType = null;
                            z--; // TODO Check last block when type change
                        }
                        
                    } else { // Else check both faces/polygons for visibility
                        if (Natora.world.getBlockAt(new Vertex(x-1,y,z)) == null) { // If visible
                            poly1.add(new Vertex(x,y,z));
                        } else {
                            if (!poly1.isEmpty() && subSliceType!=null) {
                                sides = new double[4]; // [L,R,T,B]
                                sides[0] = poly1.get(0).getZ();
                                sides[1] = poly1.get(0).getZ()+1;
                                sides[2] = poly1.get(0).getY()+1;
                                sides[3] = poly1.get(0).getY();
                                for (int i = 1; i < poly1.size(); i++) {
                                    if (poly1.get(i).getZ() < sides[0]) { // If farther left(-z)
                                        sides[0] = poly1.get(i).getZ();
                                    }
                                    if (poly1.get(i).getZ()+1 > sides[1]) { // If farther right(+z)
                                        sides[1] = poly1.get(i).getZ()+1;
                                    }
                                    if (poly1.get(i).getY() < sides[3]) { // If farther down(-y)
                                        sides[3] = poly1.get(i).getY();
                                    }
                                    if (poly1.get(i).getY()+1 > sides[2]) { // If farther up(+y)
                                        sides[2] = poly1.get(i).getY()+1;
                                    }
                                }
                                System.out.println("[ChunkMeshCreation] Flushed quad "+new Quad(new Vertex(x,sides[3],sides[0]),new Vertex(x,sides[2],sides[0]),new Vertex(x,sides[2],sides[1]),new Vertex(x,sides[3],sides[1]),new Vertex(1,0,0),subSliceType));
                                flushQuad(new Quad(new Vertex(x+1,sides[3],sides[0]),new Vertex(x+1,sides[2],sides[0]),new Vertex(x+1,sides[2],sides[1]),new Vertex(x+1,sides[3],sides[1]),new Vertex(1,0,0),subSliceType)); // Flush face/polygon 1
                                poly1.clear();
                            }
                        }
                        if (Natora.world.getBlockAt(new Vertex(x+1,y,z)) == null) { // If visible
                            poly2.add(new Vertex(x,y,z));
                        } else {
                            if (!poly2.isEmpty() && subSliceType!=null) {
                                sides = new double[4]; // [L,R,T,B] // FIX
                                sides[0] = poly2.get(0).getZ();
                                sides[1] = poly2.get(0).getZ()+1;
                                sides[2] = poly2.get(0).getY()+1;
                                sides[3] = poly2.get(0).getY();
                                for (int i = 1; i < poly2.size(); i++) {
                                    if (poly2.get(i).getZ() < sides[0]) { // If farther left(-z)
                                        sides[0] = poly2.get(i).getZ();
                                    }
                                    if (poly2.get(i).getZ()+1 > sides[1]) { // If farther right(+z)
                                        sides[1] = poly2.get(i).getZ()+1;
                                    }
                                    if (poly2.get(i).getY() < sides[3]) { // If farther down(-y)
                                        sides[3] = poly2.get(i).getY();
                                    }
                                    if (poly2.get(i).getY()+1 > sides[2]) { // If farther up(+y)
                                        sides[2] = poly2.get(i).getY()+1;
                                    }
                                }
                                System.out.println("[ChunkMeshCreation] Flushed quad "+new Quad(new Vertex(x+1,sides[3],sides[0]),new Vertex(x+1,sides[2],sides[0]),new Vertex(x+1,sides[2],sides[1]),new Vertex(x+1,sides[3],sides[1]),new Vertex(1,0,0),subSliceType));
                                flushQuad(new Quad(new Vertex(x,sides[3],sides[0]),new Vertex(x,sides[2],sides[0]),new Vertex(x,sides[2],sides[1]),new Vertex(x,sides[3],sides[1]),new Vertex(1,0,0),subSliceType)); // Flush face/polygon 2
                                poly2.clear();
                            }
                        }
                    }
                }
            }
            // Deal with slice, connect the z-rows vertically along the y-axis
        }


        for (int y = curChunk.yPos*Chunk.CHUNKSIZE; y < (curChunk.yPos+1)*Chunk.CHUNKSIZE; y++) { // Split by y
            for (int z = curChunk.zPos*Chunk.CHUNKSIZE; z < (curChunk.zPos+1)*Chunk.CHUNKSIZE; z++) { // Work with z and x
                for (int x = curChunk.xPos*Chunk.CHUNKSIZE; x < (curChunk.xPos+1)*Chunk.CHUNKSIZE; x++) {

                }
            }
        }


        for (int z = curChunk.zPos*Chunk.CHUNKSIZE; z < (curChunk.zPos+1)*Chunk.CHUNKSIZE; z++) { // Split by z
            for (int x = curChunk.xPos*Chunk.CHUNKSIZE; x < (curChunk.xPos+1)*Chunk.CHUNKSIZE; x++) { // Work with x and y
                for (int y = curChunk.yPos*Chunk.CHUNKSIZE; y < (curChunk.yPos+1)*Chunk.CHUNKSIZE; y++) {

                }
            }
        }
        
        

        return currentMesh;
    }
    
    private static void flushQuad(Quad poly) { // Check if face is on side facing camera somehow
        currentMesh.add(poly.convertToTriangles()[0]);
        currentMesh.add(poly.convertToTriangles()[1]);
    }
    
}
