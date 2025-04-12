import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class Day_04 {
    static char[][] charMap;
    public static void main(String[] args) throws IOException {
        int answer1 = 0, answer2 = 0;
        List<String> list = (Files.readAllLines(Path.of("../resources/04.txt")));
        charMap = new char[list.size()][list.get(1).length()];
        for (int i = 0; i < list.size(); i++)
            for (int j = 0; j < list.get(0).length(); j++)
                charMap[i][j] = list.get(i).charAt(j);                  //Parse input to char[][]
        for (int i = 0; i < charMap.length; i++)
            for (int j = 0; j < charMap[0].length; j++)
                if (charMap[i][j] == 'X')
                    answer1 += lookForXMAS(new int[]{i,j});             //XMAS search for Part 1 solution
                else if (charMap[i][j] == 'A')
                    if (lookForXMAS2(new int[]{i,j})) answer2++;        //X-MAS search for Part 2 solution
        System.out.println(answer1 + "\r\n" + answer2);
    }
    public static int lookForXMAS(int[] coord) {                        //Check all 8 directions for "XMAS"
        int count = 0;
        int[][] check = new int[][]{{-1,-1},{-1,0},{-1,1},{0,-1},{0,1},{1,-1},{1,0},{1,1}};
        for (int[] coo : check)
            if (coord[0]+(coo[0]*3)>=0 && coord[0]+(coo[0]*3)<charMap.length && coord[1]+(coo[1]*3)>=0 && coord[1]+(coo[1]*3)<charMap[0].length)
                if (charMap[coord[0] + coo[0]][coord[1] + coo[1]] == 'M')
                    if (charMap[coord[0] + (coo[0] * 2)][coord[1] + (coo[1] * 2)] == 'A')
                        if (charMap[coord[0] + (coo[0] * 3)][coord[1] + (coo[1] * 3)] == 'S')
                            count++;
        return count;
    }
    public static boolean lookForXMAS2(int[] coords) {                  //Check the 2 top corners and their opposites to check for X-MAS
        boolean xmas = false;
        if (coords[0] > 0 && coords[0] < charMap.length - 1 && coords[1] > 0 && coords[1] < charMap[0].length - 1) {
            boolean x = true;
            switch (charMap[coords[0]-1][coords[1]-1]) {
                case 'M' -> { if (charMap[coords[0]+1][coords[1]+1] != 'S') x = false; }
                case 'S' -> { if (charMap[coords[0]+1][coords[1]+1] != 'M') x = false; }
                default -> { x = false; }
            }
            switch (charMap[coords[0]-1][coords[1]+1]) {
                case 'M' -> { if (charMap[coords[0]+1][coords[1]-1] != 'S') x = false; }
                case 'S' -> { if (charMap[coords[0]+1][coords[1]-1] != 'M') x = false; }
                default -> { x = false; }
            }
            xmas = x;
        }
        return xmas;
    }
}