import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

public class Day_09 {
    public static void main(String[] args) throws IOException {
        int index = 0;
        String input = (Files.readAllLines(Path.of("../resources/09.txt"))).get(0);
        ArrayList<Integer> disk = new ArrayList<>();
        for (int i = 0; i < input.length(); i++) {
            int n = Integer.parseInt(Character.toString(input.charAt(i)));  //Parse input and transform it into our "disk image"
            if (i % 2 == 0) {
                for (int j = 0; j < n; j++) disk.add(index);
                index++;
            }
            else for (int j = 0; j < n; j++) disk.add(-1);
        }
        ArrayList<Integer> disk1 = new ArrayList<>(disk), disk2 = new ArrayList<>(disk);
        for (int i = (disk1.size()-1); i > -1; i--)                         //Rearrange with file system fragmentation (part 1)
            if(disk1.indexOf(-1) < i) {
                disk1.set(disk1.indexOf(-1),disk1.get(i));
                disk1.set(i, -1);
            }
        int l = disk2.size()-1;                                             
        while (disk2.get(l) == -1) l--;
        for (int id = disk2.get(l); id > -1; id--) {                        //Rearrange entire files without fragmentation (part 2)
            int blocks = 0, free = 0, p1, p2 = 0;
            int i = disk.indexOf(id);
            p1 = i;
            while (i < disk2.size() && disk2.get(i) == id) {i++; blocks++;} //Count the data blocks
            for (int j = 0; j < disk2.size(); j++) {
                if (disk2.get(j) == -1) {                                   //Count the free space blocks
                    if (free == 0) p2 = j;
                    free++;
                }
                else free = 0;
                if (free == blocks && p1 > p2) {
                    for (int k = 0; k < free; k++) {                        //Put file in first contiguous free space that fits it
                        disk2.set(p1+k, -1);
                        disk2.set(p2+k, id);
                    }
                    break;
                }
            }
        }
        long answer1 = checksum(disk1), answer2 = checksum(disk2);
        System.err.println(answer1+"\r\n"+answer2);                         //Print solutions
    }
    public static long checksum(ArrayList<Integer> arr) {                   //Checksum calculation Σ(file id * position on disk)
        long checksum = 0;
        for (int i = 0; i < arr.size(); i++) 
            if (arr.get(i) != -1) checksum += i * arr.get(i);
        return checksum;
    }
}