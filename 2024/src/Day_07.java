import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Day_07 {
    public static void main(String[] args) throws IOException {
        long answer1 = 0, answer2 = 0;
        List<String> input = Files.readAllLines(Path.of("../resources/07.txt"));
        for (String line : input)
            try (Scanner reader = new Scanner(line).useDelimiter(": | ")) {
                long testValue = reader.nextLong();                                 //Parse test value
                ArrayList<Long> terms = new ArrayList<>();
                while (reader.hasNextLong()) terms.add(reader.nextLong());          //Parse equation terms
                if (solveEquationPM(testValue, terms)) answer1 += testValue;        //Solve with only + and *
                else if (solveEquationCPM(testValue, terms)) answer2 += testValue;  //Solve with || + and *
                reader.close();                                                     //Close Scanner to prevent memory leaks
            }
        answer2 += answer1;
        System.out.println(answer1+"\r\n"+answer2);                                 //Print solutions
    }
    public static boolean solveEquationPM(long val, ArrayList<Long> arr) {          //Attempts to solve the equation using only * and + operators
        for (int i = 0; i < Math.pow(2, arr.size()-1); i++) {                       //Every possible equation is encoded in the binary numbers 0 to n(operators)
            String bits = Integer.toBinaryString(i);
            while (bits.length() < arr.size()-1) bits="0"+bits;                     //Add leading 0s if needed
            long result = arr.get(0);
            for (int j = 0; j < arr.size()-1; j++)                                  //Go through every bit and operate accordingly
                if (bits.charAt(j)=='1') result *= arr.get(j+1);                    //1 for * and 0 for +
                else result += arr.get(j+1);
            if (result == val) return true;
        }
        return false;
    }
    public static boolean solveEquationCPM(long val, ArrayList<Long> arr) {         //Attempts to solve the equation using ||, * and + operators
        for (int i = 0; i < Math.pow(3, arr.size()-1); i++) {                       //Every possible equation is encoded in the ternary numbers 0 to n(operators)
            String trits = toTritString(i);
            while (trits.length() < arr.size()-1) trits="0"+trits;                  //Add leading 0s if needed
            long result = arr.get(0);
            for (int j = 0; j < arr.size()-1; j++)                                  //Go through every trit and operate accordingly
                switch (trits.charAt(j)) {                                          //2 for concatenate, 1 for * and 0 for +
                    case '2' -> result = Long.parseLong(Long.toString(result)+Long.toString(arr.get(j+1)));
                    case '1' -> result *= arr.get(j+1);
                    default -> result += arr.get(j+1);
                }
            if (result == val) return true;
        }
        return false;
    }
    public static String toTritString(int i) {      //Simple decimal to ternary string encoder
        String trits = new String();
        while (i > 0) {
            trits = i % 3 + trits;
            i /= 3;
        }
        return trits;
    } 
}