import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

public class Day_06 {
    static int[] startPos = new int[2];
    public static void main(String[] args) throws IOException{
        int answer1 = 0, answer2 = 0;
        List<String> input = Files.readAllLines(Path.of("../resources/06.txt"));
        char[][] map = new char[input.size()][input.get(0).length()];
        for (int i = 0; i < input.size(); i++)
            for (int j = 0; j < input.get(i).length(); j++) {
                map[i][j] = input.get(i).charAt(j);
                if (map[i][j] == '^') startPos = new int[]{i, j};           //Parse the map and note the starting position
            }
        char[][] solveMap = copyMap(map);
        walkPath(solveMap);                                                 //Solve the path for the part 1 answer
        for (char[] line : solveMap)
            for (char c : line)
                if (c == 'X') answer1++;                                    //Count the number of X's in the solved map
        for (int i = 0; i < map.length; i++)
            for (int j = 0; j < map[0].length; j++)
                if (solveMap[i][j] == 'X' && map[i][j] != '^') {            //New obstructions only matter if they are in the guard's usual path
                    char[][] altMap = copyMap(map);                         //For every position, create a new map with the new obstruction
                    altMap[i][j] = '#';                                     //and solve its path. Then increment the answer 2 counter if
                    if(walkPath(altMap)) answer2++;                         //a loop is found.
                }
        System.out.println(answer1 + "\r\n" + answer2);                     //Print solutions
    }
    public static boolean walkPath(char[][] map) {
        boolean loop = false;
        int[][] directions = new int[][]{{-1,0},{0,1},{1,0},{0,-1}};        //UP, RIGHT, DOWN, LEFT
        int[] guardPos = startPos.clone();
        int[] currentDir = directions[0];
        HashSet<List> history = new HashSet<>();
        while (guardPos[0]+currentDir[0] > -1 && guardPos[0]+currentDir[0] < map.length && guardPos[1]+currentDir[1] > -1 && guardPos[1]+currentDir[1] < map[0].length && !loop) {
            while (map[guardPos[0]+currentDir[0]][guardPos[1]+currentDir[1]] == '#')
                switch (currentDir[0]) {                                    //^^ Turn right while obstructed
                    case -1 -> currentDir = directions[1];
                    case 1 -> currentDir = directions[3];
                    default -> {
                        switch (currentDir[1]) {
                            case -1 -> currentDir = directions[0];
                            case 1 -> currentDir = directions[2];
                            default -> {}
                        }
                    }
                }
            map[guardPos[0]][guardPos[1]] = 'X';                            //Mark current tile, then move forward
            guardPos[0] += currentDir[0];
            guardPos[1] += currentDir[1];                                   //If this direction at this position has already been seen, a loop is found
            if (!history.add(Arrays.asList(guardPos[0],guardPos[1],currentDir[0],currentDir[1]))) loop = true;
        }
        map[guardPos[0]][guardPos[1]] = 'X';                                //Mark the last square walked after the path ends
        return loop;
    }
    public static char[][] copyMap(char[][] map) {                          //Function to copy the map
        char[][] copy = new char[map.length][map[0].length];
        for (int i = 0; i < map.length; i++)
            copy[i] = map[i].clone();
        return copy;
    }
}