/**
 * LandGen.java
 * Copyright (C) 2011  Kevin Wang and Shan Shi
 * 
 * This file is part of Minecarft.
 * 
 * Minecarft is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * Minecarft is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with Minecarft.  If not, see <http://www.gnu.org/licenses/>.
 */

package minecarft;

import java.util.Random;

/**
 *
 * @author sshi
 */
public class LandGen {
    private int length = 128;
    private int width = 128;
    private int height = 128;
    private int[][][] world;
    
    public LandGen(int length, int width, int height){
        Random r = new Random();
        world = new int[length][width][height];
        this.length = length;
        this.width = width;
        this.height = height;
        generate();
    }
    
    private void generate(){
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
    private void placeBedrock(){
        for(int i = 0; i < length; i++){
            for(int j = 0; j < width; j++){
                world[i][j][0] = World.TYPE_BEDROCK;
            }
        }
    }

    private void placeStone(){
        int[][] perlin = perlinNoise();
        for(int i = 0; i < length; i++){
            for(int j = 0; j < width; j++){
                for(int k = 1; k < perlin[i][j]+1; k++){
                    world[i][j][k] = World.TYPE_STONE;
                }
            }
        }
    }
    
    private void placeDirt(){
        int[][] perlin = perlinNoise();
        for(int i = 0; i < length; i++){
            for(int j = 0; j < width; j++){
                lab:for(int k = 0; k < height; k++){
                    if(world[i][j][k] == 0){
                        for(int l = 0; l < perlin[i][j]; l++){
                            world[i][j][l+k] = World.TYPE_DIRT;
                        }
                        break lab;
                    }
                }
            }
        }
    }
    
    private void addWater(){
        
    }
    
    public void erodeLandscape(){}
    public void addLava(){}
    public void plantTrees(){}
    public int[][] perlinNoise(){
        Random r = new Random();
        int [][] ret,tmp;
        tmp = ret = new int[length][width];
        for(int h = 16; h > 0; h--){ //buncha iterations                                                
            tmp = new int[length][width];
            for(int i = 0; i < length; i+=h){ //x axis                                                  
                for(int j= 0; j < width; j+=h){ //y axis                                                
                    int m,n,o,p;
                    m = i-h;
                    n = j-h;
                    o = i+h;
                    p = j+h;
                    if(m<0){m=0;}
                    if(n<0){n=0;}
                    if(o>length){o=length;}
                    if(p>width){p=width;}
                    int q = r.nextInt(h/13 +1);
                    for(int k = m; k < o; k++){
                        for(int l = n; l < p; l++){
                            tmp[k][l] += q;
                        }
                    }
                }
            }
            for(int i = 0; i < length; i++){
                for(int j = 0; j < width; j++){
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
