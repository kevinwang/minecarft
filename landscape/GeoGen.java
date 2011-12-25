import java.util.*;
public class GeoGen{
    public final int LENGTH = 256;
    public final int WIDTH = 256;
    public final int HEIGHT= 256;
    //LENGTH = WIDTH = HEIGHT = 256;
    public GeoGen(){
	Random r = new Random();
	/*int[][][] world;
	world = new int[32][32][256];
	generate(r.nextInt());
	*/
	int[][] world = perlinNoise();
	for(int i = 0; i < LENGTH; i++){
	    for(int j = 0; j< WIDTH; j++){
		System.out.print(world[i][j]);
	    }
	    System.out.println();
	}
    }
    public GeoGen(int s){
	Random r = new Random(s);
	int[][][] world;
	world = new int[LENGTH][WIDTH][HEIGHT];
	generate(r.nextInt());
    }
    public void generate(int a){
	/*placeBedrock();
	placeStone(a);
	placeDirt(a);
	addWater();
	erodeLandscape(a);
	addLava();
	plantTrees(a);
	*/
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
    /*public void placeBedrock(){
	for(int i = 0; i < 32; i++){
	    for(int j = 0; j < 32; j++){
		world[i][j][0] = 1337;
	    }
	}
    }

    public void placeStone(){
	
    }    
    */
    //32x32 perlin noise generator
    public int[][] perlinNoise(){
	Random r = new Random();
	int [][] ret,tmp;
	tmp = ret = new int[LENGTH][WIDTH];
	for(int h = 16; h > 0; h--){ //4 iterations
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
		    for(int k = m; k < o; k++){
			for(int l = n; l < p; l++){
			    tmp[k][l] = r.nextInt((int)(h/2));
			}
		    }
		}
	    }
	    for(int i = 0; i < 32; i++){
		for(int j = 0; j < 32; j++){
		    ret[i][j] += tmp[i][j];
		}
	    }
	}
	return ret;
    }
    /*
    public String toString(int lvl){
	for(int i = 0; i < 32; i++){
	    for(int j = 0; j < 32; j++){
		System.out.println(world[i][j][lvl]);
	    }
	    System.out.println();
	}
    }
    */
}