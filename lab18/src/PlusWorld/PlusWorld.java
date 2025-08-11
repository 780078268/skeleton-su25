package PlusWorld;
import org.junit.Test;
import static org.junit.Assert.*;

import byowTools.TileEngine.TERenderer;
import byowTools.TileEngine.TETile;
import byowTools.TileEngine.Tileset;

import java.util.Random;

/**
 * Draws a world consisting of plus shaped regions.
 */
public class PlusWorld {

    public static void main(String[] args) {
        int s = 5;
        TERenderer ter = new TERenderer();
        ter.initialize(100,100);
        TETile[][] randomTiles = new TETile[100][100];
        fillWithRandomTiles(randomTiles , s);
        ter.renderFrame(randomTiles);
    }

    public static void fillWithRandomTiles(TETile[][] tiles,int s) {
        int height = tiles[0].length;
        int width = tiles.length;
        for (int x = 0; x < 3*s; x += 1) {
            if(x<s || x >= 2*s){
                for(int y = 0; y < s; y += 1) {
                    tiles[x][y+s] = Tileset.WALL;
            }
            }else{
                for(int y = 0; y < s*3; y += 1) {
                    tiles[x][y] = Tileset.WALL;
                }
            }
        }
    }

}
