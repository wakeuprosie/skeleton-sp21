package byow.lab12;
import org.junit.Test;
import static org.junit.Assert.*;

import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;


/**
 * Draws a world consisting of hexagonal regions.
 */
public class HexWorld {

    public static TETile[][] addHexagon(int s) {

        // grid size
        int width = (s * 2) + (s - 2);
        int height = s * 2;

        // initialize a 2D array to hold the hexagon
        TETile[][] hexagon = new TETile[width][height];
        for (int x = 0; x < width; x += 1) {
            for (int y = 0; y < height; y += 1) {
                hexagon[x][y] = Tileset.NOTHING;
            }
        }

        // bottom half
        for (int y = 0; y < s; y += 1) { // loop over rows

            int startC = s - 1 - (1 * y); // starting index x to fill
            int content = s + (2 * y); // # of tiles to fill

            while (content != 0) { // fill tiles in this row
                hexagon[startC][y] = Tileset.FLOWER;
                startC += 1;
                content -= 1;
            }
        }

        // top half
        int i = 0;
        int h = (s - 1);
        for (int y = s; y < (2 * s); y += 1) {
            int startC = 0 + (1 * i);
            int content = s + (2 * h);
            while (content != 0) {
                hexagon[startC][y] = Tileset.FLOWER;
                startC += 1;
                content -= 1;
            }
            i += 1;
            h -= 1;
        }

        return hexagon;

    }


    public static void main(String[] args) {

        // Tile rendering engine
        TERenderer ter = new TERenderer();
        ter.initialize(10, 8); // Hard code for a world with 19 hexagons, each of size 3

        TETile[][] hexagon = addHexagon(3);

        // Fill in a hexagon
        ter.renderFrame(hexagon);

    }

}
