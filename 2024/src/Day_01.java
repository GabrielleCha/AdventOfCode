import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class Day_01 {
    public static void main(String[] args) throws FileNotFoundException {
        int answer1 = 0, answer2 = 0;
        ArrayList<Integer> arr1 = new ArrayList<>(), arr2 = new ArrayList<>();
        Scanner reader = new Scanner(new File("../resources/01.txt")).useDelimiter("   |\r\n");
            for (int i = 0; i < 1000; i++) {        //Parsing numbers to the arrays
                arr1.add(reader.nextInt());
                arr2.add(reader.nextInt());
            }
        int dupeCount;
        for (int i = 0; i < arr1.size(); i++) {     //Part 2 solution calculation
            dupeCount = 0;
            for (int j = 0; j < arr2.size(); j++) 
                if(arr1.get(i).equals(arr2.get(j))) 
                    dupeCount += 1;
            answer2 += arr1.get(i) * dupeCount;
        }
        Collections.sort(arr1);                     //Sorting
        Collections.sort(arr2);
        for (int i = 0; i < arr1.size(); i++)       //Part 1 solution calculation
            answer1 += Math.abs(arr1.get(i) - arr2.get(i)); 
        System.out.println(answer1 + "\r\n" + answer2);
    }
}