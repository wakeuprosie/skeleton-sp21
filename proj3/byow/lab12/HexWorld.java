package byow.lab12;
import org.junit.Test;
import static org.junit.Assert.*;

import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

import java.util.Random;

/**
 * Draws a world consisting of hexagonal regions.
 */
public class HexWorld {

    // Making some changes for git
    // Create a hexagon with sides length S and add it to a position in the world
    public static void addHexagon(int S) {

        int left, right;
        int content = S;
        left = right = S - 1;

        // top half
        for (int i = 1; i <= S; i += 1) {
            for (int j = left; j > 0; j -= 1) {
                System.out.print(" ");
            }
            for (int h = content; h > 0; h -= 1) {
                System.out.print("a");
            }
            for (int k = right; k > 0; k -= 1) {
                System.out.print(" ");
            }
            System.out.println();
            left = right -= 1;
            content += 2;
        }

        // bottom half idk
        left = right = 0;
        content -= 2;
        for (int i = S; i > 0; i -= 1) {
            for (int j = left; j > 0; j -= 1) {
                System.out.print(" ");
            }
            for (int h = content; h > 0; h -= 1) {
                System.out.print("a");
            }
            for (int k = right; k > 0; k -= 1) {
                System.out.print(" ");
            }
            left = right += 1;
            content -= 2;
            System.out.println();
        }
    }

    public static void main(String[] args) {
        addHexagon(3);
    }

}
