import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Day_05 {
    public static void main(String[] args) throws IOException {
        int answer1 = 0, answer2 = 0;
        List<String> input = (Files.readAllLines(Path.of("../resources/05.txt")));
        ArrayList<int[]> rules = new ArrayList<>();
        ArrayList<ArrayList<Integer>> updates = new ArrayList<>();
        for (String line : input)
            if (!line.isEmpty())
                if (line.contains("|"))
                    try (Scanner numberScan = new Scanner(line).useDelimiter("\\||\r\n")) {
                        rules.add(new int[]{numberScan.nextInt(), numberScan.nextInt()});
                        numberScan.close();                             //^^ Parse the rules
                    }
                else
                    try (Scanner numberScan = new Scanner(line).useDelimiter(",|\r\n")) {
                        ArrayList<Integer> pages = new ArrayList<>();
                        while (numberScan.hasNextInt())
                            pages.add(numberScan.nextInt());            //Parse the updates
                        updates.add(pages);
                        numberScan.close();
                    }
        for (ArrayList<Integer> update : updates) {
            ArrayList<int[]> relevantRules = new ArrayList<>();
            for (int[] rule : rules)                                    //vv Keep only the rules relevant to the update
                if (update.contains(rule[0]) && update.contains(rule[1])) relevantRules.add(rule);
            boolean valid = true;
            for (int[] rule : relevantRules)
                if (update.indexOf(rule[0]) > update.indexOf(rule[1])) valid = false;
            if (valid) answer1 += update.get((update.size()-1)/2);      //Add the middle numbers for Part 1 solution
            else {
                boolean swap = true;
                while (swap) {                                          //Swap elements according to the rule table
                    swap = false;                                       //until swaps are no longer needed
                    for (int[] rule : relevantRules)
                        if (update.indexOf(rule[0]) > update.indexOf(rule[1])) {
                            update.set(update.indexOf(rule[0]), rule[1]);
                            update.set(update.indexOf(rule[1]), rule[0]);
                            swap = true;
                        }
                }
                answer2 += update.get((update.size()-1)/2);             //Add the middle numbers for Part 2 solution
            }
        }
        System.out.println(answer1 + "\r\n" + answer2);                 //Print solutions
    }
}