import java.util.*;
import java.io.*;
public class LandGen{
    public final int LENGTH = 128;
    public final int WIDTH = 128;
    public final int HEIGHT= 128;
    int[][][] world;
    public LandGen(){
	Random r = new Random();
	world = new int[LENGTH][WIDTH][HEIGHT];
	generate();
    }
    public void generate(){
	placeBedrock();
	placeStone();
	placeDirt();
	addWater();
	erodeLandscape();
	addLava();
	plantTrees();
    }
    /*
      ----------Key----------
      air = 0
      stone = 1
      dirt = 2
      wood = 3
      leaves = 4
      water = 10
      lava = 11
      bedrock = 1337
     */
    public void placeBedrock(){
	for(int i = 0; i < LENGTH; i++){
	    for(int j = 0; j < WIDTH; j++){
		world[i][j][0] = 1337;
	    }
	}
    }
    
    public void placeStone(){
	int[][] perlin = perlinNoise();
	for(int i = 0; i < LENGTH; i++){
	    for(int j = 0; j < WIDTH; j++){
		for(int k = 1; k < perlin[i][j]+1; k++){
		    world[i][j][k]=1;
		}
	    }
	}    
    }
    public void placeDirt(){
	int[][] perlin = perlinNoise();
	for(int i = 0; i < LENGTH; i++){
	    for(int j = 0; j < WIDTH; j++){
		lab:for(int k = 0; k < HEIGHT; k++){
		    if(world[i][j][k] == 0){
			for(int l = 0; l < perlin[i][j]; l++){
			    world[i][j][l+k] = 2;
			}
			break lab;
		    }
		}
	    }
	}
    }
    public void addWater(){
	for(int i = 0; i< )
    }
    public void erodeLandscape(){}
    public void addLava(){}
    public void plantTrees(){}
    public int[][] perlinNoise(){
	Random r = new Random();
        int [][] ret,tmp;
        tmp = ret = new int[LENGTH][WIDTH];
        for(int h = 16; h > 0; h--){ //buncha iterations                      
            tmp = new int[LENGTH][WIDTH];
            for(int i = 0; i < LENGTH; i+=h){ //x axis                        
                for(int j= 0; j < WIDTH; j+=h){ //y axis                      
                    int m,n,o,p;
                    m = i-h;
                    n = j-h;
                    o = i+h;
                    p = j+h;
                    if(m<0){m=0;}
                    if(n<0){n=0;}
                    if(o>LENGTH){o=LENGTH;}
                    if(p>WIDTH){p=WIDTH;}
                    int q = r.nextInt(h/8 +1);
                    for(int k = m; k < o; k++){
                        for(int l = n; l < p; l++){
                            tmp[k][l] += q;                  
                        }
                    }
                }
            }
            for(int i = 0; i < LENGTH; i++){
                for(int j = 0; j < WIDTH; j++){
                    ret[i][j] += tmp[i][j];
                }
            }
	}
	return ret;
    }
    public int[][][] getWorld(){
	return world;
    } 
}