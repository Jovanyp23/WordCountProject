import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class wc {
    public static void main(String [] args){
        System.out.println("Hey hm");
        String fileName="temp.txt";
        String line=null;
        int charz=0;
        int count=0;
        int wordz=0;
        if (args.length==0) {
            System.out.println("0 0 0 ");
        }
        try{


            FileReader reading= new FileReader(fileName);
            BufferedReader buff= new BufferedReader(reading);
            while((line=buff.readLine()) !=null){
                System.out.println(line);
                charz= charz+line.length();
                wordz=wordz+line.split("\\s+").length;
                System.out.println(line.split("\\s+").length);
                count++;
            }
             buff.close();
        }
        catch(FileNotFoundException e){
            System.out.println("Cannot open file");
        }
        catch(IOException e){
            System.out.println("error reading file");
        }
        System.out.println("Lines:"+count);
        System.out.println("Characters:"+charz);
        System.out.println("Words:"+wordz);

    }
}
