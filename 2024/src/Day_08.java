import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class Day_08 {
    public static void main(String[] args) throws IOException {
        int answer1 = 0, answer2 = 0;
        List<String> input = Files.readAllLines(Path.of("../resources/08.txt"));
        char[][] nodeMap = new char[input.size()][input.get(0).length()];
        char[][] antinodeMap = new char[nodeMap.length][nodeMap[0].length];
        char[][] antinodeRHMap = new char[nodeMap.length][nodeMap[0].length];
        HashSet<Character> nodeSet = new HashSet<>();
        for (String line : input)
            for (int i = 0; i < line.length(); i++) {
                nodeMap[input.indexOf(line)][i] = line.charAt(i);                           //Parsing input to 2D array
                if (line.charAt(i) != '.') nodeSet.add(line.charAt(i));                     //Storing every unique node type
            }
        for (int i = 0; i < nodeMap.length; i++) 
            for (int j = 0; j < nodeMap[0].length; j++){
                antinodeMap[i][j] = '.';
                antinodeRHMap[i][j] = '.';                                                  //Filling both solution arrays with dots
            }
        for (char c : nodeSet) {                                                            //For each unique node type
            ArrayList<int[]> nodeCoords = new ArrayList<>();
            for (int i = 0; i < nodeMap.length; i++)
                for (int j = 0; j < nodeMap[i].length; j++)
                    if (nodeMap[i][j] == c) nodeCoords.add(new int[]{i,j});                 //Store the coordinates of each node
            for (int i = 0; i < nodeCoords.size(); i++)                                     //The number of every possible node pair is "n(nodes) choose 2"
                for (int j = i + 1; j < nodeCoords.size(); j++) {
                    int[] ant1 = nodeCoords.get(i), ant2 = nodeCoords.get(j);               //Store the coordinates of both current nodes
                    int[] nodeVector = new int[] {ant2[0] - ant1[0],ant2[1] - ant1[1]};     //Store the vector from ant1 to ant2
                    int r = 0;                                                              //Resonance variable for part 2 solution (applying vector 0 times ensures the antenna is itself an antinode)
                    while (ant2[0] + (nodeVector[0] * r) > -1 && ant2[0] + (nodeVector[0] * r) < antinodeMap.length &&      //Check if ant2 position + vector * r is within bounds
                           ant2[1] + (nodeVector[1] * r) > -1 && ant2[1] + (nodeVector[1] * r) < antinodeMap[0].length) {
                        antinodeRHMap[ant2[0] + (nodeVector[0] * r)][ant2[1] + (nodeVector[1] * r)] = '#';          //If so mark it on part 2 map
                        if (r == 1) antinodeMap[ant2[0] + nodeVector[0]][ant2[1] + nodeVector[1]] = '#';            //If we're applying the vector exactly one time and mark it on part 1 map
                        r++;
                    }
                    r = 0;
                    while (ant1[0] - (nodeVector[0] * r) > -1 && ant1[0] - (nodeVector[0] * r) < antinodeMap.length &&      //Check if ant1 position - vector * r is within bounds
                           ant1[1] - (nodeVector[1] * r) > -1 && ant1[1] - (nodeVector[1] * r) < antinodeMap[0].length) {
                        antinodeRHMap[ant1[0] - (nodeVector[0] * r)][ant1[1] - (nodeVector[1] * r)] = '#';          //If so mark it on part 2 map
                        if (r == 1) antinodeMap[ant1[0] - nodeVector[0]][ant1[1] - nodeVector[1]] = '#';            //If we're applying the vector exactly one time and mark it on part 1 map
                        r++;
                    }
                }
        }
        for (char[] line : antinodeMap)
            for (char c : line) if (c == '#') answer1++;    //Count the unique antinode positions without resonant harmonics
        for (char[] line : antinodeRHMap)
            for (char c : line) if (c == '#') answer2++;    //Count the unique antinode positions with resonant harmonics
        System.out.println(answer1+"\r\n"+answer2);
    }
}