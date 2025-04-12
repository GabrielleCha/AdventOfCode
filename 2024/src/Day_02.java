import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Day_02 {
    public static void main(String[] args) throws FileNotFoundException {
        int answer1 = 0, answer2 = 0;
        try (Scanner fileScan = new Scanner(new File("../resources/02.txt"))) {
            while (fileScan.hasNextLine()) {
                ArrayList<Integer> r = new ArrayList<>();
                try (Scanner reportScan = new Scanner(fileScan.nextLine()).useDelimiter(" |\r\n")) {
                    while (reportScan.hasNextInt()) r.add(reportScan.nextInt());                //Parse report to the ArrayList
                    reportScan.close();                                                         //Close reader to prevent memory leak
                }
                if (test(r)) answer1++;                                                         //Part 1 report test
                else for (int i = 0; i < r.size(); i++) {
                    ArrayList<Integer> r1 = new ArrayList<>(r);                                 //Part 2 modified report tests
                    r1.remove(i);
                    if(test(r1)) { answer2++; break; }
                }
            }
            fileScan.close();                                                                   //Close reader to prevent memory leak
        }
        System.out.println(answer1 + "\r\n" + answer2 + "\r\n" + (answer1 + answer2));          //Print solutions
    }
    private static boolean test(ArrayList<Integer> r){                                          //Test function
        boolean inc = (r.get(0) < r.get(1));
        for (int i = 1; i < r.size(); i++) 
            if (Math.abs(r.get(i-1) - r.get(i)) > 3 || Math.abs(r.get(i-1) - r.get(i)) == 0 ||
                (inc && r.get(i-1) > r.get(i)) || (!inc && r.get(i-1) < r.get(i))) 
                return false;
        return true;
    }
}