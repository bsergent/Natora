
package com.challengercity.natora;

import static org.lwjgl.opengl.GL11.*;
/**
 *
 * @author Ben Sergent V/ha1fBit
 */
public class Chunk {
    
    public final int xPos,yPos,zPos;
    public static final int CHUNKSIZE = 16;
    private Block[][][] blocks = new Block[CHUNKSIZE][CHUNKSIZE][CHUNKSIZE]; // Locations relative to chunk & May be faster to unwind the block array and calculate location during runtime, like in Map_X Editor's pixels
    private java.util.ArrayList<Triangle> mesh = new java.util.ArrayList<Triangle>();
    
    private boolean isModified = true;
    
    public Chunk(int x, int y, int z) {
        xPos = x;
        yPos = y;
        zPos = z;
    }
    
    public void save(java.io.DataOutputStream dos) throws java.io.IOException {
        
    }
    
    public void setBlock(Vertex k, Block v) {
        blocks[(int)catalog(k.getX()-(xPos*CHUNKSIZE))][(int)catalog(k.getY()-(yPos*CHUNKSIZE))][(int)catalog(k.getZ()-(zPos*CHUNKSIZE))] = v;
        isModified = true;
    }
    
    public Block getBlock(Vertex k) {
        try {
            return blocks[(int)catalog(k.getX()-(xPos*CHUNKSIZE))][(int)catalog(k.getY()-(yPos*CHUNKSIZE))][(int)catalog(k.getZ()-(zPos*CHUNKSIZE))];
        } catch(java.lang.ArrayIndexOutOfBoundsException ex) {
            return null;
        }
    }
    
    private double catalog(double num) {
        if (num < 0) {
            return num - 1;
        }
        return num;
    }
    
    public void calcRendBlks() {
        if (isModified) {
            mesh.clear();
            
            if (false) {
                mesh = ChunkMeshCreater.calculateMesh(this);
            } else {
                /* Simple/Worse Way */
                Vertex vert = new Vertex(xPos*CHUNKSIZE,yPos*CHUNKSIZE,zPos*CHUNKSIZE); // Implement slopes along with larger polygons
                for (int x = xPos*CHUNKSIZE; x < (xPos+1)*CHUNKSIZE; x++) {
                for (int y = yPos*CHUNKSIZE; y < (yPos+1)*CHUNKSIZE; y++)
                for (int z = zPos*CHUNKSIZE; z < (zPos+1)*CHUNKSIZE; z++)

                    if (Natora.world.getBlockAt(vert.setPos(x,y,z)) != null) { // If not air
                        if (Natora.world.getBlockAt(vert.setPos(x-1,y,z)) == null) {
                            mesh.add(new Triangle(new Vertex(x,y,z),new Vertex(x,y,z+1),new Vertex(x,y+1,z+1), new Vertex(1,0,0),Natora.world.getBlockAt(vert.setPos(x,y,z)).getType()));
                            mesh.add(new Triangle(new Vertex(x,y,z),new Vertex(x,y+1,z),new Vertex(x,y+1,z+1), new Vertex(1,0,0),Natora.world.getBlockAt(vert.setPos(x,y,z)).getType()));
                        }
                        if (Natora.world.getBlockAt(vert.setPos(x+1,y,z)) == null) {
                            mesh.add(new Triangle(new Vertex(x+1,y,z),new Vertex(x+1,y+1,z),new Vertex(x+1,y+1,z+1), new Vertex(1,0,0),Natora.world.getBlockAt(vert.setPos(x,y,z)).getType()));
                            mesh.add(new Triangle(new Vertex(x+1,y,z),new Vertex(x+1,y,z+1),new Vertex(x+1,y+1,z+1), new Vertex(1,0,0),Natora.world.getBlockAt(vert.setPos(x,y,z)).getType()));
                        }
                        if (Natora.world.getBlockAt(vert.setPos(x,y,z+1)) == null) {
                            mesh.add(new Triangle(new Vertex(x,y,z+1),new Vertex(x,y+1,z+1),new Vertex(x+1,y+1,z+1), new Vertex(0,0,1),Natora.world.getBlockAt(vert.setPos(x,y,z)).getType()));
                            mesh.add(new Triangle(new Vertex(x,y,z+1),new Vertex(x+1,y,z+1),new Vertex(x+1,y+1,z+1), new Vertex(0,0,1),Natora.world.getBlockAt(vert.setPos(x,y,z)).getType()));
                        }
                        if (Natora.world.getBlockAt(vert.setPos(x,y,z-1)) == null) {
                            mesh.add(new Triangle(new Vertex(x,y,z),new Vertex(x,y+1,z),new Vertex(x+1,y+1,z), new Vertex(0,0,1),Natora.world.getBlockAt(vert.setPos(x,y,z)).getType()));
                            mesh.add(new Triangle(new Vertex(x,y,z),new Vertex(x+1,y,z),new Vertex(x+1,y+1,z), new Vertex(0,0,1),Natora.world.getBlockAt(vert.setPos(x,y,z)).getType()));
                        }
                        if (Natora.world.getBlockAt(vert.setPos(x,y+1,z)) == null) {
                            mesh.add(new Triangle(new Vertex(x,y+1,z),new Vertex(x,y+1,z+1),new Vertex(x+1,y+1,z+1), new Vertex(0,1,0),Natora.world.getBlockAt(vert.setPos(x,y,z)).getType()));
                            mesh.add(new Triangle(new Vertex(x,y+1,z),new Vertex(x+1,y+1,z),new Vertex(x+1,y+1,z+1), new Vertex(0,1,0),Natora.world.getBlockAt(vert.setPos(x,y,z)).getType()));
                        }
                        if (Natora.world.getBlockAt(vert.setPos(x,y-1,z)) == null) {
                            mesh.add(new Triangle(new Vertex(x,y,z),new Vertex(x,y,z+1),new Vertex(x+1,y,z+1), new Vertex(0,1,0),Natora.world.getBlockAt(vert.setPos(x,y,z)).getType()));
                            mesh.add(new Triangle(new Vertex(x,y,z),new Vertex(x+1,y,z),new Vertex(x+1,y,z+1), new Vertex(0,1,0),Natora.world.getBlockAt(vert.setPos(x,y,z)).getType()));
                        }

                    }
                }
            }
            
            
//            
//            double[] polygonEdges = new double[4]; // {L,R,T,B}
//            BlockType polygonType = null;
//            
//            /* Split by X */
//            for (int x = xPos*CHUNKSIZE; x < (xPos+1)*CHUNKSIZE; x++) {
//                for (int y = yPos*CHUNKSIZE; y < (yPos+1)*CHUNKSIZE; y++) {
//                    for (int z = zPos*CHUNKSIZE; z < (zPos+1)*CHUNKSIZE; z++) {
//                        if (Natora.world.getBlockAt(new Vertex(x,y,z)) != null) { // If not null
//                            if (polygonType == null) { // If no type set for the polygon
//                                polygonType = Natora.world.getBlockAt(new Vertex(x,y,z)).getType(); // Set the polygon type to the block's type
//                            } else if (Natora.world.getBlockAt(new Vertex(x,y,z)).getType() == polygonType) { // If type is set, check if the current block is that type
//                                if (Natora.world.getBlockAt(new Vertex(x-1,y,z)) == null) {
//                                    if (z <= polygonEdges[0]) { // If farther to left
//                                        polygonEdges[0] = z;
//                                    }
//                                    if (z >= polygonEdges[1]) { // If farther to left
//                                        polygonEdges[1] = z;
//                                    }
//                                }
//                                if (Natora.world.getBlockAt(new Vertex(x+1,y,z)) == null) {
//                                    //polygonQuads.add(new Quad(new Vertex(x+1,y,z),new Vertex(x+1,y+1,z),new Vertex(x+1,y+1,z+1),new Vertex(x+1,y,z+1),new Vertex(1,0,0),polygonType));
//                                } 
//                            }
//                        } else { // If null, draw the finished polygon
//                            
//                        }
//                    }
//                }
//            }
            
            /* What to check for */
            // Check that the block isn't air
            // If block face is visible to player and surrounding blocks
            // Check for adjacent blocks and their types to merge with
            
            
              // Check if visible, not null, and same block type
              // If so, add to current list of vertices to add to the new polygon
              // If not, add the polygon to the mesh, may be better to iterate the y starting the z at the first z of the last y
            
            /* Split by Y */
             // Same as X
            
            /* Split by Z */
             // Same as X
            
            

            
            /* Complex/Better Way */
//            Vertex vert = new Vertex(xPos*CHUNKSIZE,yPos*CHUNKSIZE,zPos*CHUNKSIZE);
//            java.util.ArrayList<Vertex> side = new java.util.ArrayList<Vertex>();
//            double[] sideEdges = new double[4]; // {L,R,T,B}
//            BlockType sideType = null;
//            
//
//               
//            for (int x = xPos*CHUNKSIZE; x < (xPos+1)*CHUNKSIZE; x++) {
//                for (int y = yPos*CHUNKSIZE; y < (yPos+1)*CHUNKSIZE; y++) {
//                    // If the side z's are the same, connect them
//                    for (int z = zPos*CHUNKSIZE; z < (zPos+1)*CHUNKSIZE; z++) {
//                        if (sideType == null && Natora.world.getBlockAt(vert.setPos(x,y,z))!=null) {
//                            sideType = Natora.world.getBlockAt(vert.setPos(x,y,z)).getType();
//                        }
//                        if (Natora.world.getBlockAt(vert.setPos(x,y,z)) != null && sideType != null && Natora.world.getBlockAt(vert.setPos(x,y,z)).getType() == sideType) { // And same type as current
//                            side.add(new Vertex(x,y,z));
//                        } else if (!side.isEmpty()) {
//                            sideEdges[0] = side.get(0).getZ();
//                            sideEdges[1] = side.get(0).getZ()+1;
//                            sideEdges[2] = side.get(0).getY();
//                            sideEdges[3] = side.get(0).getY()+1;
//                            for (int i = 0; i < side.size(); i++) {
//                                if (side.get(i).getZ() <= sideEdges[0]) { // If farther to left
//                                    sideEdges[0] = side.get(i).getZ();
//                                }
//                                if (side.get(i).getZ() >= sideEdges[1]) { // If farther to right
//                                    sideEdges[1] = side.get(i).getZ()+1;
//                                }
//                                if (side.get(i).getY() < sideEdges[2]) { // If farther up
//                                    sideEdges[2] = side.get(i).getY();
//                                }
//                                if (side.get(i).getY() > sideEdges[3]) { // If farther down
//                                    sideEdges[3] = side.get(i).getY()+1;
//                                }
//                            }
//
////                            0,2 ______2______ 1,2
////                               |          __/|
////                               |       __/   |
////                              0|    __/      |1
////                               | __/         |
////                            0,3|/____________|1,3
////                                      3
//
//                            mesh.add(new Triangle(new Vertex(x,sideEdges[3],sideEdges[0]),new Vertex(x,sideEdges[2],sideEdges[0]),new Vertex(x,sideEdges[2],sideEdges[1]),new Vertex(1,0,0)));
//                            mesh.add(new Triangle(new Vertex(x,sideEdges[3],sideEdges[0]),new Vertex(x,sideEdges[3],sideEdges[1]),new Vertex(x,sideEdges[2],sideEdges[1]),new Vertex(1,0,0)));
//                            side.clear();
//                            sideEdges = new double[4];
//                            if (Natora.world.getBlockAt(vert.setPos(x,y,z)) != null && Natora.world.getBlockAt(vert.setPos(x,y,z)).getType() != sideType) {
//                                z--;
//                            }
//                            sideType = null;
//                        }
//                    }
//                }
//            }
            isModified = false;
        }
    }
    
    public void draw(Location cam) {
        // Draw chunk boundaries for debug
        if (Natora.debug) {
            glLineWidth(2.5f); 
            glColor3f(1.0f, 1.0f, 1.0f);
            glBegin(GL_LINES);
            {
                glVertex3i((xPos)*CHUNKSIZE, (yPos)*CHUNKSIZE, (zPos)*CHUNKSIZE);
                glVertex3i((xPos+1)*CHUNKSIZE, (yPos)*CHUNKSIZE, (zPos)*CHUNKSIZE);

                glVertex3i((xPos)*CHUNKSIZE, (yPos)*CHUNKSIZE, (zPos)*CHUNKSIZE);
                glVertex3i((xPos)*CHUNKSIZE, (yPos+1)*CHUNKSIZE, (zPos)*CHUNKSIZE);

                glVertex3i((xPos)*CHUNKSIZE, (yPos)*CHUNKSIZE, (zPos)*CHUNKSIZE);
                glVertex3i((xPos)*CHUNKSIZE, (yPos)*CHUNKSIZE, (zPos+1)*CHUNKSIZE);

                glVertex3i((xPos+1)*CHUNKSIZE, (yPos)*CHUNKSIZE, (zPos)*CHUNKSIZE);
                glVertex3i((xPos+1)*CHUNKSIZE, (yPos+1)*CHUNKSIZE, (zPos)*CHUNKSIZE);

                glVertex3i((xPos+1)*CHUNKSIZE, (yPos)*CHUNKSIZE, (zPos)*CHUNKSIZE);
                glVertex3i((xPos+1)*CHUNKSIZE, (yPos)*CHUNKSIZE, (zPos+1)*CHUNKSIZE);

                glVertex3i((xPos+1)*CHUNKSIZE, (yPos)*CHUNKSIZE, (zPos+1)*CHUNKSIZE);
                glVertex3i((xPos)*CHUNKSIZE, (yPos)*CHUNKSIZE, (zPos+1)*CHUNKSIZE);

                glVertex3i((xPos+1)*CHUNKSIZE, (yPos+1)*CHUNKSIZE, (zPos)*CHUNKSIZE);
                glVertex3i((xPos+1)*CHUNKSIZE, (yPos+1)*CHUNKSIZE, (zPos+1)*CHUNKSIZE);

                glVertex3i((xPos)*CHUNKSIZE, (yPos)*CHUNKSIZE, (zPos+1)*CHUNKSIZE);
                glVertex3i((xPos)*CHUNKSIZE, (yPos+1)*CHUNKSIZE, (zPos+1)*CHUNKSIZE);

                glVertex3i((xPos)*CHUNKSIZE, (yPos+1)*CHUNKSIZE, (zPos+1)*CHUNKSIZE);
                glVertex3i((xPos)*CHUNKSIZE, (yPos+1)*CHUNKSIZE, (zPos)*CHUNKSIZE);

                glVertex3i((xPos)*CHUNKSIZE, (yPos+1)*CHUNKSIZE, (zPos+1)*CHUNKSIZE);
                glVertex3i((xPos+1)*CHUNKSIZE, (yPos+1)*CHUNKSIZE, (zPos+1)*CHUNKSIZE);

                glVertex3i((xPos)*CHUNKSIZE, (yPos+1)*CHUNKSIZE, (zPos)*CHUNKSIZE);
                glVertex3i((xPos+1)*CHUNKSIZE, (yPos+1)*CHUNKSIZE, (zPos)*CHUNKSIZE);
                
                glVertex3i((xPos+1)*CHUNKSIZE, (yPos)*CHUNKSIZE, (zPos+1)*CHUNKSIZE);
                glVertex3i((xPos+1)*CHUNKSIZE, (yPos+1)*CHUNKSIZE, (zPos+1)*CHUNKSIZE);
            }
            glEnd();
        }
        
        /* Draw block mesh */
        glBegin(GL_TRIANGLES);
        
        Triangle tri;
        for (int i = 0; i < mesh.size(); i++) {
            tri = mesh.get(i);
            switch (tri.getType()) {
                case STONE:
                    glColor3f(0.2f,0.2f,0.2f); // Dark gray
                    break;
                case DIRT :
                    glColor3f(0.5f,0.2f,0.2f); // Reddish gray
                    break;
            }
            glNormal3d(tri.getNormal().getX(),tri.getNormal().getY(),tri.getNormal().getZ());
            glVertex3d(tri.getV1().getX(),tri.getV1().getY(),tri.getV1().getZ());
            glVertex3d(tri.getV2().getX(),tri.getV2().getY(),tri.getV2().getZ());
            glVertex3d(tri.getV3().getX(),tri.getV3().getY(),tri.getV3().getZ());
            Camera.faceCount++;
        }
        glEnd();
        
        glBegin(GL_LINES);
        
        for (int i = 0; i < mesh.size(); i++) {
            tri = mesh.get(i);
            switch (tri.getType()) {
                case STONE:
                    glColor3f(0.3f,0.3f,0.3f); // Dark gray
                    break;
                case DIRT :
                    glColor3f(0.6f,0.3f,0.3f); // Reddish gray
                    break;
            }
            glVertex3d(tri.getV1().getX(),tri.getV1().getY(),tri.getV1().getZ());
            glVertex3d(tri.getV2().getX(),tri.getV2().getY(),tri.getV2().getZ());
            
            glVertex3d(tri.getV2().getX(),tri.getV2().getY(),tri.getV2().getZ());
            glVertex3d(tri.getV3().getX(),tri.getV3().getY(),tri.getV3().getZ());
            
            glVertex3d(tri.getV1().getX(),tri.getV1().getY(),tri.getV1().getZ());
            glVertex3d(tri.getV3().getX(),tri.getV3().getY(),tri.getV3().getZ());
        }
        glEnd();
        
//        glPointSize( 6.0f );
//        glBegin(GL_POINTS);
//        
//        for (int x = 0; x < CHUNKSIZE; x++) {
//        for (int y = 0; y < CHUNKSIZE; y++) {
//        for (int z = 0; z < CHUNKSIZE; z++) {
//            if (blocks[x][y][z]!=null) {
//                switch (blocks[x][y][z].getType()) {
//                    case STONE:
//                        glColor3f(0.3f,0.3f,0.3f); // Dark gray
//                        break;
//                    case DIRT :
//                        glColor3f(0.6f,0.3f,0.3f); // Reddish gray
//                        break;
//                }
//                glVertex3d(x+0.5f+(xPos*CHUNKSIZE),y+0.5f+(yPos*CHUNKSIZE),z+0.5f+(zPos*CHUNKSIZE));
//            }
//        }
//        }
//        }
//        glEnd();
    }
     
}
