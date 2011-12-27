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
    private int length;
    private int width;
    private int height;
    private int sealvl = 20;
    private int tonofstone = 50;
    private int tonofdirt = 5;
    private int[][][] world;
    int LAVA = World.TYPE_WATER;
    public LandGen(int length, int width, int height){
        Random r = new Random();
        world = new int[length+1][width+1][height+1];
        this.length = length;
        this.width = width;
        this.height = height;
        for(int i = 0; i < length; i++){
            for(int j = 0; j < width; j++){
                for(int k = 0; k < height; k++){
                    world[i][j][k] = World.TYPE_AIR;
                }
            }
        }
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
    
    private void placeBedrock(){
        for(int i = 0; i < length; i++){
            for(int j = 0; j < width; j++){
                world[i][j][0] = World.TYPE_BEDROCK;
            }
        }
    }

    private void placeStone(){
        //tonofstone high chunk at the bottom
        for(int i = 0; i < length; i++){
            for(int j = 0; j < width; j++){
                for(int k = 1; k < tonofstone; k++)
                    world[i][j][k] = World.TYPE_STONE;
            }
        }
        //perlin noise to top it off
        int[][] perlin = perlinNoise(13);
        for(int i = 0; i < length; i++){
            for(int j = 0; j < width; j++){
                for(int k = tonofstone; k < perlin[i][j]+tonofstone; k++){
                    world[i][j][k] = World.TYPE_STONE;
                }
            }
        }
    }
    
    private void placeDirt(){
        int[][] perlin = perlinNoise(3);
        for(int i = 0; i<length; i++){
            for(int j = 0; j<width; j++){
                perlin[i][j] += tonofdirt;
            }
        }
        for(int i = 0; i < length; i++){
            for(int j = 0; j < width; j++){
                lab:for(int k = tonofstone+1; k < height; k++){
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
        int tmp = 0;
        int levels = 0;
        boolean b = false;
        for(int k = tonofstone + tonofdirt; k < height; k++){
            if(levels < 8){
                for(int i = 0; i < length; i++){
                    for(int j = 0; j < width; j++){
                        if(world[i][j][k] == 0){
                            world[i][j][k] = World.TYPE_WATER;
                            b=true;
                            if(k>tmp){tmp=k;}
                        }
                    }
                }
                if(b){levels++;}
            }
            sealvl=tmp;
            System.out.println(sealvl);
        }
    }
    
    private void erodeLandscape(){
        Random r = new Random();
        int a,b,c;
        //setting erosion source blocks
        for(int i = 0; i < 8; i++){
            a = r.nextInt(length);
            b = r.nextInt(width);
            c = sealvl + 8 +r.nextInt(10);
            world[a][b][c] = 9001; //erosion magic number
        }
        for(int i = 0; i < 8; i++){
            a = r.nextInt(length);
            b = r.nextInt(width);
            c = sealvl - 4 - r.nextInt(10);
            world[a][b][c] = 9001; //erosion magic number
        }
        //eroding
        for(int h = 0; h < 500; h++){
            for(int i = 1; i < length - 1; i++){
                for(int j = 1; j < width - 1; j++){
                    for(int k = 1; k < sealvl + 20; k++){
                        if(world[i][j][k] == 9001){
                            switch(r.nextInt(28)+1){
                                case  1:case  2:case  3:
                                    world[i][j][k-1] = 9001;
                                    break;
                                case  4:case  5:case  6:case  7:case  8:case  9:
                                    world[i-1][j][k] = 9001;
                                    break;
                                case 10:case 11:case 12:case 13:case 14:case 15:
                                    world[i+1][j][k] = 9001;
                                    break;
                                case 16:case 17:case 18:case 19:case 20:case 21:
                                    world[i][j+1][k] = 9001;
                                    break;
                                case 22:case 23:case 24:case 25:case 26:case 27:
                                    world[i][j-1][k] = 9001;
                                    break;
                                case 28:
                                    world[i][j][k+1] = 9001;
                                    break;
                            }                  
                            world[i][j][k] = 9002;
                            if(r.nextInt(10000) == 1){
                                world[i][j][k] = 9001;
                            }
                            if(r.nextInt(1000) == 1){
                                world[i][j][k] = 9004;
                            }
                        }
                    }    
                }
            }
        }
        for(int h = 0; h < 300; h++){
            for(int i = 1; i < length - 1; i++){
                for(int j = 1; j < width - 1; j++){
                    for(int k = 1; k < sealvl + 20; k++){
                        if(world[i][j][k] == 9004){
                            switch(r.nextInt(28)+1){
                                case  1:case 2:
                                    world[i][j][k-1] = 9004;
                                    break;
                                case 3:case  4:case  5:case  6:case  7:case  8:
                                    world[i-1][j][k] = 9004;
                                    break;
                                case  9:case 10:case 11:case 12:case 13:case 14:
                                    world[i+1][j][k] = 9004;
                                    break;
                                case 15:case 16:case 17:case 18:case 19:case 20:
                                    world[i][j+1][k] = 9004;
                                    break;
                                case 21:case 22:case 23:case 24:case 25:case 26:
                                    world[i][j-1][k] = 9004;
                                    break;
                                case 27:case 28:
                                    world[i][j][k+1] = 9004;
                                    break;
                            }
                            world[i][j][k] = 9002;
                        }
                    }
                }
            }
        }
        //thicker tunnels
        for(int h = 0; h < 5; h++){
            for(int i = 0; i < length; i++){
                for(int j = 0; j < width; j++){
                    for(int k = 0; k < height; k++){ 
                        try{
                            if(
                                world[i-1][j][k] > 9000 ||
                                world[i][j-1][k] > 9000 ||
                                world[i][j][k-1] > 9000 ||
                                world[i+1][j][k] > 9000 ||
                                world[i][j+1][k] > 9000 ||
                                world[i][j][k+1] > 9000 ){
                                 world[i][j][k] = 8999;
                            }
                        }catch(Exception e){                    
                        }                
                    }
                }
            }
        }
        //air
        for(int i = 0; i < length; i++){
            for(int j = 0; j < width; j++){
                for(int k = 0; k < height; k++){               
                    if(world[i][j][k] >= 8999){
                        world[i][j][k] = World.TYPE_AIR;
                    }
                }
            }
        }
        //cleanup
        for(int i = 0; i < length; i++){
            for(int j = 0; j < width; j++){
                for(int k = 0; k < height; k++){ 
                    try{
                        if(world[i][j][k] != 0 && 
                            world[i-1][j][k] == World.TYPE_AIR &&
                            world[i][j-1][k] == World.TYPE_AIR &&
                            world[i][j][k-1] == World.TYPE_AIR &&
                            world[i+1][j][k] == World.TYPE_AIR &&
                            world[i][j+1][k] == World.TYPE_AIR &&
                            world[i][j][k+1] == World.TYPE_AIR){
                             world[i][j][k] = World.TYPE_AIR;
                        }
                    }catch(Exception e){
                        
                    }
                    
                }
            }
        }
    }
    public void addLava(){
        int tmp = 0;
        int levels = 0;
        boolean b = false;
        for(int k = 1; k < height; k++){
            if(levels < 13){
                for(int i = 0; i < length; i++){
                    for(int j = 0; j < width; j++){
                        if(world[i][j][k] == 0){
                            world[i][j][k] = LAVA;
                            b=true;
                            if(k>tmp){tmp=k;}
                        }
                    }
                }
                if(b){levels++;}
            }
        }
    }
    public void plantTrees(){
        
    }
    public int[][] perlinNoise(int a){
        Random r = new Random();
        int [][] ret,tmp;
        tmp = ret = new int[length][width];
        for(int h = a+2; h > 0; h--){ //buncha iterations                                                
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
                    int q = r.nextInt(h/a +1);
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
