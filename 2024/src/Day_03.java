import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Day_03 {
    public static void main(String[] args) throws FileNotFoundException {
        int answer1 = 0, answer2 = 0;
        boolean d = true;
        try (Scanner reader = new Scanner(new File("../resources/03.txt"))) {
            while (true) {
                String op = reader.findWithinHorizon(Pattern.compile("mul\\(\\d{1,3},\\d{1,3}\\)|(do|don't)\\(\\)"), 0);
                if (op == null) break;                              //^^ Parse next valid token
                else try (Scanner numberScan = new Scanner (op)) {
                    int add = 0;
                    switch (op) {
                        case "do()" -> d = true;                    //Flip bool for Part 2
                        case "don't()" -> d = false;
                        default -> add = Integer.parseInt(numberScan.findInLine("\\d{1,3}")) * Integer.parseInt(numberScan.findInLine("\\d{1,3}"));
                    }                                               //^^ mul() calculation
                    answer1 += add;                                 //Add all for Part 1 solution
                    if (d) answer2 += add;                          //Add conditionally for Part 2 solution                                                        
                    numberScan.close();                             //Close reader to prevent memory leak
                }
            }
            reader.close();                                         //Close reader to prevent memory leak
        }
        System.out.println(answer1 + "\r\n" + answer2);             //Print solutions
    }
}