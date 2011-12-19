import java.util.*;
public class GeoGen{
    public GeoGen(){
	Random r = new Random();
	/*int[][][] world;
	world = new int[32][32][256];
	generate(r.nextInt());
	*/
	int[][] world = perlinNoise();
	for(int i = 0; i < 32; i++){
	    for(int j = 0; j<32; j++){
		System.out.print(world[i][j]);
	    }
	    System.out.println();
	}
    }
    public GeoGen(int s){
	Random r = new Random(s);
	int[][][] world;
	world = new int[32][32][256];
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
	tmp = ret = new int[32][32];
	for(int h = 4; h > 0; h--){
	    for(int i = 0; i < 32; i+=2*h){
		for(int j= 0; j < 32; j+=2*h){
		    tmp[i][j] = r.nextInt();
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